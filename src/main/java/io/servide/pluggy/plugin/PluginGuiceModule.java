package io.servide.pluggy.plugin;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import com.google.inject.AbstractModule;
import com.google.inject.binder.AnnotatedBindingBuilder;

import io.servide.common.inject.initialize.InitializerModule;
import io.servide.pluggy.config.auto.ConfigDiscovery;

final class PluginGuiceModule extends AbstractModule {

	private final PluggyPlugin plugin;

	PluginGuiceModule(PluggyPlugin plugin)
	{
		this.plugin = plugin;
	}

	@Override
	protected void configure()
	{
		this.install(new InitializerModule());

		this.bind(Plugin.class).toInstance(this.plugin);
		this.uncheckedBind(this.plugin.getClass()).toInstance(this.plugin);

		this.bind(Logger.class).annotatedWith(PluginLogger.class).toInstance(this.plugin.getLogger());
		this.bind(FileConfiguration.class).toInstance(this.plugin.getConfig());
		this.bind(File.class).annotatedWith(DataFolder.class).toInstance(this.plugin.getDataFolder());

		ConfigDiscovery.inject(plugin, this.binder());
	}

	private AnnotatedBindingBuilder<Object> uncheckedBind(Class<?> binding)
	{
		@SuppressWarnings("unchecked")
		Class<Object> castedBinding = (Class<Object>) binding;

		return this.bind(castedBinding);
	}

}
