package com.colabspace.reservas.service;

import com.colabspace.reservas.domain.Sala;
import com.colabspace.reservas.dto.request.SalaRequest;
import com.colabspace.reservas.dto.response.SalaResponse;
import com.colabspace.reservas.repository.SalaRepository;
import com.colabspace.reservas.exceptions.ConflitoDeEstadoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
