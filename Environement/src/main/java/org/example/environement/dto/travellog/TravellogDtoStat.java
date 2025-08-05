package org.example.environement.dto.travellog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Data
@Builder
public class TravellogDtoStat {
    private double totalDistanceKm;
    private double totalEmissionsKg;
    private Map<String,Double> byMode;

    public TravellogDtoStat(double totalDistanceKm, double totalEmissionsKg, Map<String, Double> byMode) {
        this.totalDistanceKm = totalDistanceKm;
        this.totalEmissionsKg = totalEmissionsKg;
        this.byMode = byMode;
    }

    public TravellogDtoStat() {
        totalDistanceKm = 0;
        totalEmissionsKg = 0;
        byMode = new HashMap<>();
    }

    public void addTotalDistanceKm(double totalDistanceKm) {
        this.totalDistanceKm += totalDistanceKm;
    }

    public void addTotalEmissionsKg(double totalEmissionsKg) {
        this.totalEmissionsKg += totalEmissionsKg;
    }

    public void addMode(String mode,double distance) {
        this.byMode.put(mode,distance);
    }
}
