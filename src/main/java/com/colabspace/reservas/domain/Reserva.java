package com.colabspace.reservas.domain;

import com.colabspace.reservas.domain.enums.StatusReserva;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sala_id",nullable = false)
    private Sala sala;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaborador;

    @Column(nullable = false)
    private LocalDateTime inicio;
    @Column(nullable = false)
    private LocalDateTime fim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusReserva status;

    @Column(nullable = false)
    private LocalDateTime criadaEm;

    @Column
    private String motivoCancelamento;

    public Reserva(Sala sala, Colaborador colaborador,
                   LocalDateTime inicio, LocalDateTime fim) {
        this.sala = sala;
        this.colaborador = colaborador;
        this.inicio = inicio;
        this.fim = fim;
        this.status = StatusReserva.CONFIRMADA;
        this.criadaEm = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reserva outra)) return false;
        return id != null && Objects.equals(id, outra.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
