package com.thermostate.temperature.infrastructure;

import com.thermostate.shared.ClientError;
import com.thermostate.shared.ValueResponse;
import com.thermostate.temperature.application.DecreaseTemperature;
import com.thermostate.temperature.application.GetTemperature;
import com.thermostate.temperature.application.IncreaseTemperature;
import com.thermostate.temperature.model.Temperature;
import com.thermostate.temperature.model.TemperatureChange;
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
    private final GetTemperature getTemperature;

    public TemperatureController(IncreaseTemperature increaseTemperature,
                                 DecreaseTemperature decreaseTemperature, final GetTemperature getTemperature) {
        this.increaseTemperature = increaseTemperature;
        this.decreaseTemperature = decreaseTemperature;
        this.getTemperature = getTemperature;
    }

    @PutMapping("/temperature/increment")
    public void tempIncrement (@RequestBody TempUpdateRequest tempUpdateRequest) {
        increaseTemperature.execute(TemperatureChange.create(tempUpdateRequest.temperature));
    }

    @PutMapping("/temperature/decrement")
    public void tempDecrement (@RequestBody TempUpdateRequest tempUpdateRequest) {
        decreaseTemperature.execute(TemperatureChange.create(-tempUpdateRequest.temperature));
    }

    @GetMapping("/temperature")
    @ResponseBody
    public ValueResponse<Temperature> getTemp () {
        return new ValueResponse<>(getTemperature.execute());
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
