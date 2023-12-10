package com.thermostate.externaltemperature.infrastucture;

import com.thermostate.shared.PropertiesLoader;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLConnection;
import java.util.Scanner;

@Component
public class URLBuilder {
  final PropertiesLoader properties;

  public URLBuilder(final PropertiesLoader properties) {
    this.properties = properties;
  }

  public String getTempFromWttIn() throws IOException {
    return readUrl(new java.net.URL(properties.getExternalTemp().get("url")).openConnection());
  }

  @NotNull
  private static String readUrl(final URLConnection con) throws IOException {
    try (Scanner scanner = new Scanner(con.getInputStream())) {
      StringBuilder response = new StringBuilder();
      while (scanner.hasNextLine()) {
        response.append(scanner.nextLine());
      }
      return response.toString();
    }
  }

}
