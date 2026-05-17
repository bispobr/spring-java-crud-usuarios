package com.crud.demo.controller;

import com.crud.demo.dto.UsuarioRequisicaoDTO;
import com.crud.demo.dto.UsuarioRespostaDTO;
import com.crud.demo.mapper.UsuarioMapper;
import com.crud.demo.model.Usuario;
import com.crud.demo.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioMapper mapper;

    @PostMapping("/Cadastro")
    @Operation(description = "Endpoint responsável por cadastrar novos Usuarios")
    @ApiResponse(responseCode = "201", description = "Novo usuario criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<UsuarioRespostaDTO> salvarUsuario(@RequestBody @Valid UsuarioRequisicaoDTO requisicaoDTO){
        log.info("Solicitação para criar usuario recebida");
        Usuario usuarioCriado = usuarioService.salvarUsuario(mapper.paraEntidade(requisicaoDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.paraRespostaDTO(usuarioCriado));
    }

    @GetMapping("/ListarPorEmail")
    @Operation(description = "Endpoint responsável por listar usuarios por email")
    @ApiResponse(responseCode = "200", description = "Listagem bem sucedida")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "404", description = "email não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<UsuarioRespostaDTO> buscarUsuarioPorEmail(@RequestParam @Email(message = "E-mail inválido") String email){
        log.info("Solicitação para listar usuario por email recebida");
        return ResponseEntity.ok(mapper.paraRespostaDTO(usuarioService.buscarUsuarioPorEmail(email)));
    }

    @GetMapping("/ListarPorid")
    @Operation(description = "Endpoint responsável por listar usuarios por id")
    @ApiResponse(responseCode = "200", description = "Listagem bem sucedida")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "404", description = "id não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<UsuarioRespostaDTO> buscarUsuarioPorid(@RequestParam @NotNull Long id){
        log.info("Solicitação para listar usuario por id recebida");
        return ResponseEntity.ok(mapper.paraRespostaDTO(usuarioService.buscaUsuarioPorID(id)));
    }

    @DeleteMapping("/RemoverPorEmail")
    @Operation(description = "Endpoint responsável por remover usuario por email")
    @ApiResponse(responseCode = "200", description = "usuario removido com sucesso")
    @ApiResponse(responseCode = "404", description = "usuario não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<Void> deletarUsuarioPorEmail(@RequestParam @Email (message = "E-mail inválido") String email){
        log.info("Solicitação para remover usuario por email recebida");
        usuarioService.deletarUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/RemoverPorid")
    @Operation(description = "Endpoint responsável por remover usuario por id")
    @ApiResponse(responseCode = "200", description = "usuario removido com sucesso")
    @ApiResponse(responseCode = "404", description = "usuario não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<Void> deletarUsuarioPorID(@RequestParam @NotNull Long id){
        log.info("Solicitação para remover usuario por id recebida");
        usuarioService.deletarUsuarioPorID(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/atualizar")
    @Operation(description = "Endpoint responsável por atualizar Usuarios")
    @ApiResponse(responseCode = "200", description = "usuario atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição, dados enviados não atendem os requisitos")
    @ApiResponse(responseCode = "404", description = "usuario não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<Void> atualizarUsuarioPorId(@RequestParam Long id, @RequestBody UsuarioRequisicaoDTO usuario){
        log.info("solicitação para atualizar usuario recebida ");
        usuarioService.atualizarUsuarioPorId(id, usuario);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar")
    @Operation(description = "Endpoint responsável por listar todos os usuarios ")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<List<UsuarioRespostaDTO>> listarUsuarios (){
        log.info("Solicitação para listar todos os usuarios recebida");
        return ResponseEntity.ok().body(mapper.paraRespostaListDTO(usuarioService.listarUsuariosCadastrados()));
    }


}
