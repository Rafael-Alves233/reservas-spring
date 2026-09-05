package com.colabspace.reservas.repository;

import com.colabspace.reservas.domain.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {
    public boolean existsByEmail(String email);
}
