package pet.luke.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import pet.luke.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco,
        String nomeResponsavel) {
}
