package com.thermostate.brain.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusService {
    @Getter
    private final ThermostateStatus status;
    private final ThermostatAdapter adapter;
    public void updateStatus() {
        adapter.setActiveStatus(status.getActive());
    }
}
