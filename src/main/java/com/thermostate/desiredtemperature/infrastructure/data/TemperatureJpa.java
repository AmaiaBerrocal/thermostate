package com.thermostate.desiredtemperature.infrastructure.data;

import com.thermostate.desiredtemperature.model.Temperature;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Temperature ")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TemperatureJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="temperature")
    private Integer temperature;

    public Temperature toDomain() {
        return new Temperature(this.temperature);
    }

}
