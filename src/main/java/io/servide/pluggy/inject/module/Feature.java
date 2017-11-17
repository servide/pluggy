package io.servide.pluggy.inject.module;

import java.util.logging.Logger;

import com.google.inject.Inject;

import io.servide.common.inject.module.Enableable;
import io.servide.pluggy.inject.plugin.PluginLogger;

public class Feature implements Enableable {

	@Inject
	@PluginLogger
	private Logger logger;

	@Override
	public final void onEnable()
	{
		this.enable();
		this.logger.info("Enabled Feature [" + this.getName() + "]");
	}

	public void enable()
	{

	}

}
