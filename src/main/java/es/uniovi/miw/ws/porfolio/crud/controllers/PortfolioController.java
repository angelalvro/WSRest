package es.uniovi.miw.ws.porfolio.crud.controllers;

import es.uniovi.miw.ws.porfolio.crud.models.Portfolio;
import es.uniovi.miw.ws.porfolio.crud.repositories.PortfolioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

//Cualquier origen 1 hora
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    //Inicializamos el repositorio
    private final PortfolioRepository portfolioRepository;

    public PortfolioController(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    //Devuelve todos
    //ok es un 200
    //Devuelve todos si no se le pasa un requestParam, en este caso no es obligatorio
    @GetMapping
    public ResponseEntity<?> getPortfolio
            (@RequestParam(value="nombre",required=false) String nombre){
        if(nombre != null)
            return ResponseEntity.
                    ok(portfolioRepository.findByNombre(nombre));
        return ResponseEntity.ok(portfolioRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPortfolio(@PathVariable long id) {
        Optional<Portfolio> found = portfolioRepository.findById(id);
        if(found.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(found.get());
    }

    @PostMapping
    public ResponseEntity<?> postPortfolio(@Valid @RequestBody Portfolio portfolio) {
        portfolioRepository.saveAndFlush(portfolio);
        //Metemos un location para que el nuevo recurso este disponible
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(portfolio.getId())
                .toUri();
        return ResponseEntity.created(location).body(portfolio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putPortfolio(@PathVariable long id, @Valid
    @RequestBody Portfolio portfolio) {
        Optional<Portfolio> found = portfolioRepository.findById(id);
        if(found.isEmpty())
            return ResponseEntity.notFound().build();
        Portfolio current = found.get();
        current.setNombre(portfolio.getNombre());
        current.setActivo(portfolio.getActivo());
        current.setBolsa(portfolio.getBolsa());
        current.setCantidad(portfolio.getCantidad());
        current.setPrecio(portfolio.getPrecio());
        portfolioRepository.saveAndFlush(current);
        return ResponseEntity.ok(current);
    }

    //Cuando borramos devolvemos el objeto que hemos borrado
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePortfolio(@PathVariable long id) {
        Optional<Portfolio> found = portfolioRepository.findById(id);
        if(found.isEmpty())
            return ResponseEntity.notFound().build();
        Portfolio current = found.get();
        portfolioRepository.delete(current);
        return ResponseEntity.ok(current);
    }
}

