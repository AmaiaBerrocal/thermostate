package com.thermostate.roomtemperature.infrastructure;

import com.thermostate.roomtemperature.model.RoomTemperature;
import com.thermostate.roomtemperature.model.RoomTemperatureRepo;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class RoomTemperatureFileRepo implements RoomTemperatureRepo {
    // this is the file in which my sensor writes the temperature that it measures
    final static String ROOM_TEMPERATURE_FILE = "/sys/bus/w1/devices/28-0415a4ddf9ff/w1_slave";

    @Override
    public RoomTemperature getTemp() {
        return new RoomTemperature(read());
    }

    public String read() {
        String res = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(
                ROOM_TEMPERATURE_FILE)))){

            res = br.lines()
                    .filter((line) -> line.contains("t="))
                    .map((line) -> line.substring(line.indexOf("t=") + 2))
                    .findFirst().get();

        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return transformReaded(res);
    }

    /** Temperature must be divided by 1000 because of the format in which is readed
     * @param temp
     * @return
     */
    private String transformReaded(String temp) {
        try{
            return String.valueOf((Double.parseDouble(temp)/1000));
        }catch (Exception e) {
            return temp;
        }
    }
}
