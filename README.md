# greenci-handler-plugin

This project is according to [greenci-maven-plugin](https://github.com/Spirals-Davidson/greenci-maven-plugin). 
He is used to print correct log data when you run your test.

/!\ YOU MUST HAVE IT TO [greenci-maven-plugin](https://github.com/Spirals-Davidson/greenci-maven-plugin) WORK CORRECTLY !
# Install 

- Clone the project in your computer
- Do `mvn clean install` 
- In the Maven .m2 configuration file, add :
```
  <PluginGroups>
       <PluginGroup> com.powerapi </ pluginGroup>
   </ PluginGroups>
```
- add the dependency in your pom.xml project 
```
  <dependency>
      <groupId>com.powerapi</groupId>
      <artifactId>handler-greenci-powerapi</artifactId>
      <version>1.0</version>
  </dependency>
```
- Configure your log in resources/application.yml
```
  logging:
    level:
      org.springframework.web: ERROR
      org.hibernate: INFO
      com.mkyong: DEBUG
    pattern:
        console: "%d{HH:mm:ss.S} [%thread] %-5level%logger{100} - %msg%n"
        file: "%d{HH:mm:ss.S} [%thread] %-5level %logger{100}- %msg%n"
    file: boot_example.log
```

# Usage 
Just put `@RunWith(GreenciTestRunner.class)` annotation on any test class you have. 

# Extends 
If you want load something before your class is set up without static `@BeforeClass`, 
you can extends your test class with `GreenciTestListener` like this :
```
public class MyTestClass extends GreenciTestListener {

    public void beforeClassSetup() throws Exception {
        //Do what you want here
    }
}
```

# More 
- If you need to initialized spring boot context, do like that in your test class : (MyTestClass is your test class)
```
public class MyTestClass extends GreenciTestListener {
    private TestContextManager testContextManager;
    public void beforeClassSetup() throws Exception {
        testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);
    }
}
```
