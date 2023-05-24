package com.api.pogbet.repository;

import com.api.pogbet.model.Aposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApostaRepository extends JpaRepository<Aposta, Long> {
    List<Aposta> findByCategoria(String categoria);

    List<Aposta> findByValor(Float valor);

    List<Aposta> findByGanhou(boolean ganhou);

    List<Aposta> findByData(String data);

}
