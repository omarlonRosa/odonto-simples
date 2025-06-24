package com.odontosimples.controller;

import com.odontosimples.dto.*;
import com.odontosimples.entity.Usuario;
import com.odontosimples.security.JwtTokenProvider;
import com.odontosimples.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Endpoints para autenticação e autorização")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/login")
    @Operation(summary = "Realizar login", description = "Autentica um usuário e retorna o token JWT")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getSenha())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);

            Usuario usuario = (Usuario) authentication.getPrincipal();
            UsuarioDTO usuarioDto = modelMapper.map(usuario, UsuarioDTO.class);

            LoginResponseDTO response = new LoginResponseDTO(jwt, "Bearer", usuarioDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Credenciais inválidas");
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar usuário", description = "Cria um novo usuário no sistema")
    public ResponseEntity<?> register(@Valid @RequestBody UsuarioCreateDTO usuarioDto) {
        try {
            if (usuarioService.emailExiste(usuarioDto.getEmail())) {
                return ResponseEntity.badRequest().body("Email já está em uso");
            }

            Usuario usuario = new Usuario();
            usuario.setNome(usuarioDto.getNome());
            usuario.setEmail(usuarioDto.getEmail());
            usuario.setSenha(usuarioDto.getSenha());
            usuario.setTelefone(usuarioDto.getTelefone());
            
            if (usuarioDto.getRole() != null) {
                usuario.setRole(Usuario.Role.valueOf(usuarioDto.getRole()));
            }

            Usuario usuarioSalvo = usuarioService.criarUsuario(usuario);
            UsuarioDTO response = modelMapper.map(usuarioSalvo, UsuarioDTO.class);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao criar usuário: " + e.getMessage());
        }
    }

    @GetMapping("/me")
    @Operation(summary = "Obter dados do usuário logado", description = "Retorna os dados do usuário autenticado")
    public ResponseEntity<UsuarioDTO> getCurrentUser(Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        UsuarioDTO usuarioDto = modelMapper.map(usuario, UsuarioDTO.class);
        return ResponseEntity.ok(usuarioDto);
    }

    @PostMapping("/change-password")
    @Operation(summary = "Alterar senha", description = "Altera a senha do usuário autenticado")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDto, 
                                          Authentication authentication) {
        try {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            usuarioService.alterarSenha(usuario.getId(), 
                                       changePasswordDto.getSenhaAtual(), 
                                       changePasswordDto.getNovaSenha());
            return ResponseEntity.ok("Senha alterada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao alterar senha: " + e.getMessage());
        }
    }
}

// DTO para alteração de senha
class ChangePasswordDTO {
    private String senhaAtual;
    private String novaSenha;

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}


