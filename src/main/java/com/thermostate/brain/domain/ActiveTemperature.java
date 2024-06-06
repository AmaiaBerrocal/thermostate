package com.thermostate.brain.domain;

import com.thermostate.shared.domain.Temperature;

public interface ActiveTemperature {

    Boolean higherThan(Temperature currentTemperature);
}
