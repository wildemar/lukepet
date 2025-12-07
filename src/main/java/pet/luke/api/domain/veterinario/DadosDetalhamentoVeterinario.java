package pet.luke.api.domain.veterinario;

import pet.luke.api.domain.endereco.Endereco;

public record DadosDetalhamentoVeterinario(Long id, String nome, String email, String crm, String telefone, Especialidade especialidade, Endereco endereco) {

    public DadosDetalhamentoVeterinario(Veterinario veterinario) {
        this(veterinario.getId(), veterinario.getNome(), veterinario.getEmail(), veterinario.getCrmv(), veterinario.getTelefone(), veterinario.getEspecialidade(), veterinario.getEndereco());
    }
}
