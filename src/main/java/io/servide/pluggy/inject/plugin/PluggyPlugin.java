package io.servide.pluggy.inject.plugin;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import io.servide.common.config.Configs;
import io.servide.common.except.Try;
import io.servide.common.inject.discover.Discovery;
import io.servide.common.inject.module.Modular;
import io.servide.pluggy.config.ModuleConfig;
import io.servide.pluggy.inject.module.Module;
import io.servide.pluggy.inject.module.PluginModule;

public abstract class PluggyPlugin extends JavaPlugin implements Modular {

	private static final String MODULE_CONFIG_NAME = "modules.yml";
	private final File moduleConfigFile = this.findModuleConfigFile();
	private ModuleConfig moduleConfig = this.findModuleConfig(this.moduleConfigFile);
	private Injector injector;
	@Inject
	private PluginModule module;

	public static Injector getInjector()
	{
		return JavaPlugin.getPlugin(PluggyPlugin.class).injector;
	}

	public static boolean isModuleEnabled(Module module)
	{
		return ConfigModules
				.isModuleEnabled(module, JavaPlugin.getPlugin(PluggyPlugin.class).moduleConfig);
	}

	@Override
	public final void onEnable()
	{
		this.injector = Guice.createInjector(new PluginGuiceModule(this), this::configure);
		this.injector.injectMembers(this);

		Discovery.discover(this.injector);

		this.enable();
		this.module.discoverIfIsEnabled();
		this.checkModuleConfig();
		this.module.onEnable();
	}

	@Override
	public final void onDisable()
	{
		this.disable();
	}

	private void configure(Binder binder)
	{
	}

	public void enable()
	{

	}

	public void disable()
	{

	}

	@Override
	public final void install(Class<?> type)
	{
		this.module.install(type);
	}

	@Override
	public final Modular parent()
	{
		return this.module.parent();
	}

	@Override
	public final List<Modular> children()
	{
		return this.module.children();
	}

	@Override
	public final void discoverIfIsEnabled()
	{
		throw new IllegalStateException("Illegal operation");
	}

	public ModuleConfig getModuleConfig()
	{
		return this.moduleConfig;
	}

	public void saveModuleConfig()
	{
		Configs.saveUsingYaml(this.moduleConfigFile, this.moduleConfig);
	}

	private void checkModuleConfig()
	{
		this.moduleConfig = ConfigModules.replicateHierarchy(this.module);
		this.saveModuleConfig();
	}

	private File findModuleConfigFile()
	{
		File file = new File(
				this.getDataFolder(),
				PluggyPlugin.MODULE_CONFIG_NAME
		);

		if (!file.exists())
		{
			Try.to(file::createNewFile);
		}

		return file;
	}

	private ModuleConfig findModuleConfig(File file)
	{
		if (Try.to(() -> FileUtils.readFileToByteArray(file)).length == 0)
		{
			return new ModuleConfig();
		}

		return Configs.unwrapUsingYaml(file, ModuleConfig.class);
	}

}
