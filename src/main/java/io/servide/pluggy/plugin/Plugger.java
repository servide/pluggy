package io.servide.pluggy.plugin;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Plugger extends JavaPlugin implements Module {

  private Injector injector;
  private final List<Module> modules = new ArrayList<>();

  public abstract void enable();

  public abstract void disable();

  public void onEnable() {
    this.injector = Guice.createInjector(new PluginInjector(this));
    this.injector.injectMembers(this);
    this.modules.forEach(this.injector::injectMembers);
    this.enable();
  }

  public void onDisable() {

  }

  public void configure(Binder binder) {
  }

  public Injector getInjector() {
    return this.injector;
  }

  public void install(Binder binder, Module module) {
    binder.install(module);
    this.modules.add(module);
  }

}
