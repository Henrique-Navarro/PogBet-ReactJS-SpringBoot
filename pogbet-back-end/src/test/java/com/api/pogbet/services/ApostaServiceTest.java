package com.api.pogbet.services;

import com.api.pogbet.controllers.ApostaController;
import com.api.pogbet.model.Aposta;
import com.api.pogbet.repository.ApostaRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Testes para ApostaService")
class ApostaServiceTest {
    @InjectMocks                                    //CLASSE A SER TESTADA
    private ApostaService apostaService;

    @Mock                                           //CLASSES DENTRO DA CONTROLLER
    private ApostaRepository apostaRepositoryMock;

    @BeforeEach
    void setUp() {
        List<Aposta> apostas = createApostaList();

        BDDMockito.when(apostaRepositoryMock.save(ArgumentMatchers.any(Aposta.class)))
                .thenReturn(createAposta());

        BDDMockito.doNothing().when(apostaRepositoryMock).delete(ArgumentMatchers.any(Aposta.class));

        BDDMockito.when(apostaRepositoryMock.findAll())
                .thenReturn(createApostaList());

        BDDMockito.when(apostaRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(new Aposta(1L, "categoria", 20.0F, true, "hoje", 1L)));

        BDDMockito.when(apostaRepositoryMock.findByCategoria(ArgumentMatchers.anyString()))
                .thenReturn(createApostaList());

        BDDMockito.when(apostaRepositoryMock.findByValor(ArgumentMatchers.anyFloat()))
                .thenReturn(createApostaList());

        BDDMockito.when(apostaRepositoryMock.findByGanhou(ArgumentMatchers.anyBoolean()))
                .thenReturn(createApostaList());

        BDDMockito.when(apostaRepositoryMock.findByData(ArgumentMatchers.anyString()))
                .thenReturn(createApostaList());
    }

    @Test
    @DisplayName("save retorna aposta quando adicionado")
    void save_ReturnsAposta_WhenSuccessful() {
        Aposta apostaToBeSaved = createAposta();

        Aposta aposta = apostaService.save(apostaToBeSaved);

        Assertions.assertThat(aposta)
                .isNotNull();
    }

    @Test
    @DisplayName("replace atualiza aposta quando existir")
    void replace_UpdateAposta_WhenExists() {
        Assertions.assertThatCode(() -> apostaService.replace(createAposta()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete remove uma aposta quando existir")
    void delete_UpdateAposta_WhenExists() {
        Assertions.assertThatCode(() -> apostaService.delete(ArgumentMatchers.anyLong()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("findById retorna aposta com respectivo ID")
    void findById_ReturnsAposta_WhenSuccessful() {
        Long idEsperada = 1L;
        Aposta aposta = apostaService.findById(ArgumentMatchers.anyLong());

        Assertions.assertThat(aposta).isNotNull();

        Assertions.assertThat(aposta.getId())
                .isNotNull()
                .isEqualTo(idEsperada);
    }

    @Test
    @DisplayName("findById lança Exception quando não encontra nada")
    void findById_ThrowsBadRequestException_WhenNotFound() {
        BDDMockito.when(apostaRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());
        
        Assertions.assertThatExceptionOfType(Exception.class)
                .isThrownBy(() -> apostaService.findById(ArgumentMatchers.anyLong()));
    }

    @Test
    @DisplayName("listAll retorna uma lista com todas as apostas")
    void listAll_ReturnsListOfApostas_WhenSuccessful() {
        List<Aposta> apostas = apostaService.listAll();

        Assertions.assertThat(apostas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    @DisplayName("findAll retorna uma lista vazia quando não encontra nada")
    void findAll_ReturnsEmptyList_WhenNotFound() {
        BDDMockito.when(apostaRepositoryMock.findAll())
                .thenReturn(Collections.emptyList());

        List<Aposta> apostas = apostaService.listAll();

        Assertions.assertThat(apostas)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findByCategoria retorna uma lista de apostas com a respectiva categoria")
    void findByCategoria_ReturnsListOfApostas_WhenSuccessful() {
        String categoriaEsperada = createAposta().getCategoria();
        List<Aposta> apostas = apostaService.findByCategoria(ArgumentMatchers.anyString());

        Assertions.assertThat(apostas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(apostas.get(0).getCategoria()).isEqualTo(categoriaEsperada);
    }

    @Test
    @DisplayName("findByCategoria retorna uma lista vazia quando não encontra nada")
    void findByCategoria_ReturnsEmptyList_WhenNotFound() {
        BDDMockito.when(apostaRepositoryMock.findByCategoria(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Aposta> apostas = apostaService.findByCategoria(ArgumentMatchers.anyString());

        Assertions.assertThat(apostas)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findByValor retorna uma lista de apostas com o respectivo valor")
    void findByValor_ReturnsListOfApostas_WhenSuccessful() {
        Float valorEsperado = createAposta().getValor();
        List<Aposta> apostas = apostaService.findByValor(ArgumentMatchers.anyFloat());

        Assertions.assertThat(apostas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(apostas.get(0).getValor()).isEqualTo(valorEsperado);
    }

    @Test
    @DisplayName("findByValor retorna uma lista vazia quando não encontra nada")
    void findByValor_ReturnsEmptyList_WhenNotFound() {
        BDDMockito.when(apostaRepositoryMock.findByValor(ArgumentMatchers.anyFloat()))
                .thenReturn(Collections.emptyList());

        List<Aposta> apostas = apostaService.findByValor(ArgumentMatchers.anyFloat());

        Assertions.assertThat(apostas)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findByGanhou retorna uma lista de apostas que ganharam")
    void findByGanhou_ReturnsListOfApostas_WhenSuccessful() {
        Boolean ganhouEsperado = createAposta().isGanhou();
        List<Aposta> apostas = apostaService.findByGanhou(ArgumentMatchers.anyBoolean());

        Assertions.assertThat(apostas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(apostas.get(0).isGanhou()).isEqualTo(ganhouEsperado);
    }

    @Test
    @DisplayName("findByGanhou retorna uma lista vazia quando não encontra nada")
    void findByGanhou_ReturnsEmptyList_WhenNotFound() {
        BDDMockito.when(apostaRepositoryMock.findByGanhou(ArgumentMatchers.anyBoolean()))
                .thenReturn(Collections.emptyList());

        List<Aposta> apostas = apostaService.findByGanhou(ArgumentMatchers.anyBoolean());

        Assertions.assertThat(apostas)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findByData retorna uma lista de apostas com a respectiva data")
    void findByData_ReturnsListOfApostas_WhenSuccessful() {
        String dataEsperada = createAposta().getData();
        List<Aposta> apostas = apostaService.findByData("aposta teste");

        Assertions.assertThat(apostas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(apostas.get(0).getData()).isEqualTo(dataEsperada);
    }

    @Test
    @DisplayName("findByData retorna uma lista vazia quando não encontra nada")
    void findByData_ReturnsEmptyList_WhenNotFound() {
        BDDMockito.when(apostaRepositoryMock.findByData(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Aposta> apostas = apostaService.findByData(ArgumentMatchers.anyString());

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