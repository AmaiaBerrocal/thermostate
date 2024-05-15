package com.thermostate.brain.domain;

import com.thermostate.shared.domain.Temperature;
import lombok.Getter;

import java.util.Date;

@Getter
public class ManuallyEstablishedTemperature implements ActiveTemperature {
    Integer temp;
    Boolean active;

    public ManuallyEstablishedTemperature(Integer temp) {
        this.temp = temp;
        this.active = Boolean.TRUE;
    }

    public void setTemperature(Temperature tepm) {
        this.temp = tepm.getTemp();
        this.active = true;
    }

    @Override
    public Boolean higherThan(Temperature currentTemperature) {
        return temp > currentTemperature.getTemp();
    }
}
