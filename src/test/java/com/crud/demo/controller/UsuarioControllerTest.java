package com.crud.demo.controller;

import com.crud.demo.dto.UsuarioRequisicaoDTO;
import com.crud.demo.dto.UsuarioRespostaDTO;
import com.crud.demo.mapper.UsuarioMapper;
import com.crud.demo.model.Usuario;
import com.crud.demo.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioMapper mapper;


    @InjectMocks
    private UsuarioController usuarioController;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    private Usuario usuario;
    private UsuarioRespostaDTO usuarioRespostaDTO;
    private UsuarioRequisicaoDTO usuarioRequisicaoDTO;


    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
        usuario = Usuario.builder().id(1L).nome("Teste").email("teste@email.com").build();
        usuarioRespostaDTO = new UsuarioRespostaDTO(1L, "teste@email.com", "teste");
        usuarioRequisicaoDTO = new UsuarioRequisicaoDTO("teste@email.com", "teste");
    }

    @Test
    void deveCadastrarUsuario_ComSucesso() throws Exception {
        when(mapper.paraEntidade(usuarioRequisicaoDTO)).thenReturn(usuario);
        when(usuarioService.salvarUsuario(usuario)).thenReturn(usuario);
        when(mapper.paraRespostaDTO(usuario)).thenReturn(usuarioRespostaDTO);

        mockMvc.perform(post("/usuario/Cadastro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRequisicaoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void deveRetornarBadRequest_QuandoDadosInvalidosNoCadastro() throws Exception {
        UsuarioRequisicaoDTO dtoInvalido = new UsuarioRequisicaoDTO("email_invalido", null);

        mockMvc.perform(post("/usuario/Cadastro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveBuscarUsuarioPorEmail_ComSucesso() throws Exception {
        when(usuarioService.buscarUsuarioPorEmail(usuario.getEmail())).thenReturn(usuario);
        when(mapper.paraRespostaDTO(usuario)).thenReturn(usuarioRespostaDTO);

        mockMvc.perform(get("/usuario/ListarPorEmail")
                        .param("email", usuario.getEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(usuario.getEmail()));
    }

    @Test
    void deveRetornarNotFound_QuandoBuscarUsuarioPorEmail() throws Exception {
        when(usuarioService.buscarUsuarioPorEmail(anyString())).thenThrow(new EntityNotFoundException());

        assertThrows(ServletException.class, () -> {
            mockMvc.perform(get("/usuario/ListarPorEmail")
                            .param("email", "naoexiste@email.com"))
                    .andExpect(status().isNotFound());

        });
    }


    @Test
    void deveBuscarUsuarioPorId_ComSucesso() throws Exception {
        when(usuarioService.buscaUsuarioPorID(1L)).thenReturn(usuario);
        when(mapper.paraRespostaDTO(usuario)).thenReturn(usuarioRespostaDTO);

        mockMvc.perform(get("/usuario/ListarPorid")
                        .param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornarNotFound_QuandoBuscarUsuarioPorId() throws Exception {
        when(usuarioService.buscaUsuarioPorID(anyLong())).thenThrow(new EntityNotFoundException());

        assertThrows(ServletException.class, () -> {
            mockMvc.perform(get("/usuario/ListarPorid")
                            .param("id", "999"))
                    .andExpect(status().isNotFound());

        });
    }

    @Test
    void deveRemoverUsuarioPorEmail_ComSucesso() throws Exception {
        doNothing().when(usuarioService).deletarUsuarioPorEmail(anyString());

        mockMvc.perform(delete("/usuario/RemoverPorEmail")
                        .param("email", "teste@email.com"))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornarNotFound_QuandoRemoverUsuarioPorEmail() throws Exception {
        doThrow(new EntityNotFoundException()).when(usuarioService).deletarUsuarioPorEmail(anyString());

        assertThrows(ServletException.class, () -> {
            mockMvc.perform(delete("/usuario/RemoverPorEmail")
                            .param("email", "inexistente@email.com"))
                    .andExpect(status().isNotFound());
        });
    }

    @Test
    void deveRemoverUsuarioPorId_ComSucesso() throws Exception {
        doNothing().when(usuarioService).deletarUsuarioPorID(anyLong());

        mockMvc.perform(delete("/usuario/RemoverPorid")
                        .param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornarNotFound_QuandoRemoverUsuarioPorId() throws Exception {
        doThrow(new EntityNotFoundException()).when(usuarioService).deletarUsuarioPorID(anyLong());

        assertThrows(ServletException.class, () -> {
            mockMvc.perform(delete("/usuario/RemoverPorid")
                            .param("id", "999"))
                    .andExpect(status().isNotFound());
        });
    }

    @Test
    void deveAtualizarUsuarioPorId_ComSucesso() throws Exception {
        doNothing().when(usuarioService).atualizarUsuarioPorId(anyLong(), any());

        mockMvc.perform(put("/usuario/atualizar")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRequisicaoDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornarNotFound_QuandoAtualizarUsuarioPorId() throws Exception {
        doThrow(new EntityNotFoundException()).when(usuarioService).atualizarUsuarioPorId(anyLong(), any());

        assertThrows(ServletException.class, () -> {
            mockMvc.perform(put("/usuario/atualizar")
                            .param("id", "999")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(usuarioRequisicaoDTO)))
                    .andExpect(status().isNotFound());

        });
    }

    @Test
    void deveListarTodosUsuarios_ComSucesso() throws Exception {
        when(usuarioService.listarUsuariosCadastrados()).thenReturn(List.of(usuario));
        when(mapper.paraRespostaListDTO(List.of(usuario))).thenReturn(List.of(usuarioRespostaDTO));

        mockMvc.perform(get("/usuario/listar"))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornarListaVazia_QuandoNaoHouverUsuarios() throws Exception {
        when(usuarioService.listarUsuariosCadastrados()).thenReturn(List.of());
        when(mapper.paraRespostaListDTO(List.of())).thenReturn(List.of());

        mockMvc.perform(get("/usuario/listar"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }




}