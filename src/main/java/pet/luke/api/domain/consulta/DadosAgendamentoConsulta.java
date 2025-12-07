package pet.luke.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import pet.luke.api.domain.veterinario.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        Long idVeterinario,

        @NotNull
        Long idPaciente,

        @NotNull
        @Future
        LocalDateTime data,

        Especialidade especialidade) {
}
