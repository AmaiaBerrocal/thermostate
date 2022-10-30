package com.thermostate.schedules.infrastructure;

import com.thermostate.schedules.application.CreateSchedule;
import com.thermostate.schedules.application.GetAllSchedules;
import com.thermostate.schedules.application.GetScheduleById;
import com.thermostate.schedules.model.Schedule;
import com.thermostate.shared.ClientError;
import com.thermostate.shared.ValueResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin()
public class SchedulesController {
    private final CreateSchedule createSchedule;
    private final GetScheduleById getScheduleById;

    private final GetAllSchedules getAllSchedules;

    public SchedulesController(CreateSchedule createSchedule, GetScheduleById getScheduleById, GetAllSchedules getAllSchedules) {
        this.createSchedule = createSchedule;
        this.getScheduleById = getScheduleById;
        this.getAllSchedules = getAllSchedules;
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

    @GetMapping("/schedule/{id}")
    @ResponseBody
    public ValueResponse<Schedule> scheduleGetById(@PathVariable Integer id) {
        return new ValueResponse<>(getScheduleById.execute(id));
    }

    @GetMapping("/schedules")
    @ResponseBody
    public ValueResponse<List<Schedule>> scheduleGetAll() {
        return new ValueResponse<>(getAllSchedules.execute());
    }

    @ExceptionHandler(value = ClientError.class) //tb client error porque es el usuario el que mete mal los datos
    @ResponseStatus(value = HttpStatus.BAD_REQUEST) //porque he metido mal algún dato en la petición
    public void handleException(Exception ex) {
        ex.printStackTrace();
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
