package pet.luke.api.domain.consulta;

import pet.luke.api.domain.ValidacaoException;
import pet.luke.api.domain.consulta.*;
import pet.luke.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import pet.luke.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import pet.luke.api.domain.veterinario.Veterinario;
import pet.luke.api.domain.veterinario.VeterinarioRepository;
import pet.luke.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe!");
        }

        if (dados.idVeterinario() != null && !veterinarioRepository.existsById(dados.idVeterinario())) {
            throw new ValidacaoException("Id do veterinário informado não existe!");
        }

        validadores.forEach(v -> v.validar(dados));

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var veterinario = escolherVeterinario(dados);
        if (veterinario == null) {
            throw new ValidacaoException("Não existe veterinário disponível nessa data!");
        }

        var consulta = new Consulta(null, veterinario, paciente, dados.data(), null);
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
        consultaRepository.save(consulta);
    }


    private Veterinario escolherVeterinario(DadosAgendamentoConsulta dados) {
        if (dados.idVeterinario() != null) {
            return veterinarioRepository.getReferenceById(dados.idVeterinario());
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando veterinário não for escolhido!");
        }

        return veterinarioRepository.escolherVeterinarioAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

}
