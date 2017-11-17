package io.servide.pluggy.config;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ConfigSerializer.class)
@JsonDeserialize(using = ConfigDeserializer.class)
public class ModuleConfig {

	private String moduleName;
	private Boolean enabled;
	private List<ModuleConfig> children;

	public String getModuleName()
	{
		return this.moduleName;
	}

	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	public List<ModuleConfig> getChildren()
	{
		return this.children;
	}

	public void setChildren(List<ModuleConfig> children)
	{
		this.children = children;
	}

	public Boolean isEnabled()
	{
		return this.enabled;
	}

	public void setEnabled(Boolean enabled)
	{
		this.enabled = enabled;
	}

}
