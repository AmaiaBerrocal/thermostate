package com.thermostate.brain.infrastucture;

import com.thermostate.brain.domain.ThermostatAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RaspberryGPIOAdapter implements ThermostatAdapter {
    private static final Logger logger = LoggerFactory.getLogger(RaspberryGPIOAdapter.class);

    @Override
    public void setState(boolean stateToOn) {
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
