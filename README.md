# pluggy

Google Guice for spigot

To learn more about [`Google's Guice`](https://github.com/google/guice)

For the [`Motivation`](https://github.com/google/guice/wiki/Motivation) behind this project

## Maven

```xml

<repository>
    <id>snapshots</id>
    <name>localhost-snapshots</name>
    <url>http://maven.servide.io/artifactory/servide-snapshot</url>
</repository>

<dependency>
    <groupId>io.servide</groupId>
    <artifactId>pluggy</artifactId>
    <version>1.0</version>
</dependency>
```

## Usage

For a simple code [`example`](https://github.com/servide/pluggy/tree/master/PluggyExample)

All of Google's Guice feature are enabled

* [`bindings`](https://github.com/google/guice/wiki/Bindings)
* [`scopes`](https://github.com/google/guice/wiki/Scopes)
* [`injections`](https://github.com/google/guice/wiki/Injections)
* [`aop`](https://github.com/google/guice/wiki/AOP)

For a full [`quick start`](https://github.com/google/guice/wiki/GettingStarted) guide

To start extend PluggyPlugin

```java
public class ExamplePlugin extends PluggyPlugin
```

Now you will have these methods available to you

```java
@Override
public void enable()
{
}

@Override
public void disable()
{
}

@Override
public void configure(Binder binder)
{
}
```

You can use `binder` in the configure method body to perform regular guice usage

Read [`Guide to Google Guice`](http://www.baeldung.com/guice), if you are still having trouble

To install a module simply 

```java
@Override
public void enable()
{
    install(ExampleModule.class);
}
```

'ExampleModule' looks something like so

```java
public class ExampleModule extends PluggyModule {

	@Inject
	@PluginLogger
	Logger logger;

	@Override
	public void enable()
	{
		logger.info("ExampleModule enabled!");
	}

}
```

## Auto Configs

We have introduced a simple way to manage you configs

This uses [`Jackson`](https://github.com/FasterXML/jackson) and therefor supports complex pojos

For a [`Jackson Guide`](http://www.baeldung.com/jackson-object-mapper-tutorial) and [`Jackson Annotations`](http://www.baeldung.com/jackson-annotations)

First create a config POJO, in this case our's is very basic

Make sure to add the '@config' or the config will not be discovered

```java
@Config
public class ExampleConfig {

	private String superSecret= "test";

        //getters and setters.....

}
```

To use this config in any module or plugin use

```java
@Inject
ExampleConfig config;
```

For a more concrete example

```java
public class ExampleModule extends PluggyModule {

	@Inject
	@PluginLogger
	Logger logger;

	@Inject
	ExampleConfig config;

	@Override
	public void enable()
	{
		logger.info(config.getSuperSecret());
	}

}
```

Note if we do 'config.setSuperSecret("notSoSecret")'

That change will be saved to the file system for you

For an immutable config use

```java
@Config
public class ExampleConfig {

        //"test" is the default value but will be overridden if a value is found in the yml file
	private final String superSecret= "test";

        //getters.....

}
```
