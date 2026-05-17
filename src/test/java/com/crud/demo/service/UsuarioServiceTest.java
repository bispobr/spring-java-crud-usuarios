package com.crud.demo.service;

import com.crud.demo.dto.UsuarioRequisicaoDTO;
import com.crud.demo.model.Usuario;
import com.crud.demo.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        usuario = Usuario.builder()
                .id(1L)
                .nome("Teste")
                .email("teste@email.com")
                .build();

    }

    @Test
    void deveSalvarUsuario() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario salvo = usuarioService.salvarUsuario(usuario);

        assertEquals(usuario, salvo);
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void deveBuscarUsuarioPorEmail() {
        when(usuarioRepository.findByEmail("teste@email.com")).thenReturn(Optional.of(usuario));

        Usuario encontrado = usuarioService.buscarUsuarioPorEmail("teste@email.com");

        assertEquals(usuario, encontrado);
        verify(usuarioRepository, times(1)).findByEmail("teste@email.com");
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarUsuarioPorEmail() {
        when(usuarioRepository.findByEmail("teste@email.com")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usuarioService.buscarUsuarioPorEmail("teste@email.com"));
    }

    @Test
    void deveBuscarUsuarioPorId() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario encontrado = usuarioService.buscaUsuarioPorID(1L);

        assertEquals(usuario, encontrado);
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void deveDeletarUsuarioPorEmail() {
        when(usuarioRepository.findByEmail("teste@email.com")).thenReturn(Optional.of(usuario));

        usuarioService.deletarUsuarioPorEmail("teste@email.com");

        verify(usuarioRepository).deleteByEmail("teste@email.com");
    }

    @Test
    void deveDeletarUsuarioPorId() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        usuarioService.deletarUsuarioPorID(1L);

        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    void deveAtualizarUsuario() {
        UsuarioRequisicaoDTO dto = new UsuarioRequisicaoDTO("novo@email.com", "Novo Nome");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        usuarioService.atualizarUsuarioPorId(1L, dto);

        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void deveListarTodosUsuarios() {
        List<Usuario> listaUsuarios = List.of(usuario);
        when(usuarioRepository.findAll()).thenReturn(listaUsuarios);

        List<Usuario> resultado = usuarioService.listarUsuariosCadastrados();

        assertEquals(1, resultado.size());
        verify(usuarioRepository).findAll();
    }


}