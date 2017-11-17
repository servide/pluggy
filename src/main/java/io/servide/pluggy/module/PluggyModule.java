package io.servide.pluggy.module;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.inject.Inject;

import io.servide.common.inject.module.Enableable;
import io.servide.common.inject.module.Modular;
import io.servide.common.inject.operate.OperatorRegistry;
import io.servide.common.name.Names;
import io.servide.pluggy.plugin.PluggyPlugin;
import io.servide.pluggy.plugin.PluginLogger;

public abstract class PluggyModule implements Modular {

	private final List<Modular> children = new ArrayList<>();
	private final List<Object> installations = new ArrayList<>();

	private Modular parent;
	private boolean enabled;

	@Inject
	@PluginLogger
	private Logger logger;

	@Override
	public final void install(Class<?> type)
	{
		Object instance = PluggyPlugin.getInjector().getInstance(type);

		Boolean enabled = null;

		if (instance instanceof Modular)
		{
			this.children.add((Modular) instance);

			if (instance instanceof PluggyModule)
			{
				PluggyModule pluggyModule = (PluggyModule) instance;
				pluggyModule.parent = this;
				enabled = pluggyModule.enabled;
			}
		}
		else
		{
			this.installations.add(instance);
		}

		this.logger.info(this.getName() + " installed: [" + Names.getName(type) + "]" +
				(enabled != null ? " (Enabled: " + enabled + ")" : ""));
	}

	@Override
	public final Modular parent()
	{
		return this.parent;
	}

	@Override
	public final List<Modular> children()
	{
		return this.children;
	}

	@Override
	public final boolean isEnabled()
	{
		return this.enabled;
	}

	@Override
	public final void onEnable()
	{
		if (this.enabled)
		{
			OperatorRegistry.operateOn(this);
			this.enable();
			this.children.stream().filter(Modular::isEnabled).forEach(Modular::onEnable);
			this.installations.forEach(this::enableInstallation);
		}
	}

	private void enableInstallation(Object candidate)
	{
		OperatorRegistry.operateOn(candidate);

		if (candidate instanceof Enableable)
		{
			((Enableable) candidate).onEnable();
		}
	}

	public void enable()
	{

	}

	@Override
	public final void discoverIfIsEnabled()
	{
		this.enabled = PluggyPlugin.isModuleEnabled(this);
		this.children.forEach(Modular::discoverIfIsEnabled);
	}


}
