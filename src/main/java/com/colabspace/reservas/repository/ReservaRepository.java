package com.colabspace.reservas.repository;

import com.colabspace.reservas.domain.Reserva;
import com.colabspace.reservas.domain.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
