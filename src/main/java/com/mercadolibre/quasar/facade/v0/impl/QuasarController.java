package com.mercadolibre.quasar.facade.v0.impl;

import com.mercadolibre.quasar.facade.v0.dto.SatelliteIn;
import com.mercadolibre.quasar.facade.v0.dto.SatelliteOut;
import com.mercadolibre.quasar.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quasar/v0")
public class QuasarController {

    @Autowired
    IService srv;

    @PostMapping("/topsecret")
    public SatelliteOut topSecretMessage(@RequestBody List<SatelliteIn> satellites) {

        SatelliteOut out = new SatelliteOut();
        out.setMessage(srv.getMessage(satellites));
        out.setPosition(srv.getLocation(satellites));

        return out;
    }

}
