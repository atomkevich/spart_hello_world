name := "spark_parse"

version := "1.0"

scalaVersion := "2.11.7"
/*
libraryDependencies += "org.apache.spark" %% "spark-core" % "1.4.0"*/
/*
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "1.4.0"*/
libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "0.1.1"

libraryDependencies +=  "org.apache.spark" % "spark-core_2.11" % "1.4.0"


libraryDependencies +=  "org.apache.hbase" % "hbase-common" % "1.1.0.1"

libraryDependencies +=  "org.apache.hbase" % "hbase-client" % "1.1.0.1"

libraryDependencies +=  "org.apache.hbase" % "hbase-server" % "1.1.0.1"

//libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "1.2.0"

resolvers ++= Seq("Akka Repository" at "http://repo.akka.io/releases/")

resolvers ++= Seq("Spray Repository" at "http://repo.spray.cc/")