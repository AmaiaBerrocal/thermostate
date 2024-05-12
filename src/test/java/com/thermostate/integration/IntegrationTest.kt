package com.thermostate.integration

import com.thermostate.ThermostateApplication
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@SpringBootTest(classes = [ThermostateApplication::class])
@AutoConfigureMockMvc
@TestPropertySource(locations = ["classpath:application-test.yml"])
@ActiveProfiles("test")
open class IntegrationTest {}