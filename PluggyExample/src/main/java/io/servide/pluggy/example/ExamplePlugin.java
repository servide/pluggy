package io.servide.pluggy.example;

import com.google.inject.Inject;
import io.servide.pluggy.plugin.Plugger;
import io.servide.pluggy.plugin.PluginLogger;
import java.util.logging.Logger;

public class ExamplePlugin extends Plugger {

  @Inject
  @PluginLogger
  private Logger pluginLogger;

  @Override
  public void enable() {
    this.pluginLogger.info("I just used pluggy!");
  }

  @Override
  public void disable() {

  }

}
