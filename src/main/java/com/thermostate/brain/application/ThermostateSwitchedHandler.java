package com.thermostate.brain.application;

import com.thermostate.brain.domain.ThermostatAdapter;
import com.thermostate.brain.domain.events.ThermostateSwitched;
import com.thermostate.shared.events.infrastructure.EventHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ThermostateSwitchedHandler extends EventHandler<ThermostateSwitched> {
    private final ThermostatAdapter adapter;

    @Override
    public void handle(ThermostateSwitched event) {
        adapter.setActiveStatus(event.isOn());
    }
}
