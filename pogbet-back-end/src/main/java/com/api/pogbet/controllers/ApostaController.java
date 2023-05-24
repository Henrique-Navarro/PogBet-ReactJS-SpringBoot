package com.api.pogbet.controllers;

import com.api.pogbet.model.Aposta;
import com.api.pogbet.services.ApostaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("aposta")
@RequiredArgsConstructor
public class ApostaController {
    private final ApostaService apostaService;

    @PostMapping(path = "/add")
    public ResponseEntity<String> save(@RequestBody @Valid Aposta aposta) {
        Aposta novaAposta = apostaService.save(aposta);
        return ResponseEntity.status(HttpStatus.CREATED).body("Aposta Adicionada com Sucesso!\n");
    }

    @PutMapping
    public ResponseEntity<String> replace(@RequestBody Aposta aposta) {
        apostaService.replace(aposta);
        return ResponseEntity.status(HttpStatus.OK).body("Aposta Atualizada com Sucesso!\n");
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        apostaService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Aposta Deletada com Sucesso!\n");
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Aposta> getById(@PathVariable Long id) {
        return new ResponseEntity<>(apostaService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/list")
    public ResponseEntity<List<Aposta>> getAll() {
        return new ResponseEntity<>(apostaService.listAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/list/categoria/{categoria}")
    public ResponseEntity<List<Aposta>> getAllByCategoria(@PathVariable String categoria) {
        return new ResponseEntity<>(apostaService.findByCategoria(categoria), HttpStatus.OK);
    }

    @GetMapping(path = "/list/valor/{valor}")
    public ResponseEntity<List<Aposta>> getAllByValor(@PathVariable Float valor) {
        return new ResponseEntity<>(apostaService.findByValor(valor), HttpStatus.OK);
    }

    @GetMapping(path = "/list/ganhou/{ganhou}")
    public ResponseEntity<List<Aposta>> getAllByGanhou(@PathVariable boolean ganhou) {
        return new ResponseEntity<>(apostaService.findByGanhou(ganhou), HttpStatus.OK);
    }

    @GetMapping(path = "/list/data/{data}")
    public ResponseEntity<List<Aposta>> getAllByData(@PathVariable String data) {
        return new ResponseEntity<>(apostaService.findByData(data), HttpStatus.OK);
    }
}
