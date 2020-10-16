package com.mercadolibre.quasar.service;

import com.mercadolibre.quasar.dao.entity.SatelliteDAO;
import com.mercadolibre.quasar.facade.v0.dto.Position;
import com.mercadolibre.quasar.facade.v0.dto.SatelliteIn;
import com.mercadolibre.quasar.facade.v0.dto.SatelliteOut;

import java.util.List;

public interface IService {
    String getMessage(List<SatelliteIn> satellites);

    Position getLocation(List<SatelliteIn> satellites);

    void saveSatellite(SatelliteDAO satelliteDAO);

    SatelliteOut getSecretMessageInformation(List<SatelliteIn> satellitesInput);

    List<SatelliteDAO> findSatelliteData();
}
