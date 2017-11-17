package io.servide.pluggy.plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import io.servide.common.inject.module.Modular;
import io.servide.pluggy.config.ModuleConfig;

enum ConfigModules {

	;

	public static boolean isModuleEnabled(Modular module, ModuleConfig root)
	{
		return ConfigModules.isModuleEnabled(module, root, true);
	}

	public static boolean isModuleEnabled(Modular module, ModuleConfig root, boolean def)
	{
		return ConfigModules.reverseHierarchyLookup(module, root)
				.map(ModuleConfig::isEnabled)
				.orElse(def);
	}

	public static ModuleConfig replicateHierarchy(Modular modular)
	{
		ModuleConfig config = new ModuleConfig();
		config.setModuleName(modular.getName());
		config.setEnabled(modular.isEnabled());

		modular.children().forEach(child -> ConfigModules.addChild(config, child));

		return config;
	}

	private static void addChild(ModuleConfig config, Modular module)
	{
		List<ModuleConfig> children = Optional.ofNullable(config.getChildren())
				.orElseGet(ArrayList::new);

		ModuleConfig childConfig = new ModuleConfig();
		childConfig.setModuleName(module.getName());
		childConfig.setEnabled(module.isEnabled());

		children.add(childConfig);
		config.setChildren(children);

		module.children().forEach(child -> ConfigModules.addChild(childConfig, child));
	}

	public static Optional<ModuleConfig> reverseHierarchyLookup(Modular module, ModuleConfig root)
	{
		if (root == null)
		{
			return Optional.empty();
		}

		List<Modular> hierarchy = new ArrayList<>();
		hierarchy.add(module);

		Modular parent = module.parent();
		while (parent != null && parent.parent() != null)
		{
			hierarchy.add(parent);
			parent = parent.parent();
		}

		Collections.reverse(hierarchy);

		ModuleConfig config = root;

		for (Modular step : hierarchy)
		{

			if (config.getChildren() == null)
			{
				return Optional.empty();
			}

			config = config.getChildren().stream()
					.filter(predicate -> predicate.getModuleName().equals(step.getName()))
					.findFirst()
					.orElse(null);

			if (config == null)
			{
				return Optional.empty();
			}
		}

		return Optional.of(config);
	}

}
