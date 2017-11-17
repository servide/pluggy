package io.servide.pluggy.inject.operate;

import java.util.logging.Logger;

import com.google.inject.Inject;

import io.servide.common.inject.operate.TypeOperator;
import io.servide.pluggy.inject.module.Module;
import io.servide.pluggy.inject.plugin.PluginLogger;

public class ModuleOperator implements TypeOperator<Module> {

	@Inject
	@PluginLogger
	private Logger logger;

	@Override
	public void operate(Module module)
	{
		this.logger.info("Enabled Module: [" + module.getName() + "]");
	}

	@Override
	public Class<Module> getType()
	{
		return Module.class;
	}

}
