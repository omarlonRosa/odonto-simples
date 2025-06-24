package com.odontosimples.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ConsultaDTO {

    private Long id;

    @NotNull(message = "Paciente é obrigatório")
    private Long pacienteId;

    @NotNull(message = "Dentista é obrigatório")
    private Long dentistaId;

    @NotNull(message = "Data e hora são obrigatórias")
    private LocalDateTime dataHora;

    private String status;
    private String observacoes;

    @Positive(message = "Valor deve ser positivo")
    private BigDecimal valor;

    private Integer duracaoMinutos;
    private String tipoConsulta;
    private Boolean primeiraConsulta;
    private LocalDateTime dataReagendamento;
    private String motivoReagendamento;
    private Boolean active;


    private String pacienteNome;
    private String pacienteTelefone;
    private String dentistaNome;
    private String dentistaCro;

  
  // Constructors
    public ConsultaDTO() {}

  
  // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getDentistaId() {
        return dentistaId;
    }

    public void setDentistaId(Long dentistaId) {
        this.dentistaId = dentistaId;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public Boolean getPrimeiraConsulta() {
        return primeiraConsulta;
    }

    public void setPrimeiraConsulta(Boolean primeiraConsulta) {
        this.primeiraConsulta = primeiraConsulta;
    }

    public LocalDateTime getDataReagendamento() {
        return dataReagendamento;
    }

    public void setDataReagendamento(LocalDateTime dataReagendamento) {
        this.dataReagendamento = dataReagendamento;
    }

    public String getMotivoReagendamento() {
        return motivoReagendamento;
    }

    public void setMotivoReagendamento(String motivoReagendamento) {
        this.motivoReagendamento = motivoReagendamento;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPacienteNome() {
        return pacienteNome;
    }

    public void setPacienteNome(String pacienteNome) {
        this.pacienteNome = pacienteNome;
    }

    public String getPacienteTelefone() {
        return pacienteTelefone;
    }

    public void setPacienteTelefone(String pacienteTelefone) {
        this.pacienteTelefone = pacienteTelefone;
    }

    public String getDentistaNome() {
        return dentistaNome;
    }

    public void setDentistaNome(String dentistaNome) {
        this.dentistaNome = dentistaNome;
    }

    public String getDentistaCro() {
        return dentistaCro;
    }

    public void setDentistaCro(String dentistaCro) {
        this.dentistaCro = dentistaCro;
    }
}

// DTO para reagendamento
class ReagendamentoDTO {

    @NotNull(message = "Nova data e hora são obrigatórias")
    private LocalDateTime novaDataHora;

    private String motivo;

    // Constructors
    public ReagendamentoDTO() {}

    // Getters and Setters
    public LocalDateTime getNovaDataHora() {
        return novaDataHora;
    }

    public void setNovaDataHora(LocalDateTime novaDataHora) {
        this.novaDataHora = novaDataHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}


