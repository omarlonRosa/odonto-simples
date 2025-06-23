

package com.odontosimples.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Embeddable
public class Endereco {

  @Column(name = "cep", length = 8)
  @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 digítos")
  private String cep;

  @Column(name = "logradouro", length = 200)
  @Size(max = 200, message = "Logradouro deve ter no máximo 200 caracteres")
  private String logradouro;

  @Column(name = "numero", length = 10)
  private String numero;

  @Column(name = "Complemento", length = 100)
  private String complemento;

  @Column(name = "bairro", length = 100)
  private String bairro;

  @Column(name = "cidade", length = 100)
  private String cidade;

  @Column(name = "estado", length = 2)
  @Pattern(regexp = "[A-Z]{2}", message = "Estado deve conter 2 letras maiúsculas")
  private String estado;

  public Endereco() {}

  public Endereco(String cep, String logradouro, String numero, String bairro, String cidade, String estado) {
    this.cep = cep;
    this.logradouro = logradouro;
    this.numero = numero;
    this.bairro = bairro;
    this.cidade = cidade;
    this.estado = estado;
  }



  public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


  public String getEnderecoCompleto() {
    StringBuilder sb = new StringBuilder();

    if (logradouro != null && !logradouro.trim().isEmpty()) {
            sb.append(logradouro);
        }

    if (numero != null && !numero.trim().isEmpty()){
      sb.append(", ").append(numero);
    }

    if (complemento != null && !complemento.trim().isEmpty()) {
      sb.append(" - ").append(complemento);
    }

    if (bairro != null && !bairro.trim().isEmpty()) {
      sb.append(", ").append(bairro);
    }

    if (cidade != null && !cidade.trim().isEmpty()) {
      sb.append(", ").append(cidade);
    }

    if(estado != null && !estado.trim().isEmpty()) {
      sb.append(" - ").append(estado);
    }

    if (cep != null && !estado.trim().isEmpty()){
      sb.append(", CEP: ").append(formatarCep());
    }

    return sb.toString();
  }

  public String formatarCep() {
    if (cep == null || cep.length() != 8) {
      return cep;
    }
    return cep.substring(0, 5) + "-" + cep.substring(5);
  }

   public boolean isCompleto() {
        return cep != null && !cep.trim().isEmpty() &&
               logradouro != null && !logradouro.trim().isEmpty() &&
               bairro != null && !bairro.trim().isEmpty() &&
               cidade != null && !cidade.trim().isEmpty() &&
               estado != null && !estado.trim().isEmpty();
    }

}
