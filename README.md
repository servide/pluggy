# pluggy

Google Guice for spigot

To learn more about [`Google's Guice`](https://github.com/google/guice)

For the [`Motivation`](https://github.com/google/guice/wiki/Motivation)behind this project

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
    <version>1.0-20171117.022138-1</version>
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
