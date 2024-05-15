package com.thermostate.brain.domain;

import com.thermostate.shared.domain.Temperature;

import java.util.Date;

public class ManuallyEstablishedTemperature implements ActiveTemperature {

    Integer temp;
    Boolean active;
    Long activatedOn;

    public ManuallyEstablishedTemperature(Integer temp) {
        this.temp = temp;
        this.active = Boolean.TRUE;
    }

    public void setTemperature(Temperature tepm) {
        this.temp = tepm.getTemp();
        this.active = true;
        this.activatedOn = (new Date()).getTime();
    }

    public void deactivate() {
        this.active = false;
    }

    @Override
    public Boolean higherThan(Temperature currentTemperature) {
        return temp > currentTemperature.getTemp();
    }
}
