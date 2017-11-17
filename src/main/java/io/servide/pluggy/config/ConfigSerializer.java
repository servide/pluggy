package io.servide.pluggy.config;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import io.servide.common.except.Try;

class ConfigSerializer extends StdSerializer<ModuleConfig> {

	protected ConfigSerializer()
	{
		super(ModuleConfig.class);
	}

	@Override
	public void serialize(ModuleConfig config, JsonGenerator generator, SerializerProvider provider)
			throws IOException
	{
		generator.writeStartObject();
		this.serialize(config, generator);
		generator.writeEndObject();
	}

	private void serialize(ModuleConfig config, JsonGenerator generator) throws IOException
	{
		generator.writeFieldName(config.getModuleName());
		generator.writeStartObject();
		generator.writeBooleanField("enabled", config.isEnabled());
		if (config.getChildren() != null)
		{
			config.getChildren().forEach(child -> Try.to(() -> this.serialize(child, generator)));
		}
		generator.writeEndObject();
	}

}
