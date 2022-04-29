package es.uniovi.miw.ws.porfolio.crud.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.uniovi.miw.ws.porfolio.crud.Application;
import es.uniovi.miw.ws.porfolio.crud.models.Portfolio;
import es.uniovi.miw.ws.porfolio.crud.repositories.PortfolioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Application.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@AutoConfigureTestDatabase
@DirtiesContext(
        classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class PortfolioControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private PortfolioRepository portfolioRepository;
    private List<Portfolio> portfolios;
    private final int INITIAL_SIZE = 5;
    @BeforeEach
    public void setUp(){
        portfolios = new ArrayList<>();
        for (int i = 0 ; i < INITIAL_SIZE ; i++) {
            Portfolio portfolio = new Portfolio();
            portfolio.setNombre("Test Author " + i);
            portfolio.setBolsa("Test Comment " + i);
            portfolio.setActivo("Test Product " + i);
            portfolio.setCantidad(3 + i);
            portfolio.setPrecio(100.0 + i);
            portfolios.add(portfolio);
        }
        portfolioRepository.saveAll(portfolios);
        portfolioRepository.flush();
    }
    @Test
    public void whenGetReviews() throws Exception{
        String responseContent = mvc.perform(get("/api/portfolios")
                        .contentType(MediaType.APPLICATION_JSON))
                //Espero un ok
                .andExpect(status().isOk())
                //El contenido que espero es un json
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        Portfolio[] result = new ObjectMapper()
                .readValue(responseContent, Portfolio[].class);
        //Para el test compruebo el tamaño de la respuesta
        assertThat(result).hasSize(INITIAL_SIZE);
    }
}
