package com.thermostate.schedules.infrastructure.data;

import com.thermostate.schedules.model.Schedule;
import com.thermostate.schedules.model.ScheduleView;
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
    @Column(name="time_from")
    private String timeFrom;
    private Boolean active;
    @Column(name="min_temp")
    private Integer minTemp;
    @Column(name="created_at")
    private LocalDate createdAt;

    public static ScheduleJpa fromDomain(Schedule schedule) {
        return new ScheduleJpa(schedule.id, schedule.weekDays, schedule.timeFrom, schedule.active, schedule.minTemp, schedule.createdAt);
    }

    public ScheduleView toDomain() {
        return new ScheduleView(this.id, this.weekDays, this.timeFrom, this.active, this.minTemp, this.createdAt);
    }
}
