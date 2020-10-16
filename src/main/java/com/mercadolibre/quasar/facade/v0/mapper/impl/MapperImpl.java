package com.mercadolibre.quasar.facade.v0.mapper.impl;

import com.mercadolibre.quasar.dao.entity.SatelliteDAO;
import com.mercadolibre.quasar.facade.v0.dto.SatelliteIn;
import com.mercadolibre.quasar.facade.v0.mapper.IMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MapperImpl implements IMapper {

    @Override
    public SatelliteDAO topSecretMessageSplitInput(SatelliteIn satellite, String satelliteName) {

        SatelliteDAO satelliteDao = new SatelliteDAO();
        satelliteDao.setName(satelliteName);
        satelliteDao.setDistance(satellite.getDistance());
        satelliteDao.setMessage(satellite.getMessage().toString());

        return satelliteDao;
    }

    @Override
    public List<SatelliteIn> topSecretMessageSplitOutput(List<SatelliteDAO> daoOut) {

        List<SatelliteIn> out = new ArrayList<>();

        daoOut.forEach(satelliteDao -> {
            SatelliteIn mapperOut = satelliteInformationTransform(satelliteDao);
            out.add(mapperOut);
        });

        return out;
    }

    @Override
    public SatelliteIn satelliteInformationTransform(SatelliteDAO satelliteDAO) {
        SatelliteIn satelliteOut = new SatelliteIn();
        satelliteOut.setName(satelliteDAO.getName());
        satelliteOut.setDistance(satelliteDAO.getDistance());

        // Recovering the message from the database and trasforming it into a list of arrays
        String joinedMessageMinusBrackets = satelliteDAO.getMessage().substring(1, satelliteDAO.getMessage().length() - 1);
        String[] messageOutAsString = joinedMessageMinusBrackets.split(",");
        satelliteOut.setMessage(Arrays.asList(messageOutAsString));

        return satelliteOut;
    }




}
