package com.thermostate.schedules.infrastructure;

import com.thermostate.schedules.application.*;
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

    private final DeleteSchedule deleteSchedule;

    private final UpdateSchedule updateSchedule;

    public SchedulesController(CreateSchedule createSchedule, GetScheduleById getScheduleById, GetAllSchedules getAllSchedules, DeleteSchedule deleteSchedule, UpdateSchedule updateSchedule) {
        this.createSchedule = createSchedule;
        this.getScheduleById = getScheduleById;
        this.getAllSchedules = getAllSchedules;
        this.deleteSchedule = deleteSchedule;
        this.updateSchedule = updateSchedule;
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

    @DeleteMapping("/schedule/{id}")
    public void deleteById(@PathVariable Integer id) {

        deleteSchedule.execute(id);
    }

    @PutMapping("/schedule")
    public void scheduleUpdate(@RequestBody ScheduleUpdateRequest scheduleUpdateRequest) {
        updateSchedule.execute(scheduleUpdateRequest.id,
                scheduleUpdateRequest.dateFrom,
                scheduleUpdateRequest.dateTo,
                scheduleUpdateRequest.timeFrom,
                scheduleUpdateRequest.timeTo,
                scheduleUpdateRequest.active,
                scheduleUpdateRequest.minTemp);
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

@AllArgsConstructor
class ScheduleUpdateRequest {
    Integer id;
    LocalDate dateFrom;
    LocalDate dateTo;
    String timeFrom;
    String timeTo;
    Boolean active;
    Integer minTemp;
}
