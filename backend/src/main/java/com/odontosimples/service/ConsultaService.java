package com.odontosimples.service;

import com.odontosimples.entity.Consulta;
import com.odontosimples.entity.Dentista;
import com.odontosimples.entity.Paciente;
import com.odontosimples.repository.ConsultaRepository;
import com.odontosimples.repository.DentistaRepository;
import com.odontosimples.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
public class ConsultaService {

  @Autowired
  private ConsultaRepository consultaRository;

  @Autowired
  private PacienteRepository pacienteRepository;

  @Autowired
  private DentistaRepository dentistaRepository;

  public Consulta agendarConsulta(Consulta consulta) {

    //Validar se paciente e dentista existem 

    Paciente paciente = pacienteRepository.findById(consulta.getPaciente().getId())
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

    Dentista dentista =dentistaRepository.findById(consulta.getDentista().getId())
          .orElseThrow(() -> new RuntimeException("Dentista não encontrado"));

    //Verificar conflitos de agendamento 
    LocalDateTime inicio = consulta.getDataHora();
    LocalDateTime fim = inicio.plusMinutes(consulta.getDuracaoMinutos());

    List<Consulta> conflitos = consultaRository.findConflitosAgendamento(dentista, inicio, fim);
    if (!conflitos.isEmpty()) {
      throw new RuntimeException("Horário não disponível para este dentista");
    }

    consulta.setPaciente(paciente);
    consulta.setDentista(dentista);

    return consultaRository.save(consulta);
  }


  
}
