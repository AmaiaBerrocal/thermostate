package com.thermostate.schedules.model;

import net.datafaker.Faker;

import java.time.LocalDate;
import java.util.UUID;

public class ScheduleObjectMother{

		public static Schedule givenScheduleForDays(String weekDays){
        return givenSchedule("19:00", "22:00", weekDays, true, 15);
    }

	public static Schedule givenScheduleWithTimes(String fromTime, String toTime){
		return givenSchedule(fromTime, toTime, "L,M", true, 15);
	}

	public static Schedule givenScheduleWithActivation(Boolean activation){
		return givenSchedule("19:00", "22:00", "L,M", activation, 15);
	}

	public static Schedule givenScheduleWithMinTemp(Integer minTemp){
		return givenSchedule("19:00", "22:00", "L,M", true, minTemp);
	}

	public static Schedule givenSchedule(String fromTime, String toTime, String weekDays, Boolean active, Integer mintemp){
		return new Schedule(
				UUID.randomUUID(),
				weekDays,
				fromTime,
				toTime,
				active,
				mintemp,
				LocalDate.EPOCH);
	}
}
