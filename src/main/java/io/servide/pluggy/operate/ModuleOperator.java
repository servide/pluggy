package io.servide.pluggy.operate;

import java.util.logging.Logger;

import com.google.inject.Inject;

import io.servide.common.inject.operate.TypeOperator;
import io.servide.pluggy.module.PluggyModule;
import io.servide.pluggy.plugin.PluginLogger;

public class ModuleOperator implements TypeOperator<PluggyModule> {

	@Inject
	@PluginLogger
	private Logger logger;

	@Override
	public void operate(PluggyModule pluggyModule)
	{
		this.logger.info("Enabled PluggyModule: [" + pluggyModule.getName() + "]");
	}

	@Override
	public Class<PluggyModule> getType()
	{
		return PluggyModule.class;
	}

}
