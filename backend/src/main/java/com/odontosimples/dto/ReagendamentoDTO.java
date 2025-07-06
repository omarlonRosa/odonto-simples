package com.odontosimples.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class ReagendamentoDTO {

    private Long id;

    @NotNull(message = "ID da consulta é obrigatório")
    private Long consultaId;

    @NotNull(message = "Nova data e hora são obrigatórias")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime novaDataHora;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataHoraAnterior;

    @NotNull(message = "Motivo do reagendamento é obrigatório")
    @Size(min = 10, max = 500, message = "Motivo deve ter entre 10 e 500 caracteres")
    private String motivo;

    private String solicitadoPor; // PACIENTE, DENTISTA, CLINICA

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataReagendamento;

    private Long usuarioReagendamentoId;
    private String usuarioReagendamentoNome;

    private String observacoes;

    // Dados da consulta para contexto
    private String pacienteNome;
    private String dentistaNome;
    private String tipoConsulta;
    private Integer duracaoMinutos;

    // Status do reagendamento
    private StatusReagendamento status = StatusReagendamento.PENDENTE;

    // Enum para status do reagendamento
    public enum StatusReagendamento {
        PENDENTE("Pendente"),
        APROVADO("Aprovado"),
        REJEITADO("Rejeitado"),
        CANCELADO("Cancelado");

        private final String descricao;

        StatusReagendamento(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    // Construtores
    public ReagendamentoDTO() {}

    public ReagendamentoDTO(Long consultaId, LocalDateTime novaDataHora, String motivo) {
        this.consultaId = consultaId;
        this.novaDataHora = novaDataHora;
        this.motivo = motivo;
        this.dataReagendamento = LocalDateTime.now();
    }

    public ReagendamentoDTO(Long consultaId, LocalDateTime dataHoraAnterior, 
                           LocalDateTime novaDataHora, String motivo, String solicitadoPor) {
        this.consultaId = consultaId;
        this.dataHoraAnterior = dataHoraAnterior;
        this.novaDataHora = novaDataHora;
        this.motivo = motivo;
        this.solicitadoPor = solicitadoPor;
        this.dataReagendamento = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(Long consultaId) {
        this.consultaId = consultaId;
    }

    public LocalDateTime getNovaDataHora() {
        return novaDataHora;
    }

    public void setNovaDataHora(LocalDateTime novaDataHora) {
        this.novaDataHora = novaDataHora;
    }

    public LocalDateTime getDataHoraAnterior() {
        return dataHoraAnterior;
    }

    public void setDataHoraAnterior(LocalDateTime dataHoraAnterior) {
        this.dataHoraAnterior = dataHoraAnterior;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getSolicitadoPor() {
        return solicitadoPor;
    }

    public void setSolicitadoPor(String solicitadoPor) {
        this.solicitadoPor = solicitadoPor;
    }

    public LocalDateTime getDataReagendamento() {
        return dataReagendamento;
    }

    public void setDataReagendamento(LocalDateTime dataReagendamento) {
        this.dataReagendamento = dataReagendamento;
    }

    public Long getUsuarioReagendamentoId() {
        return usuarioReagendamentoId;
    }

    public void setUsuarioReagendamentoId(Long usuarioReagendamentoId) {
        this.usuarioReagendamentoId = usuarioReagendamentoId;
    }

    public String getUsuarioReagendamentoNome() {
        return usuarioReagendamentoNome;
    }

    public void setUsuarioReagendamentoNome(String usuarioReagendamentoNome) {
        this.usuarioReagendamentoNome = usuarioReagendamentoNome;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getPacienteNome() {
        return pacienteNome;
    }

    public void setPacienteNome(String pacienteNome) {
        this.pacienteNome = pacienteNome;
    }

    public String getDentistaNome() {
        return dentistaNome;
    }

    public void setDentistaNome(String dentistaNome) {
        this.dentistaNome = dentistaNome;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public StatusReagendamento getStatus() {
        return status;
    }

    public void setStatus(StatusReagendamento status) {
        this.status = status;
    }

    // Métodos utilitários
    public boolean isPendente() {
        return StatusReagendamento.PENDENTE.equals(status);
    }

    public boolean isAprovado() {
        return StatusReagendamento.APROVADO.equals(status);
    }

    public boolean isRejeitado() {
        return StatusReagendamento.REJEITADO.equals(status);
    }

    public boolean isCancelado() {
        return StatusReagendamento.CANCELADO.equals(status);
    }

    public String getStatusDescricao() {
        return status != null ? status.getDescricao() : "";
    }

    public boolean isReagendamentoRecente() {
        if (dataReagendamento == null) return false;
        return dataReagendamento.isAfter(LocalDateTime.now().minusHours(24));
    }

    public boolean isNovaDataFutura() {
        return novaDataHora != null && novaDataHora.isAfter(LocalDateTime.now());
    }

    public boolean isNovaDataValida() {
        return isNovaDataFutura() && 
               (dataHoraAnterior == null || !novaDataHora.equals(dataHoraAnterior));
    }

    public String getResumoReagendamento() {
        return String.format("Consulta de %s com Dr(a) %s reagendada de %s para %s. Motivo: %s",
                pacienteNome != null ? pacienteNome : "N/A",
                dentistaNome != null ? dentistaNome : "N/A",
                dataHoraAnterior != null ? dataHoraAnterior.toString() : "N/A",
                novaDataHora != null ? novaDataHora.toString() : "N/A",
                motivo != null ? motivo : "N/A");
    }

    // Validações de negócio
    public boolean isValidoParaAprovacao() {
        return isPendente() && 
               isNovaDataValida() && 
               motivo != null && 
               !motivo.trim().isEmpty();
    }

    public boolean podeSerCancelado() {
        return isPendente() || isAprovado();
    }

    public boolean podeSerEditado() {
        return isPendente();
    }

    @Override
    public String toString() {
        return "ReagendamentoDTO{" +
                "id=" + id +
                ", consultaId=" + consultaId +
                ", pacienteNome='" + pacienteNome + '\'' +
                ", dentistaNome='" + dentistaNome + '\'' +
                ", dataHoraAnterior=" + dataHoraAnterior +
                ", novaDataHora=" + novaDataHora +
                ", motivo='" + motivo + '\'' +
                ", status=" + status +
                ", solicitadoPor='" + solicitadoPor + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReagendamentoDTO that = (ReagendamentoDTO) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}


