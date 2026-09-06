package com.colabspace.reservas.config;

import com.colabspace.reservas.domain.Colaborador;
import com.colabspace.reservas.domain.Sala;
import com.colabspace.reservas.domain.enums.Departamento;
import com.colabspace.reservas.domain.enums.RecursoSala;
import com.colabspace.reservas.repository.ColaboradorRepository;
import com.colabspace.reservas.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.Set;

/**
 * Popula o banco em memoria na subida da aplicacao, para que a API ja
 * responda dados no primeiro GET sem cadastro manual.
 */
@Configuration
@Profile("test")
@RequiredArgsConstructor
public class SeedDeDados implements CommandLineRunner {

    private final SalaRepository salaRepository;
    private final ColaboradorRepository colaboradorRepository;

    @Override
    public void run(String... args) {
        // evita duplicar o seed caso o banco deixe de ser volatil
        if (salaRepository.count() > 0 || colaboradorRepository.count() > 0) {
            return;
        }

        salaRepository.saveAll(List.of(
                sala("Sala Apollo", 12, 1, true,
                        Set.of(RecursoSala.PROJETOR, RecursoSala.AR_CONDICIONADO)),
                sala("Sala Gemini", 6, 2, true,
                        Set.of(RecursoSala.TV, RecursoSala.LOUSA)),
                sala("Sala Titan", 30, 3, true,
                        Set.of(RecursoSala.PROJETOR, RecursoSala.TV,
                                RecursoSala.AR_CONDICIONADO, RecursoSala.LOUSA)),
                // sala inativa: torna a regra de "nao reservar sala inativa" testavel de imediato
                sala("Sala Mercury", 4, 1, false, Set.of())
        ));

        colaboradorRepository.saveAll(List.of(
                colaborador("Ana Souza", "ana.souza@colabspace.com", Departamento.TECNOLOGIA),
                colaborador("Bruno Lima", "bruno.lima@colabspace.com", Departamento.FINANCEIRO),
                colaborador("Carla Mendes", "carla.mendes@colabspace.com", Departamento.MARKETING)
        ));
    }

    private Sala sala(String nome, Integer capacidade, Integer andar,
                      Boolean ativa, Set<RecursoSala> recursos) {
        Sala sala = new Sala();
        sala.setNome(nome);
        sala.setCapacidade(capacidade);
        sala.setAndar(andar);
        sala.setAtiva(ativa);
        sala.getRecursos().addAll(recursos);
        return sala;
    }

    private Colaborador colaborador(String nome, String email, Departamento departamento) {
        Colaborador colaborador = new Colaborador();
        colaborador.setNome(nome);
        colaborador.setEmail(email);
        colaborador.setDepartamento(departamento);
        return colaborador;
    }
}
