package com.thermostate.brain.infrastucture;

import com.thermostate.spring.security.infrastucture.listeners.UserLoggedInListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class GPIOThermostateAdapter {
    private static final Logger logger = LoggerFactory.getLogger(GPIOThermostateAdapter.class);

    public static void setState(boolean stateToOn) {
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
