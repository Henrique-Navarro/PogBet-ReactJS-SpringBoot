package com.api.pogbet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class User {
    //ATRIBUTOS DA TABELA (USUARIO) NO BANCO DE DADOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O nome não pode ser vazio!")
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String senha;

    @NotNull
    private String dataNascimento;

    @NotNull
    private double saldo;

    @NotEmpty
    private String cpf;

}
