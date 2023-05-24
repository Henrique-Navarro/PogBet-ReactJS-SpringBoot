package com.api.pogbet.repository;


import com.api.pogbet.model.Aposta;
import com.api.pogbet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    //MÉTODOS DE EDIÇÃO|CONSULTA DIRETAMENTE NO BANCO DE DADOS

}
