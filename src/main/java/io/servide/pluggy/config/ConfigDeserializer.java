package io.servide.pluggy.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.common.collect.Streams;

class ConfigDeserializer extends StdDeserializer<ModuleConfig> {

	private static final String ROOT_NAME = "plugin";

	protected ConfigDeserializer()
	{
		super(ModuleConfig.class);
	}

	@Override
	public ModuleConfig deserialize(JsonParser parser, DeserializationContext context)
			throws IOException
	{
		ModuleConfig root = new ModuleConfig();

		JsonNode rootNode = parser.getCodec().readTree(parser);

		this.populateConfig(root, ConfigDeserializer.ROOT_NAME,
				rootNode.get(ConfigDeserializer.ROOT_NAME));

		return root;
	}

	private void populateConfig(ModuleConfig config, String key, JsonNode node)
	{
		config.setModuleName(key);
		config.setEnabled(node.get("enabled").asBoolean());

		Streams.stream(node.fields())
				.filter(entry -> !entry.getKey().equals("enabled"))
				.forEach(entry -> {
					ModuleConfig child = new ModuleConfig();
					List<ModuleConfig> children = Optional.ofNullable(config.getChildren())
							.orElseGet(ArrayList::new);

					children.add(child);
					config.setChildren(children);

					this.populateConfig(child, entry.getKey(), entry.getValue());
				});
	}

}
