package com.thermostate.brain.infrastucture;

import org.springframework.stereotype.Component;

import java.io.IOException;

public class GPIOThermostateAdapter {
    public static void setState(boolean stateToOn) {
        try {
            String command = "";
            if (stateToOn){
                command = "gpio write 25 0";//enciende
            }else{
                command = "gpio write 25 1";//apaga
            }
            System.out.println("Executing " + command);
            Runtime.getRuntime().exec(command.split(" "));
        } catch (IOException e) {
            System.err.println("No se ha podido activar/desactivar el thermostato: estamos en local?");
        }
    }
}
