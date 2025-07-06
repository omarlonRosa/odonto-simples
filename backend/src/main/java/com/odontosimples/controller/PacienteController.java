package com.odontosimples.controller;

import com.odontosimples.dto.PacienteDTO;
import com.odontosimples.entity.Paciente;
import com.odontosimples.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pacientes")
@Tag(name = "Pacientes", description = "Gerenciamento de pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @Operation(summary = "Criar paciente", description = "Cria um novo paciente")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<?> criarPaciente(@Valid @RequestBody PacienteDTO pacienteDto) {
        try {
            Paciente paciente = modelMapper.map(pacienteDto, Paciente.class);
            Paciente pacienteSalvo = pacienteService.criarPaciente(paciente);
            
            PacienteDTO response = modelMapper.map(pacienteSalvo, PacienteDTO.class);
            response.setIdade(pacienteSalvo.getIdade());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao criar paciente: " + e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Listar pacientes", description = "Lista todos os pacientes com paginação")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTISTA', 'RECEPCIONISTA')")
    public ResponseEntity<Page<PacienteDTO>> listarPacientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                   Sort.by(sortBy).descending() : 
                   Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Paciente> pacientes = pacienteService.listarTodos(pageable);
        
        Page<PacienteDTO> response = pacientes.map(paciente -> {
            PacienteDTO dto = modelMapper.map(paciente, PacienteDTO.class);
            dto.setIdade(paciente.getIdade());
            return dto;
        });
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar paciente por ID", description = "Retorna um paciente específico")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTISTA', 'RECEPCIONISTA')")
    public ResponseEntity<?> buscarPaciente(@PathVariable Long id) {
        try {
            Paciente paciente = pacienteService.buscarPorId(id);
            PacienteDTO response = modelMapper.map(paciente, PacienteDTO.class);
            response.setIdade(paciente.getIdade());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar paciente", description = "Atualiza os dados de um paciente")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<?> atualizarPaciente(@PathVariable Long id, 
                                             @Valid @RequestBody PacienteDTO pacienteDto) {
        try {
            Paciente paciente = modelMapper.map(pacienteDto, Paciente.class);
            Paciente pacienteAtualizado = pacienteService.atualizarPaciente(id, paciente);
            
            PacienteDTO response = modelMapper.map(pacienteAtualizado, PacienteDTO.class);
            response.setIdade(pacienteAtualizado.getIdade());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar paciente: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar paciente", description = "Desativa um paciente (soft delete)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> desativarPaciente(@PathVariable Long id) {
        try {
            pacienteService.desativar(id);
            return ResponseEntity.ok("Paciente desativado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao desativar paciente: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/ativar")
    @Operation(summary = "Ativar paciente", description = "Reativa um paciente")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> ativarPaciente(@PathVariable Long id) {
        try {
            pacienteService.ativar(id);
            return ResponseEntity.ok("Paciente ativado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao ativar paciente: " + e.getMessage());
        }
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar pacientes", description = "Busca pacientes por termo (nome, CPF, telefone, email)")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTISTA', 'RECEPCIONISTA')")
    public ResponseEntity<Page<PacienteDTO>> buscarPacientes(
            @RequestParam String termo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("nome"));
        Page<Paciente> pacientes = pacienteService.buscarPorTermo(termo, pageable);
        
        Page<PacienteDTO> response = pacientes.map(paciente -> {
            PacienteDTO dto = modelMapper.map(paciente, PacienteDTO.class);
            dto.setIdade(paciente.getIdade());
            return dto;
        });
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar paciente por CPF", description = "Retorna um paciente pelo CPF")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTISTA', 'RECEPCIONISTA')")
    public ResponseEntity<?> buscarPorCpf(@PathVariable String cpf) {
        try {
            Paciente paciente = pacienteService.buscarPorCpf(cpf);
            PacienteDTO response = modelMapper.map(paciente, PacienteDTO.class);
            response.setIdade(paciente.getIdade());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/aniversariantes")
    @Operation(summary = "Aniversariantes do dia", description = "Lista pacientes que fazem aniversário hoje")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTISTA', 'RECEPCIONISTA')")
    public ResponseEntity<List<PacienteDTO>> aniversariantesDodia() {
        List<Paciente> pacientes = pacienteService.buscarAniversariantesDodia();
        
        List<PacienteDTO> response = pacientes.stream()
                .map(paciente -> {
                    PacienteDTO dto = modelMapper.map(paciente, PacienteDTO.class);
                    dto.setIdade(paciente.getIdade());
                    return dto;
                })
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/aniversariantes/{mes}")
    @Operation(summary = "Aniversariantes do mês", description = "Lista pacientes que fazem aniversário no mês")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTISTA', 'RECEPCIONISTA')")
    public ResponseEntity<List<PacienteDTO>> aniversariantesDoMes(@PathVariable int mes) {
        List<Paciente> pacientes = pacienteService.buscarAniversariantesDoMes(mes);
        
        List<PacienteDTO> response = pacientes.stream()
                .map(paciente -> {
                    PacienteDTO dto = modelMapper.map(paciente, PacienteDTO.class);
                    dto.setIdade(paciente.getIdade());
                    return dto;
                })
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estatisticas")
    @Operation(summary = "Estatísticas de pacientes", description = "Retorna estatísticas gerais dos pacientes")
    @PreAuthorize("hasAnyRole('ADMIN', 'DENTISTA')")
    public ResponseEntity<?> estatisticas() {
        long totalPacientes = pacienteService.contarPacientes();
        
        return ResponseEntity.ok(new Object() {
            public final long total = totalPacientes;
        });
    }
}


