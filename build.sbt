name := "elasticsearch-api"

version := "1.0"

scalaVersion := "2.12.1"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/",
  "Scala Tools Snapshots" at "http://scala-tools.org/repo-snapshots/",
  "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
)

libraryDependencies ++= Seq(
  "com.sksamuel.elastic4s" %% "elastic4s-core" % "5.2.8",
  "com.sksamuel.elastic4s" %% "elastic4s-jackson" % "5.2.8",
  "com.sksamuel.elastic4s" %% "elastic4s-tcp" % "5.2.8",

  "com.typesafe.akka" %% "akka-http" % "10.0.4"
)
