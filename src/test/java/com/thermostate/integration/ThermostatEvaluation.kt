package com.thermostate.integration

import com.thermostate.roomtemperature.model.RoomTemperature
import com.thermostate.roomtemperature.model.RoomTemperatureRepo
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class ThermostatEvaluation {

    @MockBean
    lateinit var repo : RoomTemperatureRepo

    @Test
    fun `when desired temp above current it should be switched on`() {
        given(repo.temp).willReturn(RoomTemperature.create(10), RoomTemperature.create(20))


    }
}
