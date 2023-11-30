package com.thermostate.externaltemperature.infrastucture;

import com.thermostate.externaltemperature.model.ExternalTemperature;
import com.thermostate.externaltemperature.model.ExternalTemperatureRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TemperatureWttIn implements ExternalTemperatureRepo {

  final URLBuilder urlBuilder;

  public TemperatureWttIn(final URLBuilder urlBuilder) {
    this.urlBuilder = urlBuilder;
  }

  @Override
  public ExternalTemperature obtainExternalTemperature() {
    try {
      return buildExternalTemperature(urlBuilder.getTempFromWttIn());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @NotNull
  private static ExternalTemperature buildExternalTemperature(final String temp) throws IOException {
    return new ExternalTemperature(temp.replaceAll("\\D", ""));
  }
}
