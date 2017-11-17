package io.servide.pluggy.example;

import java.util.logging.Logger;

import com.google.inject.Inject;

import io.servide.pluggy.module.PluggyModule;
import io.servide.pluggy.plugin.PluginLogger;

public class ExampleModule extends PluggyModule {

	@Inject
	@PluginLogger
	Logger logger;

	@Inject
	ExampleConfig config;

	@Override
	public void enable()
	{
		logger.info("ExampleModule enabled!");
		logger.info("Config value = " + config.getTestValue());

		//will auto save changes
		config.setTestValue("value");
	}

}
