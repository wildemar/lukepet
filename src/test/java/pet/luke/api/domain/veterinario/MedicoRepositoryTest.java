package pet.luke.api.domain.veterinario;

import pet.luke.api.domain.consulta.Consulta;
import pet.luke.api.domain.endereco.DadosEndereco;
import pet.luke.api.domain.paciente.DadosCadastroPaciente;
import pet.luke.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class VeterinarioRepositoryTest {

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deveria devolver null quando unico veterinario cadastrado nao esta disponivel na data")
    void escolherVeterinarioAleatorioLivreNaDataCenario1() {
        //given ou arrange
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var veterinario = cadastrarVeterinario("Veterinario", "veterinario@luke.pet", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000", "Responsavel X");
        cadastrarConsulta(veterinario, paciente, proximaSegundaAs10);

        //when ou act
        var veterinarioLivre = veterinarioRepository.escolherVeterinarioAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        //then ou assert
        assertThat(veterinarioLivre).isNull();
    }

    @Test
    @DisplayName("Deveria devolver veterinario quando ele estiver disponivel na data")
    void escolherVeterinarioAleatorioLivreNaDataCenario2() {
        //given ou arrange
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var veterinario = cadastrarVeterinario("Veterinario", "veterinario@luke.pet", "123456", Especialidade.CARDIOLOGIA);

        //when ou act
        var veterinarioLivre = veterinarioRepository.escolherVeterinarioAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        //then ou assert
        assertThat(veterinarioLivre).isEqualTo(veterinario);
    }

    private void cadastrarConsulta(Veterinario veterinario, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, veterinario, paciente, data, null));
    }

    private Veterinario cadastrarVeterinario(String nome, String email, String crm, Especialidade especialidade) {
        var veterinario = new Veterinario(dadosVeterinario(nome, email, crm, especialidade));
        em.persist(veterinario);
        return veterinario;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf, String nomeResponsavel) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf, nomeResponsavel));
        em.persist(paciente);
        return paciente;
    }

    private DadosCadastroVeterinario dadosVeterinario(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroVeterinario(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf, String nomeResponsavel) {
        return new DadosCadastroPaciente(
                nome,
                email,
                "61999999999",
                cpf,
                nomeResponsavel,
                dadosEndereco()

        );
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }


}