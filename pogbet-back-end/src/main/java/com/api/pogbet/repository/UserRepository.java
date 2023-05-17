package com.api.pogbet.repository;


import com.api.pogbet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    //MÉTODOS DE EDIÇÃO|CONSULTA DIRETAMENTE NO BANCO DE DADOS
}
