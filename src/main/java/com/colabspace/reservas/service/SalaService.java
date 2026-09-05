package com.colabspace.reservas.service;

import com.colabspace.reservas.domain.Sala;
import com.colabspace.reservas.dto.request.SalaRequest;
import com.colabspace.reservas.dto.response.SalaResponse;
import com.colabspace.reservas.exceptions.RecursoNaoEncontradoException;
import com.colabspace.reservas.repository.SalaRepository;
import com.colabspace.reservas.exceptions.ConflitoDeEstadoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaService {

    private final SalaRepository repository;
    @Transactional
    public SalaResponse criar(SalaRequest salaRequest){


        if(repository.existsByNome(salaRequest.nome())) {
            throw new ConflitoDeEstadoException("Ja existe uma sala com o nome "+ salaRequest.nome());
        }
        Sala sala = new Sala();
        sala.setNome(salaRequest.nome());
        sala.setAndar(salaRequest.andar());
        sala.setCapacidade(salaRequest.capacidade());
        sala.getRecursos().addAll(salaRequest.recursos());

        Sala saved = repository.save(sala);
        return SalaResponse.from(saved);

    }
    @Transactional(readOnly = true)
    public SalaResponse acharPorId(Long id){
        Sala sala = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(id));
        return SalaResponse.from(sala);
    }

    @Transactional(readOnly = true)
    public List<SalaResponse> acharTodas(){
        List<Sala> salas = repository.findAll();
        return salas.stream().map(SalaResponse::from).toList();
    }

    @Transactional
    public SalaResponse atualizar(Long id, SalaRequest salaRequest){
        Sala entity = repository.findById(id).orElseThrow(()-> new RecursoNaoEncontradoException(id));
        if(repository.existsByNomeAndIdNot(salaRequest.nome(),id)){
            throw new ConflitoDeEstadoException("Ja existe uma sala com nome "+ salaRequest.nome());
        }
        atualizarDados(entity,salaRequest);
        return SalaResponse.from(entity);

    }

    @Transactional
    public SalaResponse inativar(Long id){
        Sala entity = repository.findById(id).orElseThrow(()-> new RecursoNaoEncontradoException(id));
        entity.setAtiva(false);
        return SalaResponse.from(entity);
    }

    @Transactional
    public SalaResponse ativar(Long id){
        Sala entity = repository.findById(id).orElseThrow(()-> new RecursoNaoEncontradoException(id));
        entity.setAtiva(true);
        return SalaResponse.from(entity);
    }



    private void atualizarDados(Sala sala, SalaRequest dto){
        sala.setNome(dto.nome());
        sala.setCapacidade(dto.capacidade());
        sala.setAndar(dto.andar());
        sala.getRecursos().clear();
        sala.getRecursos().addAll(dto.recursos());
    }
}
