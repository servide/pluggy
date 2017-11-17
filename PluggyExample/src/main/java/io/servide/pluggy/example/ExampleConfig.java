package io.servide.pluggy.example;

import io.servide.pluggy.config.auto.Config;

@Config
public class ExampleConfig {

	private String testValue = "test";

	public String getTestValue()
	{
		return testValue;
	}

	public ExampleConfig setTestValue(String testValue)
	{
		this.testValue = testValue;
		return this;
	}

}
