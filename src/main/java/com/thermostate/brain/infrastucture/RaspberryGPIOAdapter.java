package com.thermostate.brain.infrastucture;

import com.thermostate.brain.domain.ThermostatAdapter;
import com.thermostate.shared.PropertiesLoader;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class RaspberryGPIOAdapter implements ThermostatAdapter {
    final PropertiesLoader properties;
    private static final Logger logger = LoggerFactory.getLogger(RaspberryGPIOAdapter.class);

    @Override
    public void setState(boolean stateToOn) {
        if ("DEV".equals(properties.getEnv().trim())) {
            logger.info("Setting state to " + stateToOn);
            return;
        }
        try {
            String command = "";
            if (stateToOn){
                command = "gpio write 25 0";//enciende
            }else{
                command = "gpio write 25 1";//apaga
            }
            logger.trace("Executing " + command);
            Runtime.getRuntime().exec(command.split(" "));
        } catch (IOException e) {
            logger.error("No se ha podido activar/desactivar el thermostato: estamos en local?", e);
        }
    }
}
