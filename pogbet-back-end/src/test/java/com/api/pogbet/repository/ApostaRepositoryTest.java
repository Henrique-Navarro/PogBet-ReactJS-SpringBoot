package com.api.pogbet.repository;

import com.api.pogbet.model.Aposta;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Testes para ApostaRepository")
class ApostaRepositoryTest {
    @Autowired
    private ApostaRepository apostaRepository;

    @Test
    @DisplayName("Criar e salvar aposta")
    public void save_PersistAposta_WhenSuccessful() {
        Aposta apostaToBeSaved = createAposta();
        Aposta apostaSaved = this.apostaRepository.save(apostaToBeSaved);

        Assertions.assertThat(apostaSaved).isNotNull()
                .extracting(
                        Aposta::getId,
                        Aposta::getCategoria,
                        Aposta::getValor,
                        Aposta::isGanhou,
                        Aposta::getData,
                        Aposta::getUserID
                )
                .containsExactly(
                        apostaToBeSaved.getId(),
                        apostaToBeSaved.getCategoria(),
                        apostaToBeSaved.getValor(),
                        apostaToBeSaved.isGanhou(),
                        apostaToBeSaved.getData(),
                        apostaToBeSaved.getUserID()
                );
    }

    @Test
    @DisplayName("Atualizar aposta")
    public void update_UpdateAposta_WhenExists() {
        Aposta apostaToBeSaved = createAposta();
        Aposta apostaSaved = this.apostaRepository.save(apostaToBeSaved);

        apostaSaved.setData("atualizada");
        apostaSaved.setCategoria("atualizada");
        apostaSaved.setValor(150F);
        apostaSaved.setGanhou(false);

        Aposta apostaUpdated = this.apostaRepository.save(apostaSaved);

        Assertions.assertThat(apostaUpdated)
                .isNotNull()
                .extracting(
                        Aposta::getId,
                        Aposta::getData,
                        Aposta::getValor,
                        Aposta::getCategoria,
                        Aposta::isGanhou,
                        Aposta::getUserID
                )
                .containsExactly(
                        apostaSaved.getId(),
                        apostaSaved.getData(),
                        apostaSaved.getValor(),
                        apostaSaved.getCategoria(),
                        apostaSaved.isGanhou(),
                        apostaSaved.getUserID()
                );
    }

    @Test
    @DisplayName("Remover aposta quando existir")
    public void delete_RemovesAposta_WhenExists() {
        Aposta apostaToBeSaved = createAposta();
        Aposta apostaSaved = this.apostaRepository.save(apostaToBeSaved);
        this.apostaRepository.delete(apostaSaved);

        Optional<Aposta> deletedAposta = this.apostaRepository.findById(apostaSaved.getId());
        Assertions.assertThat(deletedAposta).isNotPresent();
        Assertions.assertThat(deletedAposta).isEmpty();
    }

    @Test
    @DisplayName("Remover aposta por ID quando existir")
    public void delete_RemovesApostaById_WhenExists() {
        Aposta apostaToBeSaved = createAposta();
        Aposta apostaSaved = this.apostaRepository.save(apostaToBeSaved);
        this.apostaRepository.deleteById(apostaSaved.getId());

        Optional<Aposta> deletedAposta = this.apostaRepository.findById(apostaSaved.getId());
        Assertions.assertThat(deletedAposta).isNotPresent();
    }

    @Test
    @DisplayName("Buscar aposta por ID quando existir")
    public void findById_ReturnsAposta_WhenExists() {
        Aposta apostaToBeSaved = createAposta();
        Aposta apostaSaved = this.apostaRepository.save(apostaToBeSaved);

        Optional<Aposta> foundAposta = this.apostaRepository.findById(apostaSaved.getId());

        Assertions.assertThat((foundAposta).isPresent());
        Assertions.assertThat(foundAposta.get()).isEqualTo(apostaSaved);
    }

    @Test
    @DisplayName("Buscar aposta por ID retorna Vazio quando não existir")
    public void findById_ReturnsEmptyList_WhenExists() {
        Aposta apostaToBeSaved = createAposta();
        Aposta apostaSaved = this.apostaRepository.save(apostaToBeSaved);

        Optional<Aposta> foundAposta = this.apostaRepository.findById(-999999999L);

        Assertions.assertThat(foundAposta).isEmpty();
    }

    @Test
    @DisplayName("Buscar apostas por Categoria quando existir")
    public void findByCategoria_ReturnsAposta_WhenExists() {
        Aposta apostaToBeSaved = createAposta();
        Aposta apostaSaved = this.apostaRepository.save(apostaToBeSaved);
        List<Aposta> foundAposta = this.apostaRepository.findByCategoria(apostaSaved.getCategoria());

        Assertions.assertThat(foundAposta)
                .isNotEmpty()
                .hasSize(1)
                .contains(apostaSaved);
    }

    @Test
    @DisplayName("Buscar apostas por Categoria retorna Vazio quando não existir")
    public void findByCategoria_ReturnsEmptyList_WhenExists() {
        Aposta apostaToBeSaved = createAposta();
        Aposta apostaSaved = this.apostaRepository.save(apostaToBeSaved);
        List<Aposta> foundAposta = this.apostaRepository.findByCategoria("não existe");

        Assertions.assertThat(foundAposta)
                .isEmpty();
    }

    @Test
    @DisplayName("Buscar apostas por Valor quando existir")
    public void findByValor_ReturnsAposta_WhenExists() {
        Aposta apostaToBeSaved = createAposta();
        Aposta apostaSaved = this.apostaRepository.save(apostaToBeSaved);
        List<Aposta> foundAposta = this.apostaRepository.findByValor(apostaSaved.getValor());

        Assertions.assertThat(foundAposta)
                .isNotEmpty()
                .hasSize(1)
                .contains(apostaSaved);
    }

    @Test
    @DisplayName("Buscar apostas por Valor retorna Vazio quando não existir")
    public void findByValor_ReturnsEmptyList_WhenExists() {
        Aposta apostaToBeSaved = createAposta();
        Aposta apostaSaved = this.apostaRepository.save(apostaToBeSaved);
        List<Aposta> foundAposta = this.apostaRepository.findByValor(-99999999F);

        Assertions.assertThat(foundAposta)
                .isEmpty();
    }

    @Test
    @DisplayName("Buscar apostas que Ganhou quando existir")
    public void findByGanhou_ReturnsAposta_WhenExists() {
        Aposta apostaToBeSaved = createAposta();
        Aposta apostaSaved = this.apostaRepository.save(apostaToBeSaved);
        List<Aposta> foundAposta = this.apostaRepository.findByGanhou(apostaSaved.isGanhou());

        Assertions.assertThat(foundAposta)
                .isNotEmpty()
                .hasSize(1)
                .contains(apostaSaved);
    }

    @Test
    @DisplayName("Buscar apostas que Ganhou retorna Vazio quando não existir")
    public void findByGanhou_ReturnsEmptyList_WhenExists() {
        Aposta apostaToBeSaved = createAposta();
        Aposta apostaSaved = this.apostaRepository.save(apostaToBeSaved);
        List<Aposta> foundAposta = this.apostaRepository.findByGanhou(false);

        Assertions.assertThat(foundAposta)
                .isEmpty();
    }

    @Test
    @DisplayName("Buscar apostas por Data quando existir")
    public void findByData_ReturnsAposta_WhenExists() {
        Aposta apostaToBeSaved = createAposta();
        Aposta apostaSaved = this.apostaRepository.save(apostaToBeSaved);
        List<Aposta> foundAposta = this.apostaRepository.findByData(apostaSaved.getData());

        Assertions.assertThat(foundAposta)
                .isNotEmpty()
                .hasSize(1)
                .contains(apostaSaved);
    }

    @Test
    @DisplayName("Buscar apostas por Data retorna Vazio quando não existir")
    public void findByData_ReturnsEmptyList_WhenExists() {
        Aposta apostaToBeSaved = createAposta();
        Aposta apostaSaved = this.apostaRepository.save(apostaToBeSaved);
        List<Aposta> foundAposta = this.apostaRepository.findByData("não existe");

        Assertions.assertThat(foundAposta)
                .isEmpty();
    }

    @Test
    @DisplayName("Listar todas as apostas")
    public void findAll_ReturnsAllApostas() {
        Aposta aposta1 = createAposta();
        Aposta aposta2 = createAposta();
        this.apostaRepository.saveAll(Arrays.asList(aposta1, aposta2));

        List<Aposta> allApostas = this.apostaRepository.findAll();

        Assertions.assertThat(allApostas)
                .isNotEmpty()
                .hasSize(2)
                .contains(aposta1)
                .contains(aposta2);
    }


    private static long nextApostaId = 1L;

    private Aposta createAposta() {
        long apostaId = nextApostaId++;
        return new Aposta(apostaId, "categoria", 20.0F, true, "hoje", 1L);
    }

}