package com.thermostate.roomtemperature.infrastructure;

import com.thermostate.roomtemperature.model.RoomTemperature;
import com.thermostate.roomtemperature.model.RoomTemperatureRepo;
import com.thermostate.shared.PropertiesLoader;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class RoomTemperatureFileRepo implements RoomTemperatureRepo {
    // this is the file in which my sensor writes the temperature that it measures
    final PropertiesLoader properties;

    public RoomTemperatureFileRepo(final PropertiesLoader properties) {
        this.properties = properties;
    }

    @Override
    public RoomTemperature getTemp() {
        return new RoomTemperature(read());
    }

    public String read() {
        String res = readRoomTemperatureFromFile();
        return transformReaded(res);
    }

    @Nullable
    private String readRoomTemperatureFromFile() {
        String roomTemperatureFile = properties.getRoomTemp().get("file");
        String markOnFile = properties.getRoomTemp().get("temp_mark_in_file");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(
                roomTemperatureFile)))){
            return br.lines()
                .filter((line) -> line.contains(markOnFile))
                .map((line) -> line.substring(line.indexOf(markOnFile) + 2))
                .findFirst().get();
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
        return "";
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
