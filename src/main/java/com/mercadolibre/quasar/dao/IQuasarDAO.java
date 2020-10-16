package com.mercadolibre.quasar.dao;

import com.mercadolibre.quasar.dao.entity.SatelliteDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IQuasarDAO extends JpaRepository<SatelliteDAO, String> {
}
