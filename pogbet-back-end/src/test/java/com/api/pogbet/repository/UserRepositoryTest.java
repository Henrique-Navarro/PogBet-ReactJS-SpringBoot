package com.api.pogbet.repository;

import com.api.pogbet.model.User;
import com.api.pogbet.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByEmail() {
        String email = "john.doe@example.com";
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(user);

        User foundUser = userRepository.findByEmail(email);

        verify(userRepository, times(1)).findByEmail(email);

        Assertions.assertEquals(user, foundUser);
    }

    @Test
    void testFindByNameContaining() {
        String name = "John";
        User user1 = new User();
        user1.setId(1L);
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Jane Smith");
        user2.setEmail("jane.smith@example.com");

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        when(userRepository.findByNameContaining(name)).thenReturn(userList);

        List<User> foundUsers = userRepository.findByNameContaining(name);

        verify(userRepository, times(1)).findByNameContaining(name);

        Assertions.assertEquals(userList, foundUsers);
    }

    // Adicione outros métodos de teste conforme necessário
}
