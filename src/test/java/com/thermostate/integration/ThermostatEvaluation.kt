package com.thermostate.integration

import com.thermostate.ThermostateApplication
import com.thermostate.brain.domain.ThermostateStatus
import com.thermostate.roomtemperature.application.GetRoomTemperature
import com.thermostate.roomtemperature.model.RoomTemperature
import com.thermostate.roomtemperature.model.RoomTemperatureRepo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource


@SpringBootTest(
    classes = [ThermostateApplication::class]
)
@AutoConfigureMockMvc
@TestPropertySource(
    locations = ["classpath:application-test.yml"])
@ActiveProfiles("test")
class ThermostatEvaluation {

    @MockBean
    lateinit var repo : RoomTemperatureRepo

    @Autowired
    lateinit var getRoomTemperature: GetRoomTemperature

    @Autowired
    lateinit var status: ThermostateStatus

    @BeforeEach
    fun setup() {

    }

    @Test
    fun `when desired temp beneath current it should be switched off`() {
        given(repo.temp).willReturn(RoomTemperature.create(1000), RoomTemperature.create(2000))

        println("Readed for first time: ${getRoomTemperature.execute().temp}º")
        assertThat(status.active).isTrue()
        println("Readed for second time: ${getRoomTemperature.execute().temp}º")
        assertThat(status.active).isFalse()
    }

    @Test
    fun `when current temp above desired it should be switched on`() {
        given(repo.temp).willReturn(RoomTemperature.create(2000), RoomTemperature.create(1000))

        println("Readed for first time: ${getRoomTemperature.execute().temp}º")
        assertThat(status.active).isFalse()
        println("Readed for second time: ${getRoomTemperature.execute().temp}º")
        assertThat(status.active).isTrue()
    }

    @Test
    fun `when desired temp is changed to above current it should be switched on`() {

    }

    @Test
    fun `when desired temp is changed to beneath current it should be switched off`() {

    }

    @Test
    fun `when programmed temp changes to above current it should be switched on`() {

    }

    @Test
    fun `when programmed temp is changed manually should not be changed`() {

    }

    @Test
    fun `when schedule ends the ending temperature should be the minimum`() {

    }
}
