package com.mercadolibre.quasar.service;

import com.mercadolibre.quasar.facade.v0.dto.Position;
import com.mercadolibre.quasar.facade.v0.dto.SatelliteIn;

import java.util.List;

public interface IService {
    String getMessage(List<SatelliteIn> satellites);

    Position getLocation(List<SatelliteIn> satellites);
}
