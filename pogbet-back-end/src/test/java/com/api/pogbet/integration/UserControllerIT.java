package com.api.pogbet.integration;

import com.api.pogbet.model.User;
import com.api.pogbet.services.UserService;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;



@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testSaveUser() throws Exception {
        User user = new User();
        user.setName("Breno");
        user.setEmail("brenoH@gmail.com");
        user.setSenha("pro37ABfeATGzsd04");
        user.setDataNascimento("27062002");
        user.setSaldo(18000);
        user.setCpf("9876543210");

        Mockito.when(userService.save(any(User.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/novoUsuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Breno\",\"email\":\"brenoH@gmail.com\",\"senha\":\"pro37ABfeATGzsd04\",\"dataNascimento\":\"27062002\",\"saldo\":18000,\"cpf\":\"9876543210\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Usuário criado com Sucesso!\n"));

        verify(userService, Mockito.times(1)).save(any(User.class));
    }


    @Test
    public void testReplaceUser() throws Exception {
        User user = new User();
        // Preencher os detalhes do usuário para o teste

        doNothing().when(userService).replace(any(User.class));

        mockMvc.perform(MockMvcRequestBuilders.put("/attUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"example\",\"password\":\"password\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Usuário atualizado com Sucesso!\n"));

        verify(userService, Mockito.times(1)).replace(any(User.class));
    }

    @Test
    public void testDeleteUser() throws Exception {
        Long userId = 1L;

        doNothing().when(userService).delete(userId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/" + userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Usuário deletado com Sucesso!\n"));

        verify(userService, Mockito.times(1)).delete(userId);
    }
}
