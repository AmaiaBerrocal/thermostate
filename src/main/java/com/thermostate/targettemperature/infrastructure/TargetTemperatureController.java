package com.thermostate.targettemperature.infrastructure;

import com.thermostate.shared.ClientError;
import com.thermostate.shared.ValueResponse;
import com.thermostate.targettemperature.application.DecreaseTargetTemperature;
import com.thermostate.targettemperature.application.GetTargetTemperature;
import com.thermostate.targettemperature.application.IncreaseTargetTemperature;
import com.thermostate.shared.domain.Temperature;
import com.thermostate.targettemperature.domain.TemperatureChange;
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
public class TargetTemperatureController {
    private final IncreaseTargetTemperature increaseTargetTemperature;
    private final DecreaseTargetTemperature decreaseTargetTemperature;
    private final GetTargetTemperature getTargetTemperature;
    private static final Logger LOGGER = LoggerFactory.getLogger(TargetTemperatureController.class);

    public TargetTemperatureController(IncreaseTargetTemperature increaseTargetTemperature,
                                        DecreaseTargetTemperature decreaseTargetTemperature, final GetTargetTemperature getTargetTemperature) {
        this.increaseTargetTemperature = increaseTargetTemperature;
        this.decreaseTargetTemperature = decreaseTargetTemperature;
        this.getTargetTemperature = getTargetTemperature;
    }

    @PutMapping("/temperature/increment")
    public void tempIncrement (@RequestBody TempUpdateRequest tempUpdateRequest) {
        increaseTargetTemperature.execute(TemperatureChange.create(tempUpdateRequest.temperature));
    }

    @PutMapping("/temperature/decrement")
    public void tempDecrement (@RequestBody TempUpdateRequest tempUpdateRequest) {
        decreaseTargetTemperature.execute(TemperatureChange.create(-tempUpdateRequest.temperature));
    }

    @GetMapping("/temperature")
    @ResponseBody
    public ValueResponse<Temperature> getTemp () {
        return new ValueResponse<>(getTargetTemperature.execute());
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
