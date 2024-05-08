package com.thermostate.brain.application;

import com.thermostate.brain.domain.ThermostateStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetStatus {
    final ThermostateStatus status;

    public ThermostateStatus execute() {
        return status;
    }
}
