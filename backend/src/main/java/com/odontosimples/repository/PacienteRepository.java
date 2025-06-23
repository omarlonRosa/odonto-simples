package com.odontosimples.repository;

import com.odontosimples.entity.Paciente;
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
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByCpf(String cpf);

    Optional<Paciente> findByCpfAndActiveTrue(String cpf);

    boolean existsByCpf(String cpf);

    List<Paciente> findByActiveTrue();

    Page<Paciente> findByActiveTrue(Pageable pageable);

    @Query("SELECT p FROM Paciente p WHERE p.nome LIKE %:nome% AND p.active = true")
    List<Paciente> findByNomeContainingAndActiveTrue(@Param("nome") String nome);

    @Query("SELECT p FROM Paciente p WHERE p.nome LIKE %:nome% AND p.active = true")
    Page<Paciente> findByNomeContainingAndActiveTrue(@Param("nome") String nome, Pageable pageable);

    @Query("SELECT p FROM Paciente p WHERE p.telefone = :telefone AND p.active = true")
    List<Paciente> findByTelefoneAndActiveTrue(@Param("telefone") String telefone);

    @Query("SELECT p FROM Paciente p WHERE p.email = :email AND p.active = true")
    Optional<Paciente> findByEmailAndActiveTrue(@Param("email") String email);

    @Query("SELECT p FROM Paciente p WHERE p.dataNascimento = :data AND p.active = true")
    List<Paciente> findByDataNascimentoAndActiveTrue(@Param("data") LocalDate data);

    @Query("SELECT p FROM Paciente p WHERE p.dataNascimento BETWEEN :dataInicio AND :dataFim AND p.active = true")
    List<Paciente> findByDataNascimentoBetweenAndActiveTrue(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim);

    @Query("SELECT p FROM Paciente p WHERE " +
           "(p.nome LIKE %:termo% OR p.cpf LIKE %:termo% OR p.telefone LIKE %:termo% OR p.email LIKE %:termo%) " +
           "AND p.active = true")
    List<Paciente> buscarPorTermo(@Param("termo") String termo);

    @Query("SELECT p FROM Paciente p WHERE " +
           "(p.nome LIKE %:termo% OR p.cpf LIKE %:termo% OR p.telefone LIKE %:termo% OR p.email LIKE %:termo%) " +
           "AND p.active = true")
    Page<Paciente> buscarPorTermo(@Param("termo") String termo, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Paciente p WHERE p.active = true")
    long countByActiveTrue();

    @Query("SELECT p FROM Paciente p WHERE MONTH(p.dataNascimento) = :mes AND DAY(p.dataNascimento) = :dia AND p.active = true")
    List<Paciente> findAniversariantesDodia(@Param("mes") int mes, @Param("dia") int dia);

    @Query("SELECT p FROM Paciente p WHERE MONTH(p.dataNascimento) = :mes AND p.active = true")
    List<Paciente> findAniversariantesDoMes(@Param("mes") int mes);
}


