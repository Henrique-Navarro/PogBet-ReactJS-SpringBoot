package com.api.pogbet.services;

import com.api.pogbet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    //CLASSE RESPONSÁVEL PELAS REGRAS DE NEGÓCIO
    private final UserRepository userRepository;
}
