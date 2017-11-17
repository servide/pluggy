package io.servide.pluggy.example;

import java.util.logging.Logger;

import com.google.inject.Inject;

import io.servide.pluggy.plugin.PluggyPlugin;
import io.servide.pluggy.plugin.PluginLogger;

public class ExamplePlugin extends PluggyPlugin {

	@Inject
	@PluginLogger
	Logger logger;

	@Override
	public void enable()
	{
		logger.info("ExamplePlugin loaded!");
		install(ExampleModule.class);
	}

}
