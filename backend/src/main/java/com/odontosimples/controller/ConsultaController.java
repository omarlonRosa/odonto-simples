package com.odontosimples.controller;

import com.odontosimples.dto.ConsultaDTO;
import com.odontosimples.dto.ReagendamentoDTO;
import com.odontosimples.entity.Consulta;
import com.odontosimples.service.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/consultas")
@Tag(name = "Consultas", description = "Gerenciamento de consultas e agendamentos")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @Operation(summary = "Agendar consulta", description = "Agenda uma nova consulta")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<?> agendarConsulta(@Valid @RequestBody ConsultaDTO consultaDto) {
        try {
            Consulta consulta = modelMapper.map(consultaDto, Consulta.class);
            Consulta consultaSalva = consultaService.agendarConsulta(consulta);
            
            ConsultaDTO response = modelMapper.map(consultaSalva, ConsultaDTO.class);
            response.setPacienteNome(consultaSalva.getPaciente().getNome());
            response.setDentistaNome(consultaSalva.getDentista().getNome());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao agendar consulta: " + e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Listar consultas", description = "Lista todas as consultas com paginação")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTISTA', 'RECEPCIONISTA')")
    public ResponseEntity<Page<ConsultaDTO>> listarConsultas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataHora") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                   Sort.by(sortBy).descending() : 
                   Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Consulta> consultas = consultaService.listarTodas(pageable);
        
        Page<ConsultaDTO> response = consultas.map(this::convertToDTO);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar consulta por ID", description = "Retorna uma consulta específica")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTISTA', 'RECEPCIONISTA')")
    public ResponseEntity<?> buscarConsulta(@PathVariable Long id) {
        try {
            Consulta consulta = consultaService.buscarPorId(id);
            ConsultaDTO response = convertToDTO(consulta);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar consulta", description = "Atualiza os dados de uma consulta")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<?> atualizarConsulta(@PathVariable Long id, 
                                             @Valid @RequestBody ConsultaDTO consultaDto) {
        try {
            Consulta consulta = modelMapper.map(consultaDto, Consulta.class);
            Consulta consultaAtualizada = consultaService.atualizarConsulta(id, consulta);
            
            ConsultaDTO response = convertToDTO(consultaAtualizada);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar consulta: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/reagendar")
    @Operation(summary = "Reagendar consulta", description = "Reagenda uma consulta para nova data/hora")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<?> reagendarConsulta(@PathVariable Long id, 
                                             @Valid @RequestBody ReagendamentoDTO reagendamentoDto) {
        try {
            Consulta consulta = consultaService.reagendarConsulta(id, 
                    reagendamentoDto.getNovaDataHora(), reagendamentoDto.getMotivo());
            
            ConsultaDTO response = convertToDTO(consulta);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao reagendar consulta: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/confirmar")
    @Operation(summary = "Confirmar consulta", description = "Confirma uma consulta agendada")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<?> confirmarConsulta(@PathVariable Long id) {
        try {
            Consulta consulta = consultaService.confirmarConsulta(id);
            ConsultaDTO response = convertToDTO(consulta);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao confirmar consulta: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/realizar")
    @Operation(summary = "Realizar consulta", description = "Marca uma consulta como realizada")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTISTA', 'RECEPCIONISTA')")
    public ResponseEntity<?> realizarConsulta(@PathVariable Long id) {
        try {
            Consulta consulta = consultaService.realizarConsulta(id);
            ConsultaDTO response = convertToDTO(consulta);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao realizar consulta: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar consulta", description = "Cancela uma consulta")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<?> cancelarConsulta(@PathVariable Long id) {
        try {
            Consulta consulta = consultaService.cancelarConsulta(id);
            ConsultaDTO response = convertToDTO(consulta);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao cancelar consulta: " + e.getMessage());
        }
    }

    @GetMapping("/data/{data}")
    @Operation(summary = "Consultas por data", description = "Lista consultas de uma data específica")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTISTA', 'RECEPCIONISTA')")
    public ResponseEntity<List<ConsultaDTO>> consultasPorData(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        
        List<Consulta> consultas = consultaService.listarPorData(data);
        List<ConsultaDTO> response = consultas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dentista/{dentistaId}/data/{data}")
    @Operation(summary = "Consultas por dentista e data", description = "Lista consultas de um dentista em uma data")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTISTA', 'RECEPCIONISTA')")
    public ResponseEntity<List<ConsultaDTO>> consultasPorDentistaEData(
            @PathVariable Long dentistaId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        
        List<Consulta> consultas = consultaService.listarPorDentistaEData(dentistaId, data);
        List<ConsultaDTO> response = consultas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Consultas por paciente", description = "Lista consultas de um paciente")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTISTA', 'RECEPCIONISTA')")
    public ResponseEntity<List<ConsultaDTO>> consultasPorPaciente(@PathVariable Long pacienteId) {
        List<Consulta> consultas = consultaService.listarPorPaciente(pacienteId);
        List<ConsultaDTO> response = consultas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verificar-disponibilidade")
    @Operation(summary = "Verificar disponibilidade", description = "Verifica se um horário está disponível")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<?> verificarDisponibilidade(
            @RequestParam Long dentistaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataHora,
            @RequestParam(defaultValue = "30") Integer duracao) {
        
        boolean disponivel = consultaService.verificarDisponibilidade(dentistaId, dataHora, duracao);
        
        return ResponseEntity.ok(new Object() {
            public final boolean disponivel = disponivel;
        });
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar consultas", description = "Busca consultas por termo")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTISTA', 'RECEPCIONISTA')")
    public ResponseEntity<Page<ConsultaDTO>> buscarConsultas(
            @RequestParam String termo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("dataHora").descending());
        Page<Consulta> consultas = consultaService.buscarPorTermo(termo, pageable);
        
        Page<ConsultaDTO> response = consultas.map(this::convertToDTO);
        
        return ResponseEntity.ok(response);
    }

    private ConsultaDTO convertToDTO(Consulta consulta) {
        ConsultaDTO dto = modelMapper.map(consulta, ConsultaDTO.class);
        dto.setPacienteNome(consulta.getPaciente().getNome());
        dto.setPacienteTelefone(consulta.getPaciente().getTelefone());
        dto.setDentistaNome(consulta.getDentista().getNome());
        dto.setDentistaCro(consulta.getDentista().getCroCompleto());
        return dto;
    }
}


