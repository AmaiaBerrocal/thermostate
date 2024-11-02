package com.thermostate.schedules.infrastructure;

import com.thermostate.schedules.application.*;
import com.thermostate.schedules.model.Schedule;
import com.thermostate.schedules.model.ScheduleView;
import com.thermostate.shared.ClientError;
import com.thermostate.shared.ValueResponse;
import com.thermostate.spring.security.model.LogedInUser;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin()
public class SchedulesController {

    private static final Logger logger = LoggerFactory.getLogger(SchedulesController.class);
    private final CreateSchedule createSchedule;
    private final GetScheduleById getScheduleById;

    private final GetAllSchedules getAllSchedules;

    private final DeleteSchedule deleteSchedule;

    private final UpdateSchedule updateSchedule;

    public SchedulesController(CreateSchedule createSchedule,
                               GetScheduleById getScheduleById,
                               GetAllSchedules getAllSchedules,
                               DeleteSchedule deleteSchedule,
                               UpdateSchedule updateSchedule) {
        this.createSchedule = createSchedule;
        this.getScheduleById = getScheduleById;
        this.getAllSchedules = getAllSchedules;
        this.deleteSchedule = deleteSchedule;
        this.updateSchedule = updateSchedule;
    }

    @PostMapping("/schedule")
    public void scheduleInsert(@RequestBody ScheduleCreateRequest scheduleCreateRequest) {
        LogedInUser loginUser = (LogedInUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.trace("Insert schedule received " + scheduleCreateRequest);
        createSchedule.execute(
                new Schedule(
                scheduleCreateRequest.id,
                scheduleCreateRequest.weekDays,
                scheduleCreateRequest.timeFrom,
                scheduleCreateRequest.active,
                scheduleCreateRequest.minTemp,
                loginUser.userType()));
    }

    @GetMapping("/schedule/{id}")
    @ResponseBody
    public ValueResponse<ScheduleView> scheduleGetById(@PathVariable UUID id) {
        logger.trace("Get schedule received " + id);
        return new ValueResponse<>(getScheduleById.execute(id));
    }

    @GetMapping("/schedules")
    @ResponseBody
    public ValueResponse<List<ScheduleView>> scheduleGetAll() {
        return new ValueResponse<>(getAllSchedules.execute());
    }

    @DeleteMapping("/schedule/{id}")
    public void deleteById(@PathVariable UUID id) {
        logger.trace("Delete schedule received " + id);
        deleteSchedule.execute(new Schedule(id));
    }

    @PutMapping("/schedule")
    public void scheduleUpdate(@RequestBody ScheduleUpdateRequest scheduleUpdateRequest) {
        LogedInUser loginUser = (LogedInUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.trace("Update schedule received " + scheduleUpdateRequest);
        updateSchedule.execute(new Schedule(
                        scheduleUpdateRequest.id,
                        scheduleUpdateRequest.weekDays,
                        scheduleUpdateRequest.timeFrom,
                        scheduleUpdateRequest.active,
                        scheduleUpdateRequest.minTemp,
                        loginUser.userType()));
    }

    @ExceptionHandler(value = ClientError.class) //client error porque es el usuario el que mete mal los datos
    @ResponseStatus(value = HttpStatus.BAD_REQUEST) //porque he metido mal algún dato en la petición
    public void handleException(Exception ex) {
        logger.error("ERROR: " + ex.getMessage());
        ex.printStackTrace();
    }
}

@AllArgsConstructor
@ToString
class ScheduleCreateRequest {
    UUID id;
    String weekDays;
    String timeFrom;
    Boolean active;
    Integer minTemp;
}

@AllArgsConstructor
@ToString
class ScheduleUpdateRequest {
    UUID id;
    String weekDays;
    String timeFrom;
    String timeTo;
    Boolean active;
    Integer minTemp;
}
