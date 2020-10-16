package com.mercadolibre.quasar.facade.v0.mapper;

import com.mercadolibre.quasar.dao.entity.SatelliteDAO;
import com.mercadolibre.quasar.facade.v0.dto.SatelliteIn;

import java.util.List;

public interface IMapper {
    SatelliteDAO topSecretMessageSplitInput(SatelliteIn satellite, String satelliteName);

    SatelliteIn satelliteInformationTransform(SatelliteDAO satelliteDAO);

    List<SatelliteIn> topSecretMessageSplitOutput(List<SatelliteDAO> daoOut);
}
