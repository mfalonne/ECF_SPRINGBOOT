package org.example.environement.controller;

import org.example.environement.dto.travellog.TravellogDtoResponse;
import org.example.environement.dto.travellog.TravellogDtoStat;
import org.example.environement.service.TravellogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/travellog")
public class TravellogController {

    private final TravellogService travellogService;

    public TravellogController(TravellogService travellogService) {
        this.travellogService = travellogService;
    }

    @GetMapping
    public ResponseEntity<List<TravellogDtoResponse>> getAllTravellogs (){
        return ResponseEntity.ok(travellogService.get(10));
    }

    @GetMapping("/stats/{id}")
    public ResponseEntity<TravellogDtoStat> getStatFromObseration (@PathVariable long id){
        return ResponseEntity.ok(travellogService.getStat(id));
    }

    @GetMapping("/user/{name}")
    public ResponseEntity<Map<String, TravellogDtoStat>> getTravelStatForUserOnLAstMonth (@PathVariable String name){
        return ResponseEntity.ok(travellogService.getStatForUserLastMonth(name));
    }
}
