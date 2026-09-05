package com.colabspace.reservas.controller;


import com.colabspace.reservas.dto.request.ColaboradorRequest;
import com.colabspace.reservas.dto.response.ColaboradorResponse;
import com.colabspace.reservas.service.ColaboradorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/colaboradores")
public class ColaboradorController {

    private final ColaboradorService service;

    @PostMapping
    public ResponseEntity<ColaboradorResponse> criar(@Valid @RequestBody ColaboradorRequest request){
        ColaboradorResponse response = service.criar(request);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ColaboradorResponse> acharPorId(@PathVariable Long id){
        ColaboradorResponse colaboradorResponse = service.acharPorId(id);
        return ResponseEntity.ok().body(colaboradorResponse);
    }
}
