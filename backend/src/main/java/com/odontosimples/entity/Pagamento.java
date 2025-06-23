package com.odontosimples.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
public class Pagamento extends BaseEntity {

    @NotNull(message = "Consulta é obrigatória")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id", nullable = false)
    private Consulta consulta;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "valor_desconto", precision = 10, scale = 2)
    private BigDecimal valorDesconto = BigDecimal.ZERO;

    @Column(name = "valor_final", precision = 10, scale = 2)
    private BigDecimal valorFinal;

    @NotNull(message = "Data de vencimento é obrigatória")
    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento")
    private FormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusPagamento status = StatusPagamento.PENDENTE;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "numero_parcelas")
    private Integer numeroParcelas = 1;

    @Column(name = "parcela_atual")
    private Integer parcelaAtual = 1;

    @Column(name = "comprovante", length = 500)
    private String comprovante; // Path do arquivo de comprovante

    @Column(name = "numero_transacao", length = 100)
    private String numeroTransacao;

    @Column(name = "taxa_cartao", precision = 5, scale = 2)
    private BigDecimal taxaCartao = BigDecimal.ZERO;

    @Column(name = "valor_liquido", precision = 10, scale = 2)
    private BigDecimal valorLiquido;

    


  public Pagamento() {}

    public Pagamento(Consulta consulta, BigDecimal valor, LocalDate dataVencimento) {
        this.consulta = consulta;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.valorFinal = valor;
        this.valorLiquido = valor;
    }

   



  public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
        calcularValorFinal();
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto != null ? valorDesconto : BigDecimal.ZERO;
        calcularValorFinal();
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
        calcularValorLiquido();
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Integer getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public Integer getParcelaAtual() {
        return parcelaAtual;
    }

    public void setParcelaAtual(Integer parcelaAtual) {
        this.parcelaAtual = parcelaAtual;
    }

    public String getComprovante() {
        return comprovante;
    }

    public void setComprovante(String comprovante) {
        this.comprovante = comprovante;
    }

    public String getNumeroTransacao() {
        return numeroTransacao;
    }

    public void setNumeroTransacao(String numeroTransacao) {
        this.numeroTransacao = numeroTransacao;
    }

    public BigDecimal getTaxaCartao() {
        return taxaCartao;
    }

    public void setTaxaCartao(BigDecimal taxaCartao) {
        this.taxaCartao = taxaCartao != null ? taxaCartao : BigDecimal.ZERO;
        calcularValorLiquido();
    }

    public BigDecimal getValorLiquido() {
        return valorLiquido;
    }

    public void setValorLiquido(BigDecimal valorLiquido) {
        this.valorLiquido = valorLiquido;
    }

   



  private void calcularValorFinal() {
        if (valor != null) {
            BigDecimal desconto = valorDesconto != null ? valorDesconto : BigDecimal.ZERO;
            this.valorFinal = valor.subtract(desconto);
            calcularValorLiquido();
        }
    }



    private void calcularValorLiquido() {
        if (valorFinal != null) {
            BigDecimal taxa = taxaCartao != null ? taxaCartao : BigDecimal.ZERO;
            BigDecimal valorTaxa = valorFinal.multiply(taxa.divide(BigDecimal.valueOf(100)));
            this.valorLiquido = valorFinal.subtract(valorTaxa);
        }
    }



    public void pagar(FormaPagamento forma, String numeroTransacao) {
        this.formaPagamento = forma;
        this.numeroTransacao = numeroTransacao;
        this.dataPagamento = LocalDateTime.now();
        this.status = StatusPagamento.PAGO;
    }



    public void cancelar() {
        this.status = StatusPagamento.CANCELADO;
    }

    public void estornar() {
        this.status = StatusPagamento.ESTORNADO;
    }

    public boolean isPago() {
        return StatusPagamento.PAGO.equals(status);
    }

    public boolean isPendente() {
        return StatusPagamento.PENDENTE.equals(status);
    }

    public boolean isVencido() {
        return isPendente() && dataVencimento != null && dataVencimento.isBefore(LocalDate.now());
    }

    public boolean isParcelado() {
        return numeroParcelas != null && numeroParcelas > 1;
    }

    public String getDescricaoParcela() {
        if (!isParcelado()) {
            return "À vista";
        }
        return parcelaAtual + "/" + numeroParcelas;
    }

  




  public enum FormaPagamento {
        DINHEIRO("Dinheiro", BigDecimal.ZERO),
        CARTAO_DEBITO("Cartão de Débito", BigDecimal.valueOf(2.0)),
        CARTAO_CREDITO("Cartão de Crédito", BigDecimal.valueOf(3.5)),
        PIX("PIX", BigDecimal.ZERO),
        TRANSFERENCIA("Transferência Bancária", BigDecimal.ZERO),
        CHEQUE("Cheque", BigDecimal.ZERO),
        CONVENIO("Convênio", BigDecimal.ZERO);

        private final String descricao;
        private final BigDecimal taxaPadrao;

        FormaPagamento(String descricao, BigDecimal taxaPadrao) {
            this.descricao = descricao;
            this.taxaPadrao = taxaPadrao;
        }

        public String getDescricao() {
            return descricao;
        }

        public BigDecimal getTaxaPadrao() {
            return taxaPadrao;
        }
    }

    public enum StatusPagamento {
        PENDENTE("Pendente"),
        PAGO("Pago"),
        VENCIDO("Vencido"),
        CANCELADO("Cancelado"),
        ESTORNADO("Estornado"),
        PARCIAL("Pagamento Parcial");

        private final String descricao;

        StatusPagamento(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }
}


