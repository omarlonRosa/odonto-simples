package com.odontosimples.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class UsuarioDTO {

  private Long id;

  @NotBlank(message = "Nome é obrigatório")
  @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
  private String nome;


  @NotBlank(message = "Email é obrigatório")
  @Email(message = "Email deve ter formato válido")
  private String email;

  private String role;
  private String telefone;
  private Boolean active;

  public UsuarioDTO() {}

  public UsuarioDTO(Long id, String nome, String email, String role) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.role = role;
  }



   public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}


  //DTO para criar usuario 
  class UsuarioCreateDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 a 100 caracteres")
    private String nome;


    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    private String senha;

    private String role;
    private String telefone;


    public UsuarioCreateDTO() {}

    // Getters and Setters
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
  }


//DTO login 
class LoginDTO {

  @NotBlank(message = "Email é obrigatório")
  @Email(message = "Email deve ter fomato válido")
  private String email;

  @NotBlank(message = "Senha é obrigatória")
  private String senha;

  public LoginDTO() {}

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
}


class LoginResponseDTO {

  private String token;
  private String tipo;
  private UsuarioDTO usuario;


  public LoginResponseDTO() {}

  public LoginResponseDTO(String token, String tipo, UsuarioDTO usuario) {
    this.token = token;
    this.tipo = tipo;
    this.usuario = usuario;
  }


  public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }


}
  

