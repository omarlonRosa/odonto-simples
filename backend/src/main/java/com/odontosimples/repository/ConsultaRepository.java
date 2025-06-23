package com.odontosimples.repository;

import com.odontosimples.entity.Consulta;
import com.odontosimples.entity.Dentista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    // Buscar consultas por data
    @Query("SELECT c FROM Consulta c WHERE DATE(c.dataHora) = :data AND c.active = true ORDER BY c.dataHora")
    List<Consulta> findByData(@Param("data") LocalDate data);

    // Buscar consultas por dentista e data
    @Query("SELECT c FROM Consulta c WHERE c.dentista = :dentista AND DATE(c.dataHora) = :data AND c.active = true ORDER BY c.dataHora")
    List<Consulta> findByDentistaAndData(@Param("dentista") Dentista dentista, @Param("data") LocalDate data);

    // Verificar conflitos de agendamento - versão simplificada
    @Query("SELECT c FROM Consulta c WHERE c.dentista = :dentista " +
           "AND c.dataHora >= :inicio AND c.dataHora < :fim " +
           "AND c.status IN ('AGENDADA', 'CONFIRMADA') AND c.active = true")
    List<Consulta> findConflitosAgendamento(
        @Param("dentista") Dentista dentista,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );

    // Verificar conflitos para reagendamento
    @Query("SELECT c FROM Consulta c WHERE c.id != :id AND c.dentista = :dentista " +
           "AND c.dataHora >= :inicio AND c.dataHora < :fim " +
           "AND c.status IN ('AGENDADA', 'CONFIRMADA') AND c.active = true")
    List<Consulta> findConflitosReagendamento(
        @Param("id") Long id,
        @Param("dentista") Dentista dentista,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );

    // Buscar consultas por paciente
    @Query("SELECT c FROM Consulta c WHERE c.paciente.id = :pacienteId AND c.active = true ORDER BY c.dataHora DESC")
    List<Consulta> findByPacienteId(@Param("pacienteId") Long pacienteId);

    // Buscar consultas por dentista
    @Query("SELECT c FROM Consulta c WHERE c.dentista.id = :dentistaId AND c.active = true ORDER BY c.dataHora DESC")
    Page<Consulta> findByDentistaId(@Param("dentistaId") Long dentistaId, Pageable pageable);

    // Buscar consultas por status
    @Query("SELECT c FROM Consulta c WHERE c.status = :status AND c.active = true ORDER BY c.dataHora")
    List<Consulta> findByStatus(@Param("status") Consulta.StatusConsulta status);

    // Buscar consultas do dia por status
    @Query("SELECT c FROM Consulta c WHERE DATE(c.dataHora) = :data AND c.status = :status AND c.active = true ORDER BY c.dataHora")
    List<Consulta> findByDataAndStatus(@Param("data") LocalDate data, @Param("status") Consulta.StatusConsulta status);

    // Buscar consultas entre datas
    @Query("SELECT c FROM Consulta c WHERE c.dataHora BETWEEN :inicio AND :fim AND c.active = true ORDER BY c.dataHora")
    List<Consulta> findByDataHoraBetween(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    // Buscar consultas por período e dentista
    @Query("SELECT c FROM Consulta c WHERE c.dentista = :dentista " +
           "AND c.dataHora BETWEEN :inicio AND :fim AND c.active = true ORDER BY c.dataHora")
    List<Consulta> findByDentistaAndDataHoraBetween(
        @Param("dentista") Dentista dentista,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );

    // Contar consultas por status
    @Query("SELECT COUNT(c) FROM Consulta c WHERE c.status = :status AND c.active = true")
    Long countByStatus(@Param("status") Consulta.StatusConsulta status);

    // Contar consultas do dia
    @Query("SELECT COUNT(c) FROM Consulta c WHERE DATE(c.dataHora) = :data AND c.active = true")
    Long countByData(@Param("data") LocalDate data);

    // Buscar próximas consultas do paciente
    @Query("SELECT c FROM Consulta c WHERE c.paciente.id = :pacienteId " +
           "AND c.dataHora > CURRENT_TIMESTAMP AND c.status IN ('AGENDADA', 'CONFIRMADA') " +
           "AND c.active = true ORDER BY c.dataHora")
    List<Consulta> findProximasConsultasPaciente(@Param("pacienteId") Long pacienteId);

    // Buscar consultas para confirmação (próximas 24h)
    @Query("SELECT c FROM Consulta c WHERE c.status = 'AGENDADA' " +
           "AND c.dataHora BETWEEN CURRENT_TIMESTAMP AND :limite " +
           "AND c.active = true ORDER BY c.dataHora")
    List<Consulta> findConsultasParaConfirmacao(@Param("limite") LocalDateTime limite);

    // Relatório de consultas por período
    @Query("SELECT c FROM Consulta c WHERE DATE(c.dataHora) BETWEEN :dataInicio AND :dataFim " +
           "AND c.active = true ORDER BY c.dataHora")
    List<Consulta> findRelatorioConsultas(
        @Param("dataInicio") LocalDate dataInicio,
        @Param("dataFim") LocalDate dataFim
    );

    // Buscar consultas realizadas para faturamento
    @Query("SELECT c FROM Consulta c WHERE c.status = 'REALIZADA' " +
           "AND DATE(c.dataHora) BETWEEN :dataInicio AND :dataFim " +
           "AND c.active = true ORDER BY c.dataHora")
    List<Consulta> findConsultasRealizadas(
        @Param("dataInicio") LocalDate dataInicio,
        @Param("dataFim") LocalDate dataFim
    );
}


