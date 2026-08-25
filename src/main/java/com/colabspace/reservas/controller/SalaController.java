package com.colabspace.reservas.controller;


import com.colabspace.reservas.dto.request.SalaRequest;
import com.colabspace.reservas.dto.response.SalaResponse;
import com.colabspace.reservas.service.SalaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/salas")
public class SalaController {

    private final SalaService service;

    @PutMapping
    public ResponseEntity<SalaResponse> criar(@Valid @RequestBody SalaRequest salaRequest){
        SalaResponse response = service.criar(salaRequest);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }
}
