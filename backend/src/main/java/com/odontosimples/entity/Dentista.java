package com.odontosimples.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dentistas")
public class Dentista extends BaseEntity {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "CRO é obrigatório")
    @Pattern(regexp = "\\d{4,6}", message = "CRO deve conter entre 4 e 6 dígitos")
    @Column(name = "cro", nullable = false, unique = true, length = 10)
    private String cro;

    @NotBlank(message = "Estado do CRO é obrigatório")
    @Pattern(regexp = "[A-Z]{2}", message = "Estado do CRO deve conter 2 letras maiúsculas")
    @Column(name = "cro_estado", nullable = false, length = 2)
    private String croEstado;

    @Column(name = "especialidades", length = 500)
    private String especialidades;

    @NotBlank(message = "Telefone é obrigatório")
    @Column(name = "telefone", nullable = false, length = 20)
    private String telefone;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "horario_inicio")
    private LocalTime horarioInicio;

    @Column(name = "horario_fim")
    private LocalTime horarioFim;

    @Column(name = "dias_trabalho", length = 20)
    private String diasTrabalho; // Ex: "1 a 5" (Segunda a Sexta)

    @Column(name = "tempo_consulta")
    private Integer tempoConsulta = 30; 

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    


  // Relacionamento com usuáusurio
  // um dentista pode ter um usuário do sistema
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Relacionamentos
    @OneToMany(mappedBy = "dentista", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consulta> consultas = new ArrayList<>();

    @OneToMany(mappedBy = "dentista", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prontuario> prontuarios = new ArrayList<>();

    



    public Dentista() {}

    public Dentista(String nome, String cro, String croEstado, String telefone) {
        this.nome = nome;
        this.cro = cro;
        this.croEstado = croEstado;
        this.telefone = telefone;
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

    public String getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(String especialidades) {
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

    public String getDiasTrabalho() {
        return diasTrabalho;
    }

    public void setDiasTrabalho(String diasTrabalho) {
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public List<Prontuario> getProntuarios() {
        return prontuarios;
    }

    public void setProntuarios(List<Prontuario> prontuarios) {
        this.prontuarios = prontuarios;
    }





    public String getCroCompleto() {
        return "CRO-" + croEstado + " " + cro;
    }

    public void adicionarConsulta(Consulta consulta) {
        consultas.add(consulta);
        consulta.setDentista(this);
    }

    public void adicionarProntuario(Prontuario prontuario) {
        prontuarios.add(prontuario);
        prontuario.setDentista(this);
    }

    public boolean trabalhaNodia(int diaSemana) {
        if (diasTrabalho == null || diasTrabalho.trim().isEmpty()) {
            return false;
        }
        return diasTrabalho.contains(String.valueOf(diaSemana));
    }

    public boolean isDisponivel(LocalTime horario) {
        if (horarioInicio == null || horarioFim == null) {
            return false;
        }
        return !horario.isBefore(horarioInicio) && !horario.isAfter(horarioFim);
    }

    public List<String> getListaEspecialidades() {
        if (especialidades == null || especialidades.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return List.of(especialidades.split(","));
    }

    public void setListaEspecialidades(List<String> especialidadesList) {
        if (especialidadesList == null || especialidadesList.isEmpty()) {
            this.especialidades = null;
        } else {
            this.especialidades = String.join(",", especialidadesList);
        }
    }
}


