package com.mercadolibre.quasar.facade.v0.impl;

import com.mercadolibre.quasar.dao.entity.SatelliteDAO;
import com.mercadolibre.quasar.facade.v0.IQuasarController;
import com.mercadolibre.quasar.facade.v0.dto.SatelliteIn;
import com.mercadolibre.quasar.facade.v0.dto.SatelliteOut;
import com.mercadolibre.quasar.facade.v0.mapper.IMapper;
import com.mercadolibre.quasar.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quasar/v0")
public class QuasarController implements IQuasarController {

    @Autowired
    IService srv;

    @Autowired
    IMapper mapper;

    @Override
    @PostMapping("/topsecret")
    public SatelliteOut topSecretMessage(@RequestBody List<SatelliteIn> satellites) {

        SatelliteOut out = new SatelliteOut();
        out.setMessage(srv.getMessage(satellites));
        out.setPosition(srv.getLocation(satellites));

        return out;
    }

    @Override
    @PostMapping("/topsecret_split")
    public void createTopSecretMessageSplit(@RequestBody SatelliteIn satellite, @RequestParam(name = "satellite_name") String satelliteName) {

        SatelliteDAO satelliteDAO = mapper.topSecretMessageSplitInput(satellite, satelliteName);

        srv.saveSatellite(satelliteDAO);
    }

    @Override
    @GetMapping("/topsecret_split")
    public SatelliteOut getTopSecretMessageSplit() {

        List<SatelliteDAO> daoOut = srv.findSatelliteData();

        List<SatelliteIn> satellitesInput = mapper.topSecretMessageSplitOutput(daoOut);

        SatelliteOut out = srv.getSecretMessageInformation(satellitesInput);

        return out;
    }

}
