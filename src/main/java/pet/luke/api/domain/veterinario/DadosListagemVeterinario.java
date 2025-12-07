package pet.luke.api.domain.veterinario;

public record DadosListagemVeterinario(Long id, String nome, String email, String crm, Especialidade especialidade) {

    public DadosListagemVeterinario(Veterinario veterinario) {
        this(veterinario.getId(), veterinario.getNome(), veterinario.getEmail(), veterinario.getCrmv(), veterinario.getEspecialidade());
    }

}
