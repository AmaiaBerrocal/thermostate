package com.thermostate.brain.application;

import com.thermostate.brain.domain.StatusService;
import com.thermostate.brain.domain.ThermostateStatus;
import org.springframework.stereotype.Component;

@Component
public class GetStatus {
    final StatusService service;


    public GetStatus(StatusService service) {
        this.service = service;
    }

    public ThermostateStatus execute() {
        return service.getStatus();
    }
}
