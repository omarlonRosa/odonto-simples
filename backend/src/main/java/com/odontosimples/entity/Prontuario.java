package com.odontosimples.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "prontuarios")
public class Prontuario extends BaseEntity {

    @NotNull(message = "Paciente é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @NotNull(message = "Dentista é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dentista_id", nullable = false)
    private Dentista dentista;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id")
    private Consulta consulta;

    @NotNull(message = "Data da consulta é obrigatória")
    @Column(name = "data_consulta", nullable = false)
    private LocalDate dataConsulta;

    @Column(name = "anamnese", columnDefinition = "TEXT")
    private String anamnese;

    @Column(name = "exame_clinico", columnDefinition = "TEXT")
    private String exameClinico;

    @Column(name = "diagnostico", columnDefinition = "TEXT")
    private String diagnostico;

    @Column(name = "plano_tratamento", columnDefinition = "TEXT")
    private String planoTratamento;

    @Column(name = "procedimentos_realizados", columnDefinition = "TEXT")
    private String procedimentosRealizados;

    @Column(name = "prescricoes", columnDefinition = "TEXT")
    private String prescricoes;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "proxima_consulta")
    private LocalDate proximaConsulta;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_tratamento")
    private StatusTratamento statusTratamento = StatusTratamento.EM_ANDAMENTO;

    // Campos específicos para anamnese
    @Column(name = "queixa_principal", columnDefinition = "TEXT")
    private String queixaPrincipal;

    @Column(name = "historia_doenca_atual", columnDefinition = "TEXT")
    private String historiaDoencaAtual;

    @Column(name = "historia_medica", columnDefinition = "TEXT")
    private String historiaMedica;

    @Column(name = "medicamentos_uso", columnDefinition = "TEXT")
    private String medicamentosUso;

    @Column(name = "alergias", columnDefinition = "TEXT")
    private String alergias;

    @Column(name = "habitos", columnDefinition = "TEXT")
    private String habitos;

    // Campos para exame clínico
    @Column(name = "pressao_arterial", length = 20)
    private String pressaoArterial;

    @Column(name = "temperatura", length = 10)
    private String temperatura;

    @Column(name = "exame_extraoral", columnDefinition = "TEXT")
    private String exameExtraoral;

    @Column(name = "exame_intraoral", columnDefinition = "TEXT")
    private String exameIntraoral;

    @Column(name = "odontograma", columnDefinition = "TEXT")
    private String odontograma;

    // Constructors
    public Prontuario() {}

    public Prontuario(Paciente paciente, Dentista dentista, LocalDate dataConsulta) {
        this.paciente = paciente;
        this.dentista = dentista;
        this.dataConsulta = dataConsulta;
    }

    // Getters and Setters
    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Dentista getDentista() {
        return dentista;
    }

    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public LocalDate getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDate dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getAnamnese() {
        return anamnese;
    }

    public void setAnamnese(String anamnese) {
        this.anamnese = anamnese;
    }

    public String getExameClinico() {
        return exameClinico;
    }

    public void setExameClinico(String exameClinico) {
        this.exameClinico = exameClinico;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getPlanoTratamento() {
        return planoTratamento;
    }

    public void setPlanoTratamento(String planoTratamento) {
        this.planoTratamento = planoTratamento;
    }

    public String getProcedimentosRealizados() {
        return procedimentosRealizados;
    }

    public void setProcedimentosRealizados(String procedimentosRealizados) {
        this.procedimentosRealizados = procedimentosRealizados;
    }

    public String getPrescricoes() {
        return prescricoes;
    }

    public void setPrescricoes(String prescricoes) {
        this.prescricoes = prescricoes;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public LocalDate getProximaConsulta() {
        return proximaConsulta;
    }

    public void setProximaConsulta(LocalDate proximaConsulta) {
        this.proximaConsulta = proximaConsulta;
    }

    public StatusTratamento getStatusTratamento() {
        return statusTratamento;
    }

    public void setStatusTratamento(StatusTratamento statusTratamento) {
        this.statusTratamento = statusTratamento;
    }

    public String getQueixaPrincipal() {
        return queixaPrincipal;
    }

    public void setQueixaPrincipal(String queixaPrincipal) {
        this.queixaPrincipal = queixaPrincipal;
    }

    public String getHistoriaDoencaAtual() {
        return historiaDoencaAtual;
    }

    public void setHistoriaDoencaAtual(String historiaDoencaAtual) {
        this.historiaDoencaAtual = historiaDoencaAtual;
    }

    public String getHistoriaMedica() {
        return historiaMedica;
    }

    public void setHistoriaMedica(String historiaMedica) {
        this.historiaMedica = historiaMedica;
    }

    public String getMedicamentosUso() {
        return medicamentosUso;
    }

    public void setMedicamentosUso(String medicamentosUso) {
        this.medicamentosUso = medicamentosUso;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getHabitos() {
        return habitos;
    }

    public void setHabitos(String habitos) {
        this.habitos = habitos;
    }

    public String getPressaoArterial() {
        return pressaoArterial;
    }

    public void setPressaoArterial(String pressaoArterial) {
        this.pressaoArterial = pressaoArterial;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getExameExtraoral() {
        return exameExtraoral;
    }

    public void setExameExtraoral(String exameExtraoral) {
        this.exameExtraoral = exameExtraoral;
    }

    public String getExameIntraoral() {
        return exameIntraoral;
    }

    public void setExameIntraoral(String exameIntraoral) {
        this.exameIntraoral = exameIntraoral;
    }

    public String getOdontograma() {
        return odontograma;
    }

    public void setOdontograma(String odontograma) {
        this.odontograma = odontograma;
    }

    // Business methods
    public boolean isTratamentoConcluido() {
        return StatusTratamento.CONCLUIDO.equals(statusTratamento);
    }

    public boolean isTratamentoEmAndamento() {
        return StatusTratamento.EM_ANDAMENTO.equals(statusTratamento);
    }

    public void concluirTratamento() {
        this.statusTratamento = StatusTratamento.CONCLUIDO;
    }

    public void suspenderTratamento() {
        this.statusTratamento = StatusTratamento.SUSPENSO;
    }

    public boolean temProximaConsulta() {
        return proximaConsulta != null && proximaConsulta.isAfter(LocalDate.now());
    }

    // Enums
    public enum StatusTratamento {
        EM_ANDAMENTO("Em Andamento"),
        CONCLUIDO("Concluído"),
        SUSPENSO("Suspenso"),
        CANCELADO("Cancelado");

        private final String descricao;

        StatusTratamento(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }
}


