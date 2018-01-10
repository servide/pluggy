package io.servide.pluggy.plugin;

import com.google.inject.AbstractModule;
import io.servide.pluggy.config.ConfigScanner;
import java.util.logging.Logger;

public class PluginInjector extends AbstractModule {

  private final Plugger plugin;

  public PluginInjector(Plugger plugin) {
    this.plugin = plugin;
  }

  @Override
  protected void configure() {
    this.install(this.plugin);
    new ConfigScanner(this.plugin, this.binder());
    this.bind(Logger.class).annotatedWith(PluginLogger.class).toInstance(this.plugin.getLogger());
  }

}
