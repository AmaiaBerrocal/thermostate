package com.thermostate.roomtemperature.infrastructure;

import com.thermostate.roomtemperature.domain.RoomTemperature;
import com.thermostate.roomtemperature.domain.RoomTemperatureRepo;
import com.thermostate.spring.properties.PropertiesLoader;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class RoomTemperatureFileRepo implements RoomTemperatureRepo {
    // this is the file in which my sensor writes the temperature that it measures
    final PropertiesLoader properties;
    private static final Logger logger = LoggerFactory.getLogger(RoomTemperatureFileRepo.class);
    public RoomTemperatureFileRepo(final PropertiesLoader properties) {
        this.properties = properties;
    }

    @Override
    public RoomTemperature getTemp() {
        return RoomTemperature.create(read());
    }

    public Integer read() {
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
            logger.error("ERROR: " + e.getMessage(), e);
        }
        return "";
    }

    private Integer transformReaded(String temp) {
        // Actually sensor measures mili-C, we only need centi-C
        String toBeUsed = temp.substring(0, temp.length() - 1);
        return Integer.parseInt(toBeUsed);
    }
}
