package com.colabspace.reservas.controller;


import com.colabspace.reservas.dto.request.SalaRequest;
import com.colabspace.reservas.dto.response.SalaResponse;
import com.colabspace.reservas.service.SalaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/salas")
public class SalaController {

    private final SalaService service;

    @PostMapping
    public ResponseEntity<SalaResponse> criar(@Valid @RequestBody SalaRequest salaRequest){
        SalaResponse response = service.criar(salaRequest);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SalaResponse> acharPorId(@PathVariable Long id){
        SalaResponse sala = service.acharPorId(id);
        return ResponseEntity.ok().body(sala);
    }

    @GetMapping
    public ResponseEntity<List<SalaResponse>> acharTodas(){
        List<SalaResponse> salasResponse =service.acharTodas();
        return ResponseEntity.ok(salasResponse);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<SalaResponse> atualizar(@PathVariable Long id, @Valid @RequestBody SalaRequest salaRequest){
        SalaResponse response = service.atualizar(id, salaRequest);
        return ResponseEntity.ok(response);
    }

    @PatchMapping(value = "/{id}/inativar")
    public ResponseEntity<SalaResponse> inativar(@PathVariable Long id){
        SalaResponse response = service.inativar(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping(value = "/{id}/ativar")
    public ResponseEntity<SalaResponse> ativar(@PathVariable Long id){
        SalaResponse response = service.ativar(id);
        return ResponseEntity.ok(response);
    }






















}
