package com.api.pogbet.controllers;

import com.api.pogbet.model.Aposta;
import com.api.pogbet.model.User;
import com.api.pogbet.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor


public class UserController {
    //CLASSE QUE CONTROLA AS ROTAS DAS APLICAÇÃO
    private final UserService userService;

    @PostMapping(path = "/novoUsuario")
    public ResponseEntity<String> save(@RequestBody @Valid User user) {
        User newUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário criado com Sucesso!\n");
    }
    @PutMapping(path = "/attUser")
    public ResponseEntity<String> replace(@RequestBody User user) {
        userService.replace(user);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário atualizado com Sucesso!\n");
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com Sucesso!\n");
    }
}
