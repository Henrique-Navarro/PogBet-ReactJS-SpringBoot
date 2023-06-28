package com.api.pogbet.repository;

import com.api.pogbet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Método de consulta personalizado para encontrar um usuário por e-mail
    User findByEmail(String email);

    // Método de consulta personalizado para encontrar usuários por nome (usando like)
    List<User> findByNameContaining(String name);

    // Método de consulta personalizado para encontrar usuários com saldo maior que um valor específico
    List<User> findBySaldoGreaterThan(double saldo);

    // Método de consulta personalizado para encontrar usuários com idade maior que uma determinada data de nascimento
    List<User> findByDataNascimentoAfter(String dataNascimento);

    // Método de consulta personalizado para encontrar usuários com CPF específico
    User findByCpf(String cpf);

    // Método de consulta personalizado para encontrar usuários por e-mail e senha
    User findByEmailAndSenha(String email, String senha);
}
