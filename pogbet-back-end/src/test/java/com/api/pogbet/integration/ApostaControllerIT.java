package com.api.pogbet.integration;

import com.api.pogbet.model.Aposta;
import com.api.pogbet.repository.ApostaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ApostaControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ApostaRepository apostaRepository;

    @LocalServerPort
    private int port;

    @Test
    @DisplayName("save retorna aposta quando adicionado")
    void save_ReturnsAposta_WhenSuccessful() {
        Aposta apostaToBeSaved = createAposta();

        ResponseEntity<String> response = testRestTemplate.postForEntity(
                "/aposta/add", apostaToBeSaved, String.class);
        
        Assertions.assertThat(response)
                .isNotNull();

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);
        
        Assertions.assertThat(response.getBody())
                .isNotNull();
    }

    @Test
    @DisplayName("replace atualiza aposta quando existir")
    void replace_UpdateAposta_WhenExists() {
        Aposta savedAposta = apostaRepository.save(createAposta());
        savedAposta.setCategoria("aposta atualizada");
        
        ResponseEntity<Void> response = testRestTemplate.exchange(
                "/aposta", HttpMethod.PUT, new HttpEntity<>(savedAposta), Void.class);
        

        Assertions.assertThat(response)
                .isNotNull();

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("delete remove uma aposta quando existir")
    void delete_UpdateAposta_WhenExists() {
        Aposta savedAposta = apostaRepository.save(createAposta());
        
        ResponseEntity<Void> response = testRestTemplate.exchange(
                "/aposta/{id}", HttpMethod.DELETE, null, Void.class, savedAposta.getId());
        
        Assertions.assertThat(response)
                .isNotNull();

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("getById retorna aposta com respectivo ID")
    void getById_ReturnsAposta_WhenSuccessful() {
        Aposta savedAposta = apostaRepository.save(createAposta());

        Long idEsperada = savedAposta.getId();
        
        Aposta aposta = testRestTemplate.getForObject(
                "/aposta/{id}", Aposta.class, idEsperada);

        Assertions.assertThat(aposta).isNotNull();

        Assertions.assertThat(aposta.getId())
                .isNotNull()
                .isEqualTo(idEsperada);
    }

    @Test
    @DisplayName("getById retorna NULL quando não encontra nada")
    void getById_EmptyList_WhenNotFound() {
        Aposta savedAposta = apostaRepository.save(createAposta());

        Long idEsperada = savedAposta.getId()-1L;

        Aposta aposta = testRestTemplate.getForObject(
                "/aposta/{id}", Aposta.class, idEsperada);
        
        Assertions.assertThat(aposta
                .getId()).isNull();
    }
    
    @Test
    @DisplayName("getAll retorna uma lista com todas as apostas")
    void getAll_ReturnsListOfApostas_WhenSuccessful() {
        Aposta savedAposta = apostaRepository.save(createAposta());
        String expectedName = savedAposta.getCategoria();

        List<Aposta> apostas = testRestTemplate.exchange(
                "/aposta/list", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Aposta>>() {
                }).getBody();
        
        Assertions.assertThat(apostas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(apostas.get(0).getCategoria()).isEqualTo(expectedName);
    }
    
    @Test
    @DisplayName("getAll retorna uma lista vazia quando não encontra nada")
    void getAll_ReturnsEmptyList_WhenNotFound() {
        List<Aposta> apostas = testRestTemplate.exchange(
                "/aposta/list", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Aposta>>() {
                }).getBody();

        Assertions.assertThat(apostas)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("getByCategoria retorna uma lista de apostas com a respectiva categoria")
    void getByCategoria_ReturnsListOfApostas_WhenSuccessful() {
        Aposta savedAposta = apostaRepository.save(createAposta());
        String expectedName = savedAposta.getCategoria();

        String url = String.format("/aposta/list/categoria/%s", expectedName);
        List<Aposta> apostas = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Aposta>>() {
                }).getBody();

        Assertions.assertThat(apostas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(apostas.get(0).getCategoria()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("getByCategoria retorna uma lista vazia quando não encontra nada")
    void getByCategoria_ReturnsEmptyList_WhenNotFound() {
        String expectedName = "não existe";
        String url = String.format("/aposta/list/categoria/%s", expectedName);
        
        List<Aposta> apostas = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Aposta>>() {
                }).getBody();

        Assertions.assertThat(apostas)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("getByValor retorna uma lista de apostas com o respectivo valor")
    void getByValor_ReturnsListOfApostas_WhenSuccessful() {
        Aposta savedAposta = apostaRepository.save(createAposta());
        Float expectedValor = savedAposta.getValor();

        String url = "/aposta/list/valor/"+expectedValor;
        List<Aposta> apostas = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Aposta>>() {
                }).getBody();

        Assertions.assertThat(apostas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(apostas.get(0).getValor()).isEqualTo(expectedValor);
    }

    @Test
    @DisplayName("getByValor retorna uma lista vazia quando não encontra nada")
    void getByValor_ReturnsEmptyList_WhenNotFound() {
        Float expectedValor = -1F;

        String url = "/aposta/list/valor/"+ expectedValor;
        List<Aposta> apostas = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Aposta>>() {
                }).getBody();

        Assertions.assertThat(apostas)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("getByGanhou retorna uma lista de apostas que ganharam")
    void getByGanhou_ReturnsListOfApostas_WhenSuccessful() {
        Aposta savedAposta = apostaRepository.save(createAposta());
        Boolean expectedGanhou = savedAposta.isGanhou();

        String url = "/aposta/list/ganhou/"+expectedGanhou;
        List<Aposta> apostas = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Aposta>>() {
                }).getBody();

        Assertions.assertThat(apostas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(apostas.get(0).isGanhou()).isEqualTo(expectedGanhou);
    }

    @Test
    @DisplayName("getByGanhou retorna uma lista vazia quando não encontra nada")
    void getByGanhou_ReturnsEmptyList_WhenNotFound() {
        Boolean expectedGanhou = false;

        String url = "/aposta/list/ganhou/"+expectedGanhou;
        List<Aposta> apostas = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Aposta>>() {
                }).getBody();
        
        Assertions.assertThat(apostas)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("getByData retorna uma lista de apostas com a respectiva data")
    void getByData_ReturnsListOfApostas_WhenSuccessful() {
        Aposta savedAposta = apostaRepository.save(createAposta());
        String expectedData = savedAposta.getData();

        String url = "/aposta/list/data/"+expectedData;
        List<Aposta> apostas = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Aposta>>() {
                }).getBody();

        Assertions.assertThat(apostas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(apostas.get(0).getData()).isEqualTo(expectedData);
    }

    @Test
    @DisplayName("getByData retorna uma lista vazia quando não encontra nada")
    void getByData_ReturnsEmptyList_WhenNotFound() {
        Aposta savedAposta = apostaRepository.save(createAposta());
        String expectedData = "não existe";

        String url = "/aposta/list/data/"+expectedData;
        List<Aposta> apostas = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Aposta>>() {
                }).getBody();
        Assertions.assertThat(apostas)
                .isNotNull()
                .isEmpty();
    }

    private static long nextApostaId = 1L;

    private List<Aposta> createApostaList() {
        List<Aposta> listaAposta = new ArrayList<>();
        listaAposta.add(createAposta());
        return listaAposta;
    }

    private Aposta createAposta() {
        long apostaId = nextApostaId++;
        return new Aposta(apostaId, "categoria", 20.0F, true, "hoje", 1L);
    }
}
