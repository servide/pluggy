package io.servide.pluggy.inject.operate;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import io.servide.common.inject.operate.TypeOperator;
import io.servide.common.spigot.plugin.PluginUtil;

public class ListenerOperator implements TypeOperator<Listener> {

	public void operate(Listener listener)
	{
		Bukkit.getPluginManager().registerEvents(listener, PluginUtil.getPlugin());
	}

	public Class<Listener> getType()
	{
		return Listener.class;
	}

}
