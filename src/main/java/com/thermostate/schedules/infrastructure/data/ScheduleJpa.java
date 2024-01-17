package com.thermostate.schedules.infrastructure.data;

import com.thermostate.schedules.model.Schedule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="Schedules")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleJpa {
    @Id
    private UUID id;
    @Column(name="week_days")
    private String weekDays;
    @Column(name="time_to")
    private String timeTo;
    @Column(name="time_from")
    private String timeFrom;
    private Boolean active;
    @Column(name="min_temp")
    private Integer minTemp;
    @Column(name="created_at")
    private LocalDate createdAt;

    public static ScheduleJpa fromDomain(Schedule schedule) {
        return new ScheduleJpa(schedule.id, schedule.weekDays, schedule.timeTo, schedule.timeFrom, true, schedule.minTemp, schedule.createdAt);
    }

    public Schedule toDomain() {
        return new Schedule(this.id, this.weekDays, this.timeFrom, this.timeTo, this.active, this.minTemp, this.createdAt);
    }
}
