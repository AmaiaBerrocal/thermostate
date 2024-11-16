package com.thermostate.externaltemperature.infrastucture;

import com.thermostate.externaltemperature.domain.ExternalTemperature;
import com.thermostate.externaltemperature.domain.ExternalTemperatureRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@ConditionalOnExpression("'${spring.profiles.active}' == 'dev'")
public class DummieWttIn implements ExternalTemperatureRepo {


  @Override
  public ExternalTemperature obtainExternalTemperature() {
    return new ExternalTemperature("42");
  }

}
