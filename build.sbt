lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """ReactiveApplicationDevelopment02""",
    organization := "com.example",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.12.12",
    libraryDependencies ++= Seq(
      guice,
      ws,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )
