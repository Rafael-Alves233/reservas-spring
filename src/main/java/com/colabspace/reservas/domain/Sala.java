package com.colabspace.reservas.domain;

import com.colabspace.reservas.domain.enums.RecursoSala;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_sala")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nome;
    @Column(nullable = false)
    private Integer capacidade;
    @Column(nullable = false)
    private Integer andar;
    @Column(nullable = false)
    private Boolean ativa = true;



    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "sala_recurso",
            joinColumns = @JoinColumn(name = "sala_id")
    )
    @Column(name = "recurso")
    @Enumerated(EnumType.STRING)
    private Set<RecursoSala> recursos = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sala outra)) return false;
        return id != null && Objects.equals(id, outra.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
