package pet.luke.api.domain.consulta.validacoes.agendamento;

import pet.luke.api.domain.ValidacaoException;
import pet.luke.api.domain.consulta.ConsultaRepository;
import pet.luke.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorVeterinarioComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        var veterinarioPossuiOutraConsultaNoMesmoHorario = repository.existsByVeterinarioIdAndDataAndMotivoCancelamentoIsNull(dados.idVeterinario(), dados.data());
        if (veterinarioPossuiOutraConsultaNoMesmoHorario) {
            throw new ValidacaoException("Veterinário já possui outra consulta agendada nesse mesmo horário");
        }
    }

}
