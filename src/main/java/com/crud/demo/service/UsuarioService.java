package com.crud.demo.service;

import com.crud.demo.dto.UsuarioRequisicaoDTO;
import com.crud.demo.model.Usuario;
import com.crud.demo.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario salvarUsuario(Usuario usuario){
        log.info("Usuario Salvo");
        return repository.save(usuario);
    }

    public Usuario buscarUsuarioPorEmail(String email){
        log.info("Busca por email iniciada");
        return repository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    }

    public Usuario buscaUsuarioPorID(long id){
        log.info("Busca por id iniciada");
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void deletarUsuarioPorEmail(String email){
        log.info("Processando solicitação de remoção por email");
        repository.deleteByEmail(repository.findByEmail(email).orElseThrow(EntityNotFoundException::new).getEmail());
    }

    @Transactional
    public void deletarUsuarioPorID (Long id){
        log.info("Processando solicitação de remoção por id");
        repository.deleteById(repository.findById(id).orElseThrow(EntityNotFoundException::new).getId());
    }

    public void atualizarUsuarioPorId(Long id, UsuarioRequisicaoDTO usuario){
        log.info("Atualizando usuario");
        Usuario usuarioEntity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        Usuario usuarioAtualizado = Usuario.builder().email(usuario.email() != null ? usuario.email() : usuarioEntity.getEmail())
                .nome(usuario.nome() != null ? usuario.nome() : usuarioEntity.getNome())
                .id(usuarioEntity.getId()) .build();
        repository.save(usuarioAtualizado);
    }

    public List<Usuario> listarUsuariosCadastrados (){
        log.info("listando todos os usuarios");
        return repository.findAll();
    }
}
