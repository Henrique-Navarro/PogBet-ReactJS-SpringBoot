package com.api.pogbet.services;

import com.api.pogbet.model.Aposta;
import com.api.pogbet.repository.ApostaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApostaService {
    private final ApostaRepository apostaRepository;


    public Aposta save(Aposta aposta) {
        return apostaRepository.save(aposta);
    }

    public void replace(Aposta aposta) {
        Aposta savedAposta = findById(aposta.getId());
        aposta.setId(savedAposta.getId());
        apostaRepository.save(aposta);
    }

    public void delete(Long id) {
        this.apostaRepository.deleteById(id);
    }

    public List<Aposta> listAll() {
        return apostaRepository.findAll();
    }

    public Aposta findById(Long id) {
        return apostaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID não encontado"));
    }

    public List<Aposta> findByCategoria(String categoria) {
        return apostaRepository.findByCategoria(categoria);
    }

    public List<Aposta> findByValor(Float valor) {
        return apostaRepository.findByValor(valor);
    }

    public List<Aposta> findByGanhou(boolean ganhou) {
        return apostaRepository.findByGanhou(ganhou);
    }

    public List<Aposta> findByData(String data) {
        return apostaRepository.findByData(data);
    }

}
