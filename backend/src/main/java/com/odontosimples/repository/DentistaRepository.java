package com.odontosimples.repository;

import com.odontosimples.entity.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DentistaRepository extends JpaRepository<Dentista, Long> {

    Optional<Dentista> findByCro(String cro);

    Optional<Dentista> findByCroAndActiveTrue(String cro);

    boolean existsByCro(String cro);

    List<Dentista> findByActiveTrue();

    @Query("SELECT d FROM Dentista d WHERE d.nome LIKE %:nome% AND d.active = true")
    List<Dentista> findByNomeContainingAndActiveTrue(@Param("nome") String nome);

    @Query("SELECT d FROM Dentista d WHERE d.especialidades LIKE %:especialidade% AND d.active = true")
    List<Dentista> findByEspecialidadeAndActiveTrue(@Param("especialidade") String especialidade);

    @Query("SELECT d FROM Dentista d WHERE d.telefone = :telefone AND d.active = true")
    Optional<Dentista> findByTelefoneAndActiveTrue(@Param("telefone") String telefone);

    @Query("SELECT d FROM Dentista d WHERE d.email = :email AND d.active = true")
    Optional<Dentista> findByEmailAndActiveTrue(@Param("email") String email);

    @Query("SELECT d FROM Dentista d WHERE d.croEstado = :estado AND d.active = true")
    List<Dentista> findByCroEstadoAndActiveTrue(@Param("estado") String estado);

    @Query("SELECT d FROM Dentista d WHERE " +
           "d.horarioInicio <= :horario AND d.horarioFim >= :horario AND " +
           "d.diasTrabalho LIKE %:diaSemana% AND d.active = true")
    List<Dentista> findDisponiveis(@Param("horario") LocalTime horario, @Param("diaSemana") String diaSemana);

    @Query("SELECT d FROM Dentista d WHERE d.usuario.id = :usuarioId AND d.active = true")
    Optional<Dentista> findByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query("SELECT COUNT(d) FROM Dentista d WHERE d.active = true")
    long countByActiveTrue();

    @Query("SELECT d FROM Dentista d WHERE " +
           "(d.nome LIKE %:termo% OR d.cro LIKE %:termo% OR d.especialidades LIKE %:termo%) " +
           "AND d.active = true")
    List<Dentista> buscarPorTermo(@Param("termo") String termo);

    @Query("SELECT DISTINCT d.especialidades FROM Dentista d WHERE d.active = true AND d.especialidades IS NOT NULL")
    List<String> findAllEspecialidades();
}


