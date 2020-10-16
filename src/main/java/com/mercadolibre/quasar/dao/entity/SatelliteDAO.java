package com.mercadolibre.quasar.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "satellites", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Data
public class SatelliteDAO {

    @Id
    private String name;

    private double distance;

    private String message;

}
