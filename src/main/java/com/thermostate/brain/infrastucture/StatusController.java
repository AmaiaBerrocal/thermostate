package com.thermostate.brain.infrastucture;

import com.thermostate.brain.application.GetStatus;
import com.thermostate.brain.domain.ThermostateStatus;
import com.thermostate.shared.ClientError;
import com.thermostate.shared.ValueResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class StatusController {
    private final GetStatus getStatus;
    private static final Logger LOGGER = LoggerFactory.getLogger(StatusController.class);

    public StatusController(GetStatus getStatus) {
        this.getStatus = getStatus;
    }

    @GetMapping("/status")
    public ValueResponse<ThermostateStatus> getStatus () {
        return new ValueResponse<>(getStatus.execute());
    }

    @ExceptionHandler(value = ClientError.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void handleException(Exception ex) {
        LOGGER.error("ERROR: " + ex.getMessage());
        ex.printStackTrace();
    }
}

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
class TempUpdateRequest {
    Integer temperature;
}
