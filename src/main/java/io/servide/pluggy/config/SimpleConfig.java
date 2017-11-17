package io.servide.pluggy.config;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.google.inject.Binder;

import io.servide.common.except.Try;
import io.servide.common.json.Jackson;

public enum SimpleConfig {

	;

	public static void save(File dir, Object value)
	{
		String data = Jackson.objectToYaml(value);
		File target = fileForValue(dir, value.getClass());
		Try.to(() -> FileUtils.write(target, data));
	}

	public static void saveIfNotExists(File dir, Object value)
	{
		String data = Jackson.objectToYaml(value);
		File target = fileForValue(dir, value.getClass());
		if (!target.exists())
		{
			Try.to(() -> FileUtils.write(target, data));
		}
	}

	public static <T> T load(File dir, Class<T> tClass)
	{
		File target = fileForValue(dir, tClass);
		if (target.exists())
		{
			String data = Try.to(() -> FileUtils.readFileToString(target));
			return Jackson.yamlToObject(data, tClass);
		}
		T t = Try.to(tClass::newInstance);
		save(dir, t);
		return t;
	}

	public static <T> void bind(Binder binder, File dir, Class<T> tClass)
	{
		binder.bind(tClass).toInstance(load(dir, tClass));
	}

	private static File fileForValue(File dir, Class<?> aClass)
	{
		return FileUtils.getFile(dir, aClass.getSimpleName() + ".yml");
	}

}
