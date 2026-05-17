package com.crud.demo.mapper;

import com.crud.demo.dto.UsuarioRequisicaoDTO;
import com.crud.demo.dto.UsuarioRespostaDTO;
import com.crud.demo.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    public  Usuario paraEntidade (UsuarioRequisicaoDTO dto){
        Usuario usuario =  new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        return usuario;
    }

    public UsuarioRespostaDTO paraRespostaDTO (Usuario usuario){
        return  new UsuarioRespostaDTO(usuario.getId(), usuario.getEmail(), usuario.getNome());
    }

    public List<UsuarioRespostaDTO> paraRespostaListDTO (List<Usuario> lista){
        return  lista.stream().map(this::paraRespostaDTO).collect(Collectors.toList());
    }
}
