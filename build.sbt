
organization := "com.dbd"

name := "scalandradevday"

scalaVersion := "2.10.3"

scalacOptions += "-target:jvm-1.7"

javacOptions ++= Seq("-source", "1.7")

libraryDependencies ++= Seq(
    "com.datastax.cassandra" % "cassandra-driver-core" % "2.0.1",
    "log4j" % "log4j" % "1.2.17",
    "org.springframework" % "spring-webmvc" % "3.2.5.RELEASE",
    "org.springframework" % "spring-aop" % "3.2.5.RELEASE",
    "com.fasterxml.jackson.jaxrs" % "jackson-jaxrs-json-provider" % "2.2.3",
    "commons-codec" % "commons-codec" % "1.8",
    "org.apache.commons" % "commons-io" % "1.3.2",
    "org.apache.commons" % "commons-lang3" % "3.1",
    "org.cassandraunit" % "cassandra-unit" % "2.0.2.1-SNAPSHOT" % "test",
    "junit" % "junit" % "4.8.2" % "test",
    "com.novocode" % "junit-interface" % "0.10" % "test",
    "org.scalatest" %% "scalatest" % "2.1.0" % "test"
)

seq(webSettings :_*)

libraryDependencies ++= Seq(
    "org.eclipse.jetty" % "jetty-webapp" % "9.1.0.v20131115" % "container",
    "org.eclipse.jetty" % "jetty-plus"   % "9.1.0.v20131115" % "container"
)

