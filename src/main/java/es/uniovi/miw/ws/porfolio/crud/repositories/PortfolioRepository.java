package es.uniovi.miw.ws.porfolio.crud.repositories;

import es.uniovi.miw.ws.porfolio.crud.models.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByNombre(String nombre);
}

