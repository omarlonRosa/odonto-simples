package com.odontosimples.repository;

import com.odontosimples.entity.Prontuario;
import com.odontosimples.entity.Paciente;
import com.odontosimples.entity.Dentista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {

    List<Prontuario> findByPacienteAndActiveTrue(Paciente paciente);

    List<Prontuario> findByDentistaAndActiveTrue(Dentista dentista);

    List<Prontuario> findByStatusTratamentoAndActiveTrue(Prontuario.StatusTratamento status);

    @Query("SELECT p FROM Prontuario p WHERE p.consulta.id = :consultaId AND p.active = true")
    Optional<Prontuario> findByConsultaId(@Param("consultaId") Long consultaId);

    @Query("SELECT p FROM Prontuario p WHERE DATE(p.dataConsulta) = :data AND p.active = true")
    List<Prontuario> findByDataConsulta(@Param("data") LocalDate data);

    @Query("SELECT p FROM Prontuario p WHERE p.dataConsulta BETWEEN :inicio AND :fim AND p.active = true")
    List<Prontuario> findByDataConsultaBetween(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    @Query("SELECT p FROM Prontuario p WHERE p.paciente = :paciente AND p.active = true ORDER BY p.dataConsulta DESC")
    List<Prontuario> findByPacienteOrderByDataConsultaDesc(@Param("paciente") Paciente paciente);

    @Query("SELECT p FROM Prontuario p WHERE p.dentista = :dentista AND p.active = true ORDER BY p.dataConsulta DESC")
    List<Prontuario> findByDentistaOrderByDataConsultaDesc(@Param("dentista") Dentista dentista);

    @Query("SELECT p FROM Prontuario p WHERE p.proximaConsulta = :data AND p.active = true")
    List<Prontuario> findByProximaConsulta(@Param("data") LocalDate data);

    @Query("SELECT p FROM Prontuario p WHERE p.proximaConsulta BETWEEN :inicio AND :fim AND p.active = true")
    List<Prontuario> findByProximaConsultaBetween(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    @Query("SELECT p FROM Prontuario p WHERE p.proximaConsulta < :data AND p.statusTratamento = 'EM_ANDAMENTO' AND p.active = true")
    List<Prontuario> findTratamentosAtrasados(@Param("data") LocalDate data);

    // Busca textual em campos do prontuário
    @Query("SELECT p FROM Prontuario p WHERE " +
           "(p.anamnese LIKE %:termo% OR p.diagnostico LIKE %:termo% OR " +
           "p.planoTratamento LIKE %:termo% OR p.procedimentosRealizados LIKE %:termo% OR " +
           "p.queixaPrincipal LIKE %:termo%) AND p.active = true")
    List<Prontuario> buscarPorTermo(@Param("termo") String termo);

    @Query("SELECT p FROM Prontuario p WHERE " +
           "(p.anamnese LIKE %:termo% OR p.diagnostico LIKE %:termo% OR " +
           "p.planoTratamento LIKE %:termo% OR p.procedimentosRealizados LIKE %:termo% OR " +
           "p.queixaPrincipal LIKE %:termo%) AND p.active = true")
    Page<Prontuario> buscarPorTermo(@Param("termo") String termo, Pageable pageable);

    // Estatísticas
    @Query("SELECT COUNT(p) FROM Prontuario p WHERE p.paciente = :paciente AND p.active = true")
    long countByPaciente(@Param("paciente") Paciente paciente);

    @Query("SELECT COUNT(p) FROM Prontuario p WHERE p.dentista = :dentista AND p.active = true")
    long countByDentista(@Param("dentista") Dentista dentista);

    @Query("SELECT COUNT(p) FROM Prontuario p WHERE p.statusTratamento = :status AND p.active = true")
    long countByStatusTratamento(@Param("status") Prontuario.StatusTratamento status);

    @Query("SELECT COUNT(p) FROM Prontuario p WHERE DATE(p.dataConsulta) BETWEEN :inicio AND :fim AND p.active = true")
    long countByPeriodo(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    // Busca por medicamentos e alergias
    @Query("SELECT p FROM Prontuario p WHERE p.medicamentosUso LIKE %:medicamento% AND p.active = true")
    List<Prontuario> findByMedicamento(@Param("medicamento") String medicamento);

    @Query("SELECT p FROM Prontuario p WHERE p.alergias LIKE %:alergia% AND p.active = true")
    List<Prontuario> findByAlergia(@Param("alergia") String alergia);

    // Último prontuário do paciente
    @Query("SELECT p FROM Prontuario p WHERE p.paciente = :paciente AND p.active = true ORDER BY p.dataConsulta DESC LIMIT 1")
    Optional<Prontuario> findUltimoProntuario(@Param("paciente") Paciente paciente);

    // Prontuários com prescrições
    @Query("SELECT p FROM Prontuario p WHERE p.prescricoes IS NOT NULL AND p.prescricoes != '' AND p.active = true")
    List<Prontuario> findComPrescricoes();

    @Query("SELECT p FROM Prontuario p WHERE p.prescricoes LIKE %:medicamento% AND p.active = true")
    List<Prontuario> findByPrescricao(@Param("medicamento") String medicamento);
}


