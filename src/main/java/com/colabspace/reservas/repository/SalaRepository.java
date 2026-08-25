package com.colabspace.reservas.repository;

import com.colabspace.reservas.domain.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {
    public boolean existsByNome(String Nome);
}
