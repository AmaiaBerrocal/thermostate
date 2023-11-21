package com.thermostate.externaltemperture.infrastucture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class TemperatureWttInTest {

  @Mock
  URLBuilder urlBuilder;

  TemperatureWttIn sut;

  @BeforeEach
  void setup() {
    urlBuilder = mock(URLBuilder.class);
    sut = new TemperatureWttIn(urlBuilder);
  }

  @Test
  void should_format_temperature() throws IOException {
    given(urlBuilder.getTempFromWttIn()).willReturn("+9ºC");

    var response = sut.obtainExternalTemperature();

    assertThat(response.temp).isEqualTo("9");
  }

}
