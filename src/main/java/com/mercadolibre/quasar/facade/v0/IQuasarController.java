package com.mercadolibre.quasar.facade.v0;

import com.mercadolibre.quasar.facade.v0.dto.SatelliteIn;
import com.mercadolibre.quasar.facade.v0.dto.SatelliteOut;

import java.util.List;

public interface IQuasarController {

    SatelliteOut topSecretMessage(List<SatelliteIn> satellites);

    void createTopSecretMessageSplit(SatelliteIn satellite, String satelliteName);

    SatelliteOut getTopSecretMessageSplit();
}
