package com.thermostate.integration

import com.thermostate.brain.domain.ManuallyEstablishedTemperature
import com.thermostate.brain.domain.ThermostateStatus
import com.thermostate.roomtemperature.application.GetRoomTemperature
import com.thermostate.roomtemperature.model.RoomTemperature
import com.thermostate.roomtemperature.model.RoomTemperatureRepo
import com.thermostate.schedules.application.GetAllSchedules
import com.thermostate.schedules.application.ScheduleChecker
import com.thermostate.schedules.infrastructure.DateHelper
import com.thermostate.schedules.model.Schedule
import com.thermostate.shared.SchedulingConfigurator
import com.thermostate.shared.domain.Temperature
import com.thermostate.targettemperature.application.IncreaseTargetTemperature
import com.thermostate.targettemperature.model.TemperatureChange
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.MockReset
import org.springframework.boot.test.mock.mockito.SpyBean
import java.time.LocalDate
import java.util.*


class ThermostatEvaluation : IntegrationTest() {

    @SpyBean(reset = MockReset.BEFORE)
    lateinit var repo : RoomTemperatureRepo

    @SpyBean(reset = MockReset.BEFORE)
    lateinit var dateHelper: DateHelper

    @SpyBean(reset = MockReset.BEFORE)
    lateinit var getAllSchedules: GetAllSchedules

    @Autowired
    lateinit var getRoomTemperature: GetRoomTemperature

    @Autowired
    lateinit var status: ThermostateStatus

    @Autowired
    lateinit var increaseTargetTemperature: IncreaseTargetTemperature

    @Autowired
    lateinit var scheduleChecker: ScheduleChecker

    @BeforeEach
    fun setup() {
    }

    @Test
    fun `when desired temp beneath current it should be switched off`() {
        given(repo.getTemp()).willReturn(RoomTemperature.create(1000), RoomTemperature.create(2000))

        println("Readed for first time: ${getRoomTemperature.execute().temp}º")
        assertThat(status.active).isTrue()
        println("Readed for second time: ${getRoomTemperature.execute().temp}º")
        assertThat(status.active).isFalse()
    }

    @Test
    fun `when current temp above desired it should be switched on`() {
        /*given(repo.getTemp()).willReturn(RoomTemperature.create(2000), RoomTemperature.create(800))
        //status.setManualTemperature(Temperature(15))
        println("Readed for first time: ${getRoomTemperature.execute()}º")
        assertThat(status.active).isTrue()
        println("Readed for second time: ${getRoomTemperature.execute()}º")
        assertThat(status.active).isFalse()*/
    }

    @Test
    fun `when desired temp is changed to above current it should be switched on`() {
        given(repo.temp).willReturn(RoomTemperature.create(1100))
        assertThat(status.active).isFalse()
        increaseTargetTemperature.execute(TemperatureChange(500))
        assertThat(status.active).isTrue()
        println("Readed for first time: ${getRoomTemperature.execute().temp}º")

    }

    @Test
    fun `when desired temp is changed to beneath current schedule should be switched off`() {

    }

    @Test
    fun `when programmed temp changes to above current it should be switched on`() {
        //given(repo.temp).willReturn(RoomTemperature.create(900))
        val schedule =
            Schedule(UUID.randomUUID(), "L", "09:00", "20:00", true, 2500, LocalDate.now())
        given(dateHelper.nowAsDayOfWeek()).willReturn("L")
        given(dateHelper.nowAsNumber()).willReturn(1630)
        //given(dateHelper.hourOfDayAsNumber("12:34")).willReturn(1234)

        given(getAllSchedules.execute()).willReturn(listOf(schedule))

        scheduleChecker.execute(getAllSchedules.execute())
        assertThat(status.active).isTrue()
    }

    @Test
    fun `when they are many active schedules higher temp should be the target`() {
        //given(repo.temp).willReturn(RoomTemperature.create(900))
        val schedule1 =
            Schedule(UUID.randomUUID(), "L", "09:00", "20:00", true, 2500, LocalDate.now())
        val schedule2 =
            Schedule(UUID.randomUUID(), "L", "09:00", "20:00", true, 2600, LocalDate.now())
        val schedule3 =
            Schedule(UUID.randomUUID(), "L", "09:00", "20:00", true, 2400, LocalDate.now())
        given(dateHelper.nowAsDayOfWeek()).willReturn("L")
        given(dateHelper.nowAsNumber()).willReturn(1630)
        //given(dateHelper.hourOfDayAsNumber("12:34")).willReturn(1234)

        given(getAllSchedules.execute()).willReturn(listOf(schedule1, schedule2, schedule3))

        scheduleChecker.execute(getAllSchedules.execute())
        assertThat(status.active).isTrue()
    }

    @Test
    fun `when programmed temp is changed manually should not be changed`() {

    }

    @Test
    fun `when schedule ends the ending temperature should be the minimum between scheduled and manual`() {

    }
}
