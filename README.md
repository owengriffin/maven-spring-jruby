# JRuby experiments

This is the code from a few JRuby, Maven and Spring experiments I've been doing over the last few days.

Spring has Dynamic Language support, which allows you to write beans in languages which can execute on the Java Virtual Machine (JVM). I've been experimenting with using JRuby to write beans in Ruby.

You can find an simple example of embedding Ruby in a Spring application within the _spring-helloworld_ Maven project.

Why am I doing this? Writing in Ruby has many advantages least of which is the support of a vast array of libraries. I hope to re-use some of the libraries within a Spring application.

My Spring application uses Maven to resolve dependencies, download the required libraries, executing any tests and packaging up the resulting JAR file. Maven has a central repository of "artifacts" which are Java libraries, packaged in JAR files with some associated meta data. 

Ruby libraries are known as Gems and can be installed directly into the environment (using a `gem install` command), as opposed to my Java application whereby the libraries are included with the application itself. 

## Environment or application scope?

Java libraries consist of classes which are loaded into the _class path_ when the application is executed. Ruby behaves similarly in that it will scan a load path for any libraries when you reference a Ruby library. The Ruby load path consists of locations in the Ruby environment, where-as the Java class path is normally list of JAR files the application uses. Maven manages the Java class path; any dependency specified in the pom.xml with the correct scope will be added automatically.

The key difference here is that the libraries for Java can be installed and loaded on a per-application basis whilst those for Ruby are usually installed on an per-environment basis. 

It is possible to maintain multiple Ruby environments on the same system using tools such as RVM or Bundler and associate that Ruby environment with the application - but this could be messy. Ideally I want of my environment contained within the resulting JAR of my Maven build script. I don't want my application to be too dependent on the system environment - I want them to be as loosely coupled as possible. 

NB: When referring to a Ruby environment I'm talking about the file structure and associated executables within the RUBY_HOME path.

## Maven Gems?

Maven doesn't download Gems and won't make them accessible to JRuby. JRuby doesn't include any Gems other than Rake and RSpec. Creating Maven modules for every Gem I need would be painful.

## Manipulating JRuby environment - bastardising jruby.home

JRuby uses the Java property `jruby.home` to point to it's Ruby environment. By default when you execute JRuby this will be the location of the extracted JAR file. Taking this into account it's therefore possible to change the `jruby.home` property to point to a location on the filing system of an alternative JRuby installation which already has the Gems installed.

    System.setProperty('jruby.home', '/home/ogriffin/.rvm/rubies/jruby/')
    
This isn't a good idea because it still relies on the the alternative JRuby environment to have the Gems installed. It also relies on that environment to be consistent across all the machines you deploy the application.

However it is the easiest solution, and you benefit from using Ruby-ish tools such as RVM.

## Corrupting the JRuby artifact

An alternative is to install the JRuby gems onto the JRuby artifact, re-name it and package the new modified-JRuby JAR with your application. This is the approach which I've settled for.

This project is contains 3 projects:

* _jruby-gems_ - produces a JAR file which contains only the gems I require within the project
* _jruby-custom_ - packages up jruby-gems and the JRuby Maven artifact _jruby-complete_ together
* _spring-gems_ - a simple Spring application which contains a Ruby script which depends on _jruby-custom_

### jruby-gems

This is ugly, really ugly. This module consists of a pom.xml file which executes `gem install` into a target folder. That target folder is packaged up as a JAR. It uses the maven-exec-plugin to run JRuby:

    <plugin>
      <groupId>org.codehaus.mojo</groupId>
      <artifactId>exec-maven-plugin</artifactId>
      <version>1.2</version>
      <configuration>
        <executable>java</executable>
        <mainClass>org.jruby.Main</mainClass>
        <arguments>
          <argument>-classpath</argument>
          <classpath />
          <argument>org.jruby.Main</argument>
          <argument>-S</argument>
          <argument>gem</argument>
          <argument>install</argument>
          <argument>--no-ri</argument>
          <argument>--no-rdoc</argument>
          <argument>-i</argument>
          <argument>target/classes</argument>
          <argument>chronic</argument>
          <argument>choices</argument>
        </arguments>
        </configuration>
        <executions>
          <execution>
            <goals>
			  <goal>exec</goal>
            </goals>
            <phase>prepare-package</phase>
          </execution>
        </executions>
      </plugin>

The last two <argument/> configuration elements within the configuration are the gems which need to be installed. The `-i` option specifies that the gems will be installed within the `target/classes` folder. 

### jruby-custom

Slightly less ugly - but still fairly nasty. This module uses the maven-shade-plugin to generate an _uber-jar_ containing all of the dependencies. For this module the only dependencies are JRuby and the jruby-gems module. This results in the 2 JARs being combined into an artifact which contains the gems from _jruby-gems_ and the JRuby runtime. This customised artifact can then be included in any application we like and will have access to the Gems.

### spring-gems

This is the main application. It has _jruby-custom_ as a dependency along with all that is required for a Spring application. It contains one test, ContextTest which will run the `jruby_hello.rb` script. This script has a reference to the `chronic` gem installed by _jruby-gems_.

## Install JRuby as a Maven artifact

You need to download the latest JRuby from [JRuby.org](http://www.jruby.org) and install it into your Maven repository. This allows it to be included as a Maven dependency for other projects.

    wget http://jruby.org.s3.amazonaws.com/downloads/1.5.3/jruby-complete-1.5.3.jar
    mvn install:install-file -Dfile=jruby-complete-1.5.3.jar -DgroupId=org.jruby -DartifactId=jruby-complete -Dversion=1.5.3 -Dpackaging=jar
    

Running the tests from the parent pom will not work because the artifacts to not get installed into the repository.

