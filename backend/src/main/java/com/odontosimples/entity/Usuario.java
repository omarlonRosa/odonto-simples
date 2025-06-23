package com.odontosimples.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "usuarios")
public class Usuario extends BaseEntity implements UserDetails{

  @NotBlank(message = "Nome é obrigatório")
  @Size(min = 2, max = 100, message = "Nome deve entre 2 a 100 caracteres")
  @Column(name = "nome", nullable = false, length = 100)
  private String nome;


  @NotBlank(message = "Email é obrigatório")
  @Email(message = "Email deve ter formato válido")
  @Column(name = "email", nullable = false, unique = true, length = 150)
  private String email;

  @NotBlank(message = "Senha é obrigatória")
  @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
  @Column(name = "senha", nullable = false)
  private String senha;


  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private Role role = Role.USUARIO;

  @Column(name = "telefone", length = 20)
  private String telefone;


  public Usuario() {}

  public Usuario(String nome, String email, String senha, Role role) {
    this.nome = nome;
    this.email = email;
    this.senha = senha;
    this.role = role;
  }


   @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

  @Override 
  public String getPassword() {
    return senha;
  }

  @Override 
  public String getUsername() {
    return email;
  }

  @Override 
  public boolean isAccountNonExpired(){
    return true;
  }

  @Override 
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override 
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override 
  public boolean isEnabled() {
    return isActive();
  }

  public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }



  public boolean isAdmin() {
    return Role.ADMIN.equals(this.role);
  }

  public boolean isDentista(){
    return Role.DENTISTA.equals(this.role);
  }

  public boolean isRecepcionista() {
    return Role.RECEPCIONISTA.equals(this.role);
  }



  public enum Role {
    ADMIN("Administrador"),
    DENTISTA("Dentista"),
    RECEPCIONISTA("Recepcionista"),
    USUARIO("Usuário");

    private final String descricao;

    Role(String descricao) {
      this.descricao = descricao;
    }

    public String getDescricao() {
      return descricao; 
    }

  }


}
