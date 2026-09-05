package com.colabspace.reservas.service;

import com.colabspace.reservas.domain.Colaborador;
import com.colabspace.reservas.dto.request.ColaboradorRequest;
import com.colabspace.reservas.dto.response.ColaboradorResponse;
import com.colabspace.reservas.exceptions.ConflitoDeEstadoException;
import com.colabspace.reservas.exceptions.RecursoNaoEncontradoException;
import com.colabspace.reservas.repository.ColaboradorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ColaboradorService {

    private final ColaboradorRepository repository;

    @Transactional
    public ColaboradorResponse criar (ColaboradorRequest colaboradorRequest){

        if(repository.existsByEmail(colaboradorRequest.email())){
            throw new ConflitoDeEstadoException("Ja existe um colaborador com o email "+ colaboradorRequest.email());
        }
        Colaborador colaborador = new Colaborador();
        colaborador.setNome(colaboradorRequest.nome());
        colaborador.setEmail(colaboradorRequest.email());
        colaborador.setDepartamento(colaboradorRequest.departamento());

        Colaborador saved = repository.save(colaborador);
        return ColaboradorResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public ColaboradorResponse acharPorId(Long id){
        Colaborador colaborador = repository
                .findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Colaborador nao encontrado", id));

        return ColaboradorResponse.from(colaborador);
    }
}
