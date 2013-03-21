import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "example-multipage-shim"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    requireJs += "common.js",
    requireJs += "app/main1.js",
    requireJs += "app/main2.js",
    requireJsShim += "build.js"
  )

}
