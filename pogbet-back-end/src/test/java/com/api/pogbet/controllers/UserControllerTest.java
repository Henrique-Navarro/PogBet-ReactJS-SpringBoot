package com.api.pogbet.controllers;


import com.api.pogbet.model.User;
import com.api.pogbet.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_shouldReturnCreatedStatus() {
        // Arrange
        User user = new User();
        when(userService.save(user)).thenReturn(new User());

        // Act
        ResponseEntity<String> response = userController.save(user);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Usuário criado com Sucesso!\n", response.getBody());
        verify(userService, times(1)).save(user);
    }

    @Test
    void replace_shouldReturnOkStatus() {
        // Arrange
        User user = new User();

        // Act
        ResponseEntity<String> response = userController.replace(user);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuário atualizado com Sucesso!\n", response.getBody());
        verify(userService, times(1)).replace(user);
    }

    @Test
    void delete_shouldReturnOkStatus() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<String> response = userController.delete(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuário deletado com Sucesso!\n", response.getBody());
        verify(userService, times(1)).delete(id);
    }
}
