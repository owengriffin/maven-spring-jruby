# JRuby experiments

## Install JRuby as a Maven artifact

You need to download the latest JRuby from [JRuby.org](http://www.jruby.org) and install it into your Maven repository. This allows it to be included as a Maven dependency for other projects.

    wget http://jruby.org.s3.amazonaws.com/downloads/1.5.3/jruby-complete-1.5.3.jar
    mvn install:install-file -Dfile=jruby-complete-1.5.3.jar -DgroupId=org.jruby -DartifactId=jruby-complete -Dversion=1.5.3 -Dpackaging=jar
    

Running the tests from the parent pom will not work because the artifacts to not get installed into the repository.