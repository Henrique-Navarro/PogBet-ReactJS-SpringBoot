package com.api.pogbet.services;

import com.api.pogbet.model.User;
import com.api.pogbet.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReplace() {
        // Criação de um usuário de exemplo
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");

        // Configuração do comportamento do mock
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        // Execução do método a ser testado
        userService.replace(user);

        // Verificação se o método save foi chamado com o usuário correto
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDelete() {
        Long userId = 1L;

        // Execução do método a ser testado
        userService.delete(userId);

        // Verificação se o método deleteById foi chamado com o ID correto
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testSave() {
        // Criação de um usuário de exemplo
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        // Configuração do comportamento do mock
        when(userRepository.save(user)).thenReturn(user);

        // Execução do método a ser testado
        User savedUser = userService.save(user);

        // Verificação se o método save foi chamado com o usuário correto
        verify(userRepository, times(1)).save(user);

        // Verificação se o usuário retornado é o mesmo que foi salvo
        Assertions.assertEquals(user, savedUser);
    }
}
