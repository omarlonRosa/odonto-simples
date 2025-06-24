package com.odontosimples.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;
import java.util.List;

public class DentistaDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "CRO é obrigatório")
    @Pattern(regexp = "\\d{4,6}", message = "CRO deve conter entre 4 e 6 dígitos")
    private String cro;

    @NotBlank(message = "Estado do CRO é obrigatório")
    @Pattern(regexp = "[A-Z]{2}", message = "Estado do CRO deve conter 2 letras maiúsculas")
    private String croEstado;

    private List<String> especialidades;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    private String email;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
    private List<Integer> diasTrabalho;
    private Integer tempoConsulta;
    private String observacoes;
    private Boolean active;
    private String croCompleto;
    private Long usuarioId;

    // Constructors
    public DentistaDTO() {}

    // Getters and Setters
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

    public String getCro() {
        return cro;
    }

    public void setCro(String cro) {
        this.cro = cro;
    }

    public String getCroEstado() {
        return croEstado;
    }

    public void setCroEstado(String croEstado) {
        this.croEstado = croEstado;
    }

    public List<String> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<String> especialidades) {
        this.especialidades = especialidades;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public LocalTime getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(LocalTime horarioFim) {
        this.horarioFim = horarioFim;
    }

    public List<Integer> getDiasTrabalho() {
        return diasTrabalho;
    }

    public void setDiasTrabalho(List<Integer> diasTrabalho) {
        this.diasTrabalho = diasTrabalho;
    }

    public Integer getTempoConsulta() {
        return tempoConsulta;
    }

    public void setTempoConsulta(Integer tempoConsulta) {
        this.tempoConsulta = tempoConsulta;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCroCompleto() {
        return croCompleto;
    }

    public void setCroCompleto(String croCompleto) {
        this.croCompleto = croCompleto;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}


