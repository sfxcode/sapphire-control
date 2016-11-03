
name := "sapphire-extension"

organization := "com.sfxcode.sapphire"

version := "0.5.8"

crossScalaVersions := Seq("2.12.0",  "2.11.8")

scalaVersion <<= crossScalaVersions { versions => versions.head }


scalacOptions += "-deprecation"

javacOptions += "-Dorg.apache.deltaspike.ProjectStage=Test"

parallelExecution in Test := false

javacOptions ++= Seq("-source", "1.8")

javacOptions ++= Seq("-target", "1.8")

scalacOptions += "-target:jvm-1.8"

// resolvers

resolvers += "sonatype-snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
resolvers += "bintray" at "https://jcenter.bintray.com"

// Test

libraryDependencies += "org.specs2" %% "specs2-core" % "3.8.6" % "test"

libraryDependencies += "org.json4s" %% "json4s-native" % "3.4.2" % "test"

// Compile

libraryDependencies += "com.sfxcode.sapphire" %% "sapphire-core" % "1.1.8"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.12"

libraryDependencies += "org.controlsfx" % "controlsfx" % "8.40.12" intransitive()

libraryDependencies += "de.jensd" % "fontawesomefx" % "8.9"

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

bintrayReleaseOnPublish in ThisBuild := false
