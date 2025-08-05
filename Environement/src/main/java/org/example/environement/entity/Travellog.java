package org.example.environement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.environement.dto.travellog.TravellogDtoResponse;
import org.example.environement.entity.enums.TravelMode;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Travellog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "observation_id", nullable = false)
    private Observation observation;

    @Column(nullable = false)
    private Double distanceKm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TravelMode mode;

    @Column(nullable = false)
    private Double estimatedCo2Kg;

    public void calculateCO2() {
        double emissionFactor = switch (mode) {
            case WALKING, BIKE -> 0.0;
            case CAR -> 0.22;
            case BUS -> 0.11;
            case TRAIN -> 0.03;
            case PLANE -> 0.259;
        };
        this.estimatedCo2Kg = this.distanceKm * emissionFactor;
    }


    public TravellogDtoResponse entityToDto() {
        return TravellogDtoResponse.builder()
                .id(this.id)
                .distanceKm(this.distanceKm)
                .mode(this.mode.toString())
                .estimatedCo2Kg(this.estimatedCo2Kg)
                .build();
    }
}
