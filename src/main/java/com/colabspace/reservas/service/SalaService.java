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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalaService {

    private final SalaRepository repository;

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
}
