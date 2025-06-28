package com.odontosimples.service;

import com.odontosimples.entity.Paciente;
import com.odontosimples.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente criarPaciente(Paciente paciente) {
        if (pacienteRepository.existsByCpf(paciente.getCpf())) {
            throw new RuntimeException("CPF já está cadastrado");
        }
        return pacienteRepository.save(paciente);
    }

    public Paciente atualizarPaciente(Long id, Paciente pacienteAtualizado) {
        Paciente paciente = buscarPorId(id);
        
        // Verifica se o CPF não está sendo usado por outro paciente
        if (!paciente.getCpf().equals(pacienteAtualizado.getCpf()) && 
            pacienteRepository.existsByCpf(pacienteAtualizado.getCpf())) {
            throw new RuntimeException("CPF já está cadastrado");
        }
        
        paciente.setNome(pacienteAtualizado.getNome());
        paciente.setCpf(pacienteAtualizado.getCpf());
        paciente.setRg(pacienteAtualizado.getRg());
        paciente.setDataNascimento(pacienteAtualizado.getDataNascimento());
        paciente.setTelefone(pacienteAtualizado.getTelefone());
        paciente.setEmail(pacienteAtualizado.getEmail());
        paciente.setEndereco(pacienteAtualizado.getEndereco());
        paciente.setObservacoes(pacienteAtualizado.getObservacoes());
        paciente.setProfissao(pacienteAtualizado.getProfissao());
        paciente.setEstadoCivil(pacienteAtualizado.getEstadoCivil());
        paciente.setContatoEmergencia(pacienteAtualizado.getContatoEmergencia());
        paciente.setTelefoneEmergencia(pacienteAtualizado.getTelefoneEmergencia());
        
        return pacienteRepository.save(paciente);
    }

    @Transactional(readOnly = true)
    public Paciente buscarPorId(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
    }

    @Transactional(readOnly = true)
    public Paciente buscarPorCpf(String cpf) {
        return pacienteRepository.findByCpfAndActiveTrue(cpf)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Paciente> listarTodos() {
        return pacienteRepository.findByActiveTrue();
    }

    @Transactional(readOnly = true)
    public Page<Paciente> listarTodos(Pageable pageable) {
        return pacienteRepository.findByActiveTrue(pageable);
    }

    @Transactional(readOnly = true)
    public List<Paciente> buscarPorNome(String nome) {
        return pacienteRepository.findByNomeContainingAndActiveTrue(nome);
    }

    @Transactional(readOnly = true)
    public Page<Paciente> buscarPorNome(String nome, Pageable pageable) {
        return pacienteRepository.findByNomeContainingAndActiveTrue(nome, pageable);
    }

    @Transactional(readOnly = true)
    public List<Paciente> buscarPorTermo(String termo) {
        return pacienteRepository.buscarPorTermo(termo);
    }

    @Transactional(readOnly = true)
    public Page<Paciente> buscarPorTermo(String termo, Pageable pageable) {
        return pacienteRepository.buscarPorTermo(termo, pageable);
    }

    @Transactional(readOnly = true)
    public List<Paciente> buscarAniversariantesDodia() {
        LocalDate hoje = LocalDate.now();
        return pacienteRepository.findAniversariantesDodia(hoje.getMonthValue(), hoje.getDayOfMonth());
    }

    @Transactional(readOnly = true)
    public List<Paciente> buscarAniversariantesDoMes(int mes) {
        return pacienteRepository.findAniversariantesDoMes(mes);
    }

    public void ativar(Long id) {
        Paciente paciente = buscarPorId(id);
        paciente.activate();
        pacienteRepository.save(paciente);
    }

    public void desativar(Long id) {
        Paciente paciente = buscarPorId(id);
        paciente.deactivate();
        pacienteRepository.save(paciente);
    }

    @Transactional(readOnly = true)
    public long contarPacientes() {
        return pacienteRepository.countByActiveTrue();
    }

    @Transactional(readOnly = true)
    public boolean cpfExiste(String cpf) {
        return pacienteRepository.existsByCpf(cpf);
    }
}


