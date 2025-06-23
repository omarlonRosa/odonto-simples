package com.odontosimples.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
public class Consulta extends BaseEntity {

    @NotNull(message = "Paciente é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @NotNull(message = "Dentista é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dentista_id", nullable = false)
    private Dentista dentista;

    @NotNull(message = "Data e hora são obrigatórias")
    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusConsulta status = StatusConsulta.AGENDADA;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Positive(message = "Valor deve ser positivo")
    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "duracao_minutos")
    private Integer duracaoMinutos = 30;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_consulta")
    private TipoConsulta tipoConsulta;

    @Column(name = "primeira_consulta")
    private Boolean primeiraConsulta = false;

    @Column(name = "data_reagendamento")
    private LocalDateTime dataReagendamento;

    @Column(name = "motivo_reagendamento", length = 500)
    private String motivoReagendamento;

    // Relacionamento com prontuário (uma consulta pode gerar um prontuário)
    @OneToOne(mappedBy = "consulta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Prontuario prontuario;

    // Relacionamento com pagamento
    @OneToOne(mappedBy = "consulta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Pagamento pagamento;

    // Constructors
    public Consulta() {}

    public Consulta(Paciente paciente, Dentista dentista, LocalDateTime dataHora) {
        this.paciente = paciente;
        this.dentista = dentista;
        this.dataHora = dataHora;
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

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public StatusConsulta getStatus() {
        return status;
    }

    public void setStatus(StatusConsulta status) {
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

    public TipoConsulta getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(TipoConsulta tipoConsulta) {
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

    public Prontuario getProntuario() {
        return prontuario;
    }

    public void setProntuario(Prontuario prontuario) {
        this.prontuario = prontuario;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    // Business methods
    public LocalDateTime getDataHoraFim() {
        if (dataHora == null || duracaoMinutos == null) {
            return dataHora;
        }
        return dataHora.plusMinutes(duracaoMinutos);
    }

    public boolean isRealizada() {
        return StatusConsulta.REALIZADA.equals(status);
    }

    public boolean isCancelada() {
        return StatusConsulta.CANCELADA.equals(status);
    }

    public boolean isAgendada() {
        return StatusConsulta.AGENDADA.equals(status);
    }

    public void confirmar() {
        this.status = StatusConsulta.CONFIRMADA;
    }

    public void realizar() {
        this.status = StatusConsulta.REALIZADA;
    }

    public void cancelar() {
        this.status = StatusConsulta.CANCELADA;
    }

    public void reagendar(LocalDateTime novaDataHora, String motivo) {
        this.dataReagendamento = this.dataHora;
        this.dataHora = novaDataHora;
        this.motivoReagendamento = motivo;
        this.status = StatusConsulta.REAGENDADA;
    }

    // Enums
    public enum StatusConsulta {
        AGENDADA("Agendada"),
        CONFIRMADA("Confirmada"),
        REALIZADA("Realizada"),
        CANCELADA("Cancelada"),
        REAGENDADA("Reagendada"),
        FALTOU("Paciente Faltou");

        private final String descricao;

        StatusConsulta(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    public enum TipoConsulta {
        CONSULTA("Consulta"),
        AVALIACAO("Avaliação"),
        LIMPEZA("Limpeza"),
        RESTAURACAO("Restauração"),
        EXTRACAO("Extração"),
        CANAL("Tratamento de Canal"),
        ORTODONTIA("Ortodontia"),
        IMPLANTE("Implante"),
        CIRURGIA("Cirurgia"),
        EMERGENCIA("Emergência");

        private final String descricao;

        TipoConsulta(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }
}


