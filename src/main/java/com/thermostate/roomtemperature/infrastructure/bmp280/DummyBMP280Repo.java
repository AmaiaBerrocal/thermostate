package com.thermostate.roomtemperature.infrastructure.bmp280;

import com.thermostate.roomtemperature.domain.RoomTemperature;
import com.thermostate.roomtemperature.domain.RoomTemperatureRepo;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DummyBMP280Repo implements RoomTemperatureRepo {

    public DummyBMP280Repo() {

    }


    public double readTemperature() {
       return 42.0d;
    }


    @Override
    public RoomTemperature getTemp() {
        return RoomTemperature.create((int)(readTemperature() * 1000));
    }
}
