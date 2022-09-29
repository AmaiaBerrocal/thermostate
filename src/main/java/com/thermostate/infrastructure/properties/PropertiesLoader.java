package com.thermostate.infrastructure.properties;
 
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
 
@Component
@ConfigurationProperties(prefix="db")
@Getter
@Setter
public class PropertiesLoader {
    private String url;
}