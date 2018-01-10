package io.servide.pluggy.example;

import com.google.inject.Inject;
import io.servide.pluggy.Plugger;
import io.servide.pluggy.PluginLogger;
import java.util.logging.Logger;

public class ExamplePlugin extends Plugger {

  @Inject
  @PluginLogger
  private Logger pluginLogger;

  @Override
  public void enable() {
    this.pluginLogger.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%Loaded");
  }

  @Override
  public void disable() {

  }

}
