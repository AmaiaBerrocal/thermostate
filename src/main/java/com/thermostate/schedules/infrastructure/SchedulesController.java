package com.thermostate.schedules.infrastructure;

import com.thermostate.schedules.application.CreateSchedule;
import com.thermostate.shared.ClientError;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class SchedulesController {
    private final CreateSchedule createSchedule;

    public SchedulesController(CreateSchedule createSchedule) {
        this.createSchedule = createSchedule;
    }

    @PostMapping("/schedule")
    public void scheduleInsert(@RequestBody ScheduleCreateRequest scheduleCreateRequest) {
        createSchedule.execute(scheduleCreateRequest.dateFrom,
                scheduleCreateRequest.dateTo,
                scheduleCreateRequest.timeFrom,
                scheduleCreateRequest.timeTo,
                scheduleCreateRequest.active,
                scheduleCreateRequest.minTemp);
    }

    @ExceptionHandler(value = ClientError.class) //tb client error porque es el usuario el que mete mal los datos
    @ResponseStatus(value = HttpStatus.BAD_REQUEST) //porque he metido mal algún dato en la petición
    public void handleException(Exception ex) {
    }
}

@AllArgsConstructor
class ScheduleCreateRequest {
    LocalDate dateFrom;
    LocalDate dateTo;
    String timeFrom;
    String timeTo;
    Boolean active;
    Integer minTemp;
}
