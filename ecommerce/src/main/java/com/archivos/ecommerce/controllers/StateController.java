package com.archivos.ecommerce.controllers;

import com.archivos.ecommerce.dtos.NewStateDto;
import com.archivos.ecommerce.dtos.StateDto;
import com.archivos.ecommerce.services.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-states")
@RequiredArgsConstructor
public class StateController {

    private final StateService stateService;

    @GetMapping
    public ResponseEntity<List<StateDto>> getAll(){
        return ResponseEntity.ok(stateService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StateDto> getById(@PathVariable Integer id){
        return ResponseEntity.ok(stateService.getById(id));
    }

    @PostMapping
    public ResponseEntity<StateDto> create(@RequestBody NewStateDto dto){
        return ResponseEntity.status(201).body(stateService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StateDto> update(@PathVariable Integer id, @RequestBody NewStateDto dto){
        return ResponseEntity.ok(stateService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StateDto> delete(@PathVariable Integer id){
        stateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
