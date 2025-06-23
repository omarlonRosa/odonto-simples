package com.odontosimples.repository;

import com.odontosimples.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByEmailAndActiveTrue(String email);

    boolean existsByEmail(String email);

    List<Usuario> findByActiveTrue();

    List<Usuario> findByRole(Usuario.Role role);

    List<Usuario> findByRoleAndActiveTrue(Usuario.Role role);

    @Query("SELECT u FROM Usuario u WHERE u.nome LIKE %:nome% AND u.active = true")
    List<Usuario> findByNomeContainingAndActiveTrue(@Param("nome") String nome);

    @Query("SELECT u FROM Usuario u WHERE u.email LIKE %:email% AND u.active = true")
    List<Usuario> findByEmailContainingAndActiveTrue(@Param("email") String email);

    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.role = :role AND u.active = true")
    long countByRoleAndActiveTrue(@Param("role") Usuario.Role role);

    @Query("SELECT u FROM Usuario u WHERE u.telefone = :telefone AND u.active = true")
    Optional<Usuario> findByTelefoneAndActiveTrue(@Param("telefone") String telefone);
}


