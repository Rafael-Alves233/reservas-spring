package com.colabspace.reservas.service;

import com.colabspace.reservas.repository.ColaboradorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ColaboradorService {

    private final ColaboradorRepository repository;

}
