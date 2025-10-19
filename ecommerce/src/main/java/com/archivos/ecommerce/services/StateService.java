package com.archivos.ecommerce.services;

import com.archivos.ecommerce.dtos.NewStateDto;
import com.archivos.ecommerce.dtos.StateDto;
import com.archivos.ecommerce.entities.State;
import com.archivos.ecommerce.repositories.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StateService {

    private final StateRepository stateRepository;

    public List<StateDto> getAll(){
        return stateRepository.findAll().stream()
                .map(this::convertedToDto)
                .collect(Collectors.toList());
    }

    //Obtener Estados de productos por ID
    public StateDto getById(Integer id){
        State state = stateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado de producto no encontrado"));
        return convertedToDto(state);
    }

    //Crear nuevos estados de productos
    public StateDto create(NewStateDto dto){
        State state = new State();
        state.setName(dto.name());
        state.setDescription(dto.description());

        State saved = stateRepository.save(state);
        return convertedToDto(saved);
    }

    //Actualizar estado de producto existente
    public StateDto update(Integer id, NewStateDto dto){
        State state = stateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado de producto no encontrado"));
        state.setName(dto.name());
        state.setDescription(dto.description());

        State updated = stateRepository.save(state);
        return convertedToDto(updated);
    }

    //Eliminar estado de producto
    public void delete(Integer id){
        stateRepository.deleteById(id);
    }

    //Convertir entidad a DTO
    private StateDto convertedToDto(State state){
        return new StateDto(
                state.getStateId(),
                state.getName(),
                state.getDescription()
        );
    }
}
