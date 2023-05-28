package com.api.pogbet.controllers;

import com.api.pogbet.model.Aposta;
import com.api.pogbet.services.ApostaService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ApostaControllerTest {
    @InjectMocks                                    //CLASSE A SER TESTADA
    private ApostaController apostaController;

    @Mock                                           //CLASSES DENTRO DA CONTROLLER
    private ApostaService apostaServiceMock;

    @BeforeEach
    void setUp() {
        List<Aposta> apostas = createApostaList();

        BDDMockito.when(apostaServiceMock.save(ArgumentMatchers.any(Aposta.class)))
                .thenReturn(createAposta());

        BDDMockito.doNothing().when(apostaServiceMock).replace(ArgumentMatchers.any(Aposta.class));

        BDDMockito.doNothing().when(apostaServiceMock).delete(ArgumentMatchers.anyLong());

        BDDMockito.when(apostaServiceMock.listAll())
                .thenReturn(createApostaList());

        BDDMockito.when(apostaServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(new Aposta(1L, "categoria", 20.0F, true, "hoje", 1L));

        BDDMockito.when(apostaServiceMock.findByCategoria(ArgumentMatchers.anyString()))
                .thenReturn(createApostaList());

        BDDMockito.when(apostaServiceMock.findByValor(ArgumentMatchers.anyFloat()))
                .thenReturn(createApostaList());

        BDDMockito.when(apostaServiceMock.findByGanhou(ArgumentMatchers.anyBoolean()))
                .thenReturn(createApostaList());

        BDDMockito.when(apostaServiceMock.findByData(ArgumentMatchers.anyString()))
                .thenReturn(createApostaList());
    }

    @Test
    @DisplayName("save retorna aposta quando adicionado")
    void save_ReturnsAposta_WhenSuccessful() {
        Aposta apostaToBeSaved = createAposta();

        ResponseEntity<String> response = apostaController.save(apostaToBeSaved);

        Assertions.assertThat(response)
                .isNotNull();

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(response.getBody())
                .isEqualTo("Aposta Adicionada com Sucesso!\n");
    }

    @Test
    @DisplayName("replace atualiza aposta quando existir")
    void replace_UpdateAposta_WhenExists() {
        Aposta apostaToBeUpdated = createAposta();

        ResponseEntity<String> response = apostaController.replace(apostaToBeUpdated);

        Assertions.assertThatCode(() -> apostaController.replace(createAposta()))
                .doesNotThrowAnyException();

        Assertions.assertThat(response)
                .isNotNull();

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        Assertions.assertThat(response.getBody())
                .isEqualTo("Aposta Atualizada com Sucesso!\n");
    }

    @Test
    @DisplayName("delete remove uma aposta quando existir")
    void delete_UpdateAposta_WhenExists() {
        ResponseEntity<String> response = apostaController.delete(ArgumentMatchers.anyLong());

        Assertions.assertThatCode(() -> apostaController.delete(ArgumentMatchers.anyLong()))
                .doesNotThrowAnyException();

        Assertions.assertThat(response)
                .isNotNull();

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        Assertions.assertThat(response.getBody())
                .isEqualTo("Aposta Deletada com Sucesso!\n");
    }

    @Test
    @DisplayName("getById retorna aposta com respectivo ID")
    void getById_ReturnsAposta_WhenSuccessful() {
        Long idEsperada = 1L;
        Aposta aposta = apostaController.getById(ArgumentMatchers.anyLong()).getBody();

        Assertions.assertThat(aposta).isNotNull();

        Assertions.assertThat(aposta.getId())
                .isNotNull()
                .isEqualTo(idEsperada);
    }

    @Test
    @DisplayName("getById retorna NULL quando não encontra nada")
    void getById_EmptyList_WhenNotFound() {
        BDDMockito.when(apostaServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(null);

        Aposta aposta = apostaController.getById(ArgumentMatchers.anyLong()).getBody();

        Assertions.assertThat(aposta)
                .isNull();
    }

    @Test
    @DisplayName("getAll retorna uma lista com todas as apostas")
    void getAll_ReturnsListOfApostas_WhenSuccessful() {
        List<Aposta> apostas = apostaController.getAll().getBody();

        Assertions.assertThat(apostas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    @DisplayName("getAll retorna uma lista vazia quando não encontra nada")
    void getAll_ReturnsEmptyList_WhenNotFound() {
        BDDMockito.when(apostaServiceMock.listAll())
                .thenReturn(Collections.emptyList());

        List<Aposta> apostas = apostaController.getAll().getBody();

        Assertions.assertThat(apostas)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("getByCategoria retorna uma lista de apostas com a respectiva categoria")
    void getByCategoria_ReturnsListOfApostas_WhenSuccessful() {
        String categoriaEsperada = createAposta().getCategoria();
        List<Aposta> apostas = apostaController.getAllByCategoria(ArgumentMatchers.anyString()).getBody();

        Assertions.assertThat(apostas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(apostas.get(0).getCategoria()).isEqualTo(categoriaEsperada);
    }

    @Test
    @DisplayName("getByCategoria retorna uma lista vazia quando não encontra nada")
    void getByCategoria_ReturnsEmptyList_WhenNotFound() {
        BDDMockito.when(apostaServiceMock.findByCategoria(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Aposta> apostas = apostaController.getAllByCategoria(ArgumentMatchers.anyString()).getBody();

        Assertions.assertThat(apostas)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("getByValor retorna uma lista de apostas com o respectivo valor")
    void getByValor_ReturnsListOfApostas_WhenSuccessful() {
        Float valorEsperado = createAposta().getValor();
        List<Aposta> apostas = apostaController.getAllByValor(ArgumentMatchers.anyFloat()).getBody();

        Assertions.assertThat(apostas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(apostas.get(0).getValor()).isEqualTo(valorEsperado);
    }

    @Test
    @DisplayName("getByValor retorna uma lista vazia quando não encontra nada")
    void getByValor_ReturnsEmptyList_WhenNotFound() {
        BDDMockito.when(apostaServiceMock.findByValor(ArgumentMatchers.anyFloat()))
                .thenReturn(Collections.emptyList());

        List<Aposta> apostas = apostaController.getAllByValor(ArgumentMatchers.anyFloat()).getBody();

        Assertions.assertThat(apostas)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("getByGanhou retorna uma lista de apostas que ganharam")
    void getByGanhou_ReturnsListOfApostas_WhenSuccessful() {
        Boolean ganhouEsperado = createAposta().isGanhou();
        List<Aposta> apostas = apostaController.getAllByGanhou(ArgumentMatchers.anyBoolean()).getBody();

        Assertions.assertThat(apostas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(apostas.get(0).isGanhou()).isEqualTo(ganhouEsperado);
    }

    @Test
    @DisplayName("getByGanhou retorna uma lista vazia quando não encontra nada")
    void getByGanhou_ReturnsEmptyList_WhenNotFound() {
        BDDMockito.when(apostaServiceMock.findByGanhou(ArgumentMatchers.anyBoolean()))
                .thenReturn(Collections.emptyList());

        List<Aposta> apostas = apostaController.getAllByGanhou(ArgumentMatchers.anyBoolean()).getBody();

        Assertions.assertThat(apostas)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("getByData retorna uma lista de apostas com a respectiva data")
    void getByData_ReturnsListOfApostas_WhenSuccessful() {
        String dataEsperada = createAposta().getData();
        List<Aposta> apostas = apostaController.getAllByData("aposta teste").getBody();

        Assertions.assertThat(apostas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(apostas.get(0).getData()).isEqualTo(dataEsperada);
    }

    @Test
    @DisplayName("getByData retorna uma lista vazia quando não encontra nada")
    void getByData_ReturnsEmptyList_WhenNotFound() {
        BDDMockito.when(apostaServiceMock.findByData(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Aposta> apostas = apostaController.getAllByData(ArgumentMatchers.anyString()).getBody();

        Assertions.assertThat(apostas)
                .isNotNull()
                .isEmpty();
    }

    private static long nextApostaId = 1L;

    private Aposta createAposta() {
        long apostaId = nextApostaId++;
        return new Aposta(apostaId, "categoria", 20.0F, true, "hoje", 1L);
    }

    private List<Aposta> createApostaList() {
        List<Aposta> listaAposta = new ArrayList<>();
        listaAposta.add(createAposta());
        return listaAposta;
    }
}