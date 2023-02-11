package com.thermostate.roomtemperature.infrastructure;

import com.thermostate.roomtemperature.model.RoomTemperature;
import com.thermostate.shared.ClientError;
import com.thermostate.shared.ValueResponse;
import com.thermostate.roomtemperature.application.GetRoomTemperature;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class RoomTemperatureController {
    private final GetRoomTemperature getTemperature;

    public RoomTemperatureController(final GetRoomTemperature getTemperature) {
        this.getTemperature = getTemperature;
    }
    @GetMapping("/room-temperature")
    @ResponseBody
    public ValueResponse<RoomTemperature> getTemp () {
        return new ValueResponse<>(getTemperature.execute());
    }

    @ExceptionHandler(value = ClientError.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void handleException(Exception ex) {
        System.err.println("ERROR: " + ex.getMessage());
        ex.printStackTrace();
    }
}
