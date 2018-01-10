package io.servide.pluggy.example;

import com.google.inject.Inject;
import io.servide.pluggy.plugin.Plugger;
import io.servide.pluggy.plugin.PluginLogger;
import java.util.logging.Logger;

public class ExamplePlugin extends Plugger {

  @Inject
  @PluginLogger
  private Logger pluginLogger;

  @Inject
  ExampleConfig exampleConfig;

  @Override
  public void enable() {
    this.pluginLogger.info("I just used pluggy!");
    this.pluginLogger.info("Pluggy Config = " + exampleConfig.getTest());
  }

  @Override
  public void disable() {

  }

}
