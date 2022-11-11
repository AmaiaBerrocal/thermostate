package com.thermostate.temperature.infrastructure;

import com.thermostate.shared.ClientError;
import com.thermostate.temperature.application.DecreaseTemperature;
import com.thermostate.temperature.application.IncreaseTemperature;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class TemperatureController {
    private final IncreaseTemperature increaseTemperature;
    private final DecreaseTemperature decreaseTemperature;

    public TemperatureController(IncreaseTemperature increaseTemperature,
                                 DecreaseTemperature decreaseTemperature) {
        this.increaseTemperature = increaseTemperature;
        this.decreaseTemperature = decreaseTemperature;
    }

    @PutMapping("/temperature/increment")
    public void tempIncrement (@RequestBody TempUpdateRequest tempUpdateRequest) {
        increaseTemperature.execute(tempUpdateRequest.temperature);
    }

    @PutMapping("/temperature/decrement")
    public void tempDecrement (@RequestBody TempUpdateRequest tempUpdateRequest) {
        decreaseTemperature.execute(tempUpdateRequest.temperature);
    }


    @ExceptionHandler(value = ClientError.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void handleException(Exception ex) {
        System.err.println("ERROR: " + ex.getMessage());
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
