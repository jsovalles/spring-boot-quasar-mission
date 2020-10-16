package com.mercadolibre.quasar.facade.v0.dto;

import lombok.Data;

import java.util.List;

@Data
public class SatelliteIn {

    private String name;

    private double distance;

    private List<String> message;
}
