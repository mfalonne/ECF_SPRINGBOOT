package org.example.environement.controller;


import org.example.environement.dto.observation.ObservationDtoReceive;
import org.example.environement.dto.observation.ObservationDtoResponse;
import org.example.environement.service.ObservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/observations")
public class ObservationController {
    private final ObservationService observationService;

    public ObservationController(ObservationService observationService) {
        this.observationService = observationService;
    }

    @GetMapping
    public ResponseEntity<List<ObservationDtoResponse>> getAll(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int pageNumber) {
        return ResponseEntity.ok(observationService.get(pageSize, pageNumber));
    }

    @PostMapping
    public ResponseEntity<ObservationDtoResponse> create(@RequestBody ObservationDtoReceive dto) {
        return ResponseEntity.ok(observationService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObservationDtoResponse> getById(@PathVariable long id) {
        return ResponseEntity.ok(observationService.get(id));
    }

    @GetMapping("/by-location")
    public ResponseEntity<List<ObservationDtoResponse>> getByLocation(@RequestParam String location) {
        return ResponseEntity.ok(observationService.getByLocation(location));
    }

    @GetMapping("/by-species/{speciesId}")
    public ResponseEntity<List<ObservationDtoResponse>> getBySpecie(@PathVariable long speciesId) {
        return ResponseEntity.ok(observationService.getBySpecie(speciesId));
    }

}

