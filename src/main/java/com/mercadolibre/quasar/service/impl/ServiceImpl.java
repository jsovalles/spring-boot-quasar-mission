package com.mercadolibre.quasar.service.impl;

import com.mercadolibre.quasar.dao.IQuasarDAO;
import com.mercadolibre.quasar.dao.entity.SatelliteDAO;
import com.mercadolibre.quasar.facade.v0.dto.Position;
import com.mercadolibre.quasar.facade.v0.dto.SatelliteIn;
import com.mercadolibre.quasar.facade.v0.dto.SatelliteOut;
import com.mercadolibre.quasar.service.IService;
import com.mercadolibre.quasar.utils.FunctionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.StringJoiner;

@Service
public class ServiceImpl implements IService {

    @Autowired
    FunctionUtils functionUtils;

    @Autowired
    IQuasarDAO dao;

    @Override
    public String getMessage(List<SatelliteIn> satellites) {
        List<String> resultMessage = null;

        for (SatelliteIn satellite : satellites) {
            resultMessage = functionUtils.getMessage(satellite.getMessage(), resultMessage);
        }

        if (resultMessage == null || resultMessage.contains(null)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The message couldn't be decypher, it needs more information");
        }

        StringJoiner out = new StringJoiner(" ");
        resultMessage.forEach(value -> out.add(value));

        return out.toString();
    }

    @Override
    public Position getLocation(List<SatelliteIn> satellites) {
        return functionUtils.getLocation(satellites);
    }

    @Override
    public void saveSatellite(SatelliteDAO satelliteDAO) {
        dao.save(satelliteDAO);
    }

    @Override
    public List<SatelliteDAO> findSatelliteData() {
        return dao.findAll();
    }

    @Override
    public SatelliteOut getSecretMessageInformation(List<SatelliteIn> satellitesInput) {

        SatelliteOut satellitesOut = new SatelliteOut();
        satellitesOut.setMessage(getMessage(satellitesInput));
        satellitesOut.setPosition(getLocation(satellitesInput));

        return satellitesOut;

    }
}
