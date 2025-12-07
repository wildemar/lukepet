package pet.luke.api.domain.veterinario;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import pet.luke.api.domain.endereco.DadosEndereco;

public record DadosCadastroVeterinario(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,

        @NotBlank
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String crmv,
        @NotNull
        Especialidade especialidade,

        @NotNull @Valid DadosEndereco endereco) {
}
