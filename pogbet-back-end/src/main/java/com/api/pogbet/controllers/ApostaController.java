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
@RequestMapping("aposta")
@RequiredArgsConstructor
public class ApostaController {
    private final ApostaService apostaService;

    @PostMapping(path = "/add")
    public ResponseEntity<String> save(@RequestBody @Valid Aposta aposta) {
        Aposta novaAposta = apostaService.save( aposta);
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

    @GetMapping(path = "/list")
    public ResponseEntity<List<Aposta>> getAll() {
        return new ResponseEntity<>(apostaService.listAll(), HttpStatus.OK);
    }
}
