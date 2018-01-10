package io.servide.pluggy.example;

import io.servide.pluggy.config.PluggyConfig;

@PluggyConfig(fileName = "test.yml")
public class ExampleConfig {

  private final String test = "This is a test";

  public String getTest() {
    return test;
  }

}
