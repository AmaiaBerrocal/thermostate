package com.thermostate.shared;
 
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix="thermostat")
@Getter
@Setter
public class PropertiesLoader {
    private String dbUrl;
    private Map<String, String> roomTemp;
}
