package pet.luke.api.domain.consulta;

import pet.luke.api.domain.consulta.Consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long id, Long idVeterinario, Long idPaciente, LocalDateTime data) {
    public DadosDetalhamentoConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getVeterinario().getId(), consulta.getPaciente().getId(), consulta.getData());
    }
}
