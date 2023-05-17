package com.api.pogbet.controllers;

import com.api.pogbet.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {
    //CLASSE QUE CONTROLA AS ROTAS DAS APLICAÇÃO
    private final UserService userService;
}
