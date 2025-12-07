package pet.luke.api.domain.consulta.validacoes.agendamento;

import pet.luke.api.domain.ValidacaoException;
import pet.luke.api.domain.consulta.DadosAgendamentoConsulta;
import pet.luke.api.domain.veterinario.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorVeterinarioAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private VeterinarioRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        //escolha do veterinario opcional
        if (dados.idVeterinario() == null) {
            return;
        }

        var veterinarioEstaAtivo = repository.findAtivoById(dados.idVeterinario());
        if (!veterinarioEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com veterinário excluído");
        }
    }

}
