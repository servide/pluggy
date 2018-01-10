package io.servide.pluggy;

import com.google.inject.AbstractModule;
import java.util.logging.Logger;

public class PluginInjector extends AbstractModule {

  private final Plugger plugin;

  public PluginInjector(Plugger plugin) {
    this.plugin = plugin;
  }

  @Override
  protected void configure() {
    this.install(this.plugin);
    this.bind(Logger.class).annotatedWith(PluginLogger.class).toInstance(this.plugin.getLogger());
  }

}
