package com.api.pogbet.controllers;

import com.api.pogbet.model.User;
import com.api.pogbet.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void save_shouldReturnOk() throws Exception {
        User user = new User();
        user.setName("John");
        user.setEmail("john@example.com");
        user.setSenha("password");
        user.setDataNascimento("1990-01-01");
        user.setSaldo(100.0);
        user.setCpf("12345678900");

        when(userService.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/novoUsuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"John\", \"email\": \"john@example.com\", \"senha\": \"password\", \"dataNascimento\": \"1990-01-01\", \"saldo\": 100.0, \"cpf\": \"12345678900\" }"))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuário criado com Sucesso!\n"));

        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void replace_shouldReturnOk() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setEmail("john@example.com");
        user.setSenha("password");
        user.setDataNascimento("1990-01-01");
        user.setSaldo(100.0);
        user.setCpf("12345678900");

        mockMvc.perform(put("/attUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1, \"name\": \"John\", \"email\": \"john@example.com\", \"senha\": \"password\", \"dataNascimento\": \"1990-01-01\", \"saldo\": 100.0, \"cpf\": \"12345678900\" }"))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuário atualizado com Sucesso!\n"));

        verify(userService, times(1)).replace(any(User.class));
    }

    @Test
    void delete_shouldReturnOk() throws Exception {
        Long userId = 1L;

        mockMvc.perform(delete("/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuário deletado com Sucesso!\n"));

        verify(userService, times(1)).delete(userId);
    }
}
