package io.servide.pluggy.config;

import com.google.inject.Binder;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.servide.common.spigot.config.SimpleConfig;
import io.servide.pluggy.plugin.Plugger;
import java.io.File;
import java.util.Objects;

public class ConfigScanner {

  private final Plugger plugger;
  private final Binder binder;

  public ConfigScanner(Plugger plugger, Binder binder) {
    this.plugger = plugger;
    this.binder = binder;
    this.scan();
  }

  private void scan() {
    new FastClasspathScanner().addClassLoader(this.plugger.getClass().getClassLoader())
        .matchClassesWithAnnotation(PluggyConfig.class, this::process).scan(4);
  }

  private void process(Class<?> aClass) {
    String fileName = aClass.getAnnotation(PluggyConfig.class).fileName();
    Objects.requireNonNull(fileName);
    if (fileName.isEmpty()) {
      fileName = aClass.getSimpleName() + ".yml";
    }
    SimpleConfig.bind(this.binder, new File(this.plugger.getDataFolder(), fileName), aClass);
  }

}
