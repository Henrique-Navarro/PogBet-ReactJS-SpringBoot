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
    public ResponseEntity<Aposta> save(@RequestBody @Valid Aposta aposta) {
        return new ResponseEntity<>(apostaService.save(aposta), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody Aposta aposta) {
        apostaService.replace(aposta);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        apostaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/list")
    public ResponseEntity<List<Aposta>> getAll() {
        return new ResponseEntity<>(apostaService.listAll(), HttpStatus.OK);
    }
}
