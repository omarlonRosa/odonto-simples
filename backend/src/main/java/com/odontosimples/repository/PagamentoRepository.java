package com.odontosimples.repository;

import com.odontosimples.entity.Pagamento;
import com.odontosimples.entity.Consulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    Optional<Pagamento> findByConsulta(Consulta consulta);

    Optional<Pagamento> findByConsultaAndActiveTrue(Consulta consulta);

    List<Pagamento> findByStatusAndActiveTrue(Pagamento.StatusPagamento status);

    List<Pagamento> findByFormaPagamentoAndActiveTrue(Pagamento.FormaPagamento formaPagamento);

    @Query("SELECT p FROM Pagamento p WHERE DATE(p.dataVencimento) = :data AND p.active = true")
    List<Pagamento> findByDataVencimento(@Param("data") LocalDate data);

    @Query("SELECT p FROM Pagamento p WHERE p.dataVencimento BETWEEN :inicio AND :fim AND p.active = true")
    List<Pagamento> findByDataVencimentoBetween(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    @Query("SELECT p FROM Pagamento p WHERE DATE(p.dataPagamento) = :data AND p.active = true")
    List<Pagamento> findByDataPagamento(@Param("data") LocalDate data);

    @Query("SELECT p FROM Pagamento p WHERE DATE(p.dataPagamento) BETWEEN :inicio AND :fim AND p.active = true")
    List<Pagamento> findByDataPagamentoBetween(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    // Pagamentos vencidos
    @Query("SELECT p FROM Pagamento p WHERE p.dataVencimento < :data AND p.status = 'PENDENTE' AND p.active = true")
    List<Pagamento> findVencidos(@Param("data") LocalDate data);

    // Pagamentos do dia
    @Query("SELECT p FROM Pagamento p WHERE DATE(p.dataVencimento) = :data AND p.status = 'PENDENTE' AND p.active = true")
    List<Pagamento> findVencimentosDodia(@Param("data") LocalDate data);

    // Pagamentos por paciente
    @Query("SELECT p FROM Pagamento p WHERE p.consulta.paciente.id = :pacienteId AND p.active = true")
    List<Pagamento> findByPacienteId(@Param("pacienteId") Long pacienteId);

    @Query("SELECT p FROM Pagamento p WHERE p.consulta.paciente.id = :pacienteId AND p.status = :status AND p.active = true")
    List<Pagamento> findByPacienteIdAndStatus(@Param("pacienteId") Long pacienteId, @Param("status") Pagamento.StatusPagamento status);

    // Pagamentos por dentista
    @Query("SELECT p FROM Pagamento p WHERE p.consulta.dentista.id = :dentistaId AND p.active = true")
    List<Pagamento> findByDentistaId(@Param("dentistaId") Long dentistaId);

    @Query("SELECT p FROM Pagamento p WHERE p.consulta.dentista.id = :dentistaId AND DATE(p.dataPagamento) BETWEEN :inicio AND :fim AND p.active = true")
    List<Pagamento> findByDentistaIdAndPeriodo(
            @Param("dentistaId") Long dentistaId,
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim);

    // Relatórios financeiros
    @Query("SELECT SUM(p.valorFinal) FROM Pagamento p WHERE p.status = 'PAGO' AND DATE(p.dataPagamento) = :data AND p.active = true")
    BigDecimal somarReceitasDia(@Param("data") LocalDate data);

    @Query("SELECT SUM(p.valorFinal) FROM Pagamento p WHERE p.status = 'PAGO' AND DATE(p.dataPagamento) BETWEEN :inicio AND :fim AND p.active = true")
    BigDecimal somarReceitasPeriodo(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    @Query("SELECT SUM(p.valorFinal) FROM Pagamento p WHERE p.status = 'PENDENTE' AND p.active = true")
    BigDecimal somarContasReceber();

    @Query("SELECT SUM(p.valorFinal) FROM Pagamento p WHERE p.status = 'PENDENTE' AND p.dataVencimento < :data AND p.active = true")
    BigDecimal somarContasVencidas(@Param("data") LocalDate data);

    @Query("SELECT SUM(p.valorLiquido) FROM Pagamento p WHERE p.status = 'PAGO' AND DATE(p.dataPagamento) BETWEEN :inicio AND :fim AND p.active = true")
    BigDecimal somarReceitasLiquidasPeriodo(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    // Estatísticas por forma de pagamento
    @Query("SELECT p.formaPagamento, COUNT(p), SUM(p.valorFinal) FROM Pagamento p WHERE " +
           "p.status = 'PAGO' AND DATE(p.dataPagamento) BETWEEN :inicio AND :fim AND p.active = true " +
           "GROUP BY p.formaPagamento")
    List<Object[]> estatisticasPorFormaPagamento(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    // Contadores
    @Query("SELECT COUNT(p) FROM Pagamento p WHERE p.status = :status AND p.active = true")
    long countByStatus(@Param("status") Pagamento.StatusPagamento status);

    @Query("SELECT COUNT(p) FROM Pagamento p WHERE DATE(p.dataVencimento) = :data AND p.status = 'PENDENTE' AND p.active = true")
    long countVencimentosDodia(@Param("data") LocalDate data);

    @Query("SELECT COUNT(p) FROM Pagamento p WHERE p.dataVencimento < :data AND p.status = 'PENDENTE' AND p.active = true")
    long countVencidos(@Param("data") LocalDate data);

    // Busca por número de transação
    @Query("SELECT p FROM Pagamento p WHERE p.numeroTransacao = :numeroTransacao AND p.active = true")
    Optional<Pagamento> findByNumeroTransacao(@Param("numeroTransacao") String numeroTransacao);

    // Pagamentos parcelados
    @Query("SELECT p FROM Pagamento p WHERE p.numeroParcelas > 1 AND p.active = true")
    List<Pagamento> findParcelados();

    @Query("SELECT p FROM Pagamento p WHERE p.numeroParcelas > 1 AND p.status = 'PENDENTE' AND p.active = true")
    List<Pagamento> findParceladosPendentes();

    // Paginação
    @Query("SELECT p FROM Pagamento p WHERE p.active = true ORDER BY p.dataVencimento DESC")
    Page<Pagamento> findAllOrderByDataVencimentoDesc(Pageable pageable);

    @Query("SELECT p FROM Pagamento p WHERE p.status = :status AND p.active = true ORDER BY p.dataVencimento DESC")
    Page<Pagamento> findByStatusOrderByDataVencimentoDesc(@Param("status") Pagamento.StatusPagamento status, Pageable pageable);
}


