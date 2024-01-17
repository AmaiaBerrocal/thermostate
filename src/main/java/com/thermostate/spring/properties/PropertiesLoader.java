package com.thermostate.spring.properties;
 
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix="thermostate")
@Getter
@Setter
public class PropertiesLoader {
    private String dbUrl;
    private Map<String, String> roomTemp;
    private String env;
    private Map<String, String> externalTemp;
}
