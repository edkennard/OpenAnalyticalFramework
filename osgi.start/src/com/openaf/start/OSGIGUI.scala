package com.openaf.start

import java.net.{URL, Socket}

object OSGIGUI {
  def main(args:Array[String]) {
    import StartUtils._
    if (!javaVersionValid(MinimumJavaVersion)) {
      throw new IllegalStateException(
        s"You need Java ${MinimumJavaVersion.major} update ${MinimumJavaVersion.update} or above to run OpenAF. " +
        s"You currently have Java ${ActualJavaVersion.major} update ${ActualJavaVersion.update}.")
    }
    println("Args: " + args.toList)
    val (url :: instanceName :: portForUpdates :: servicePort :: Nil) = args.toList
    val baseURL = new URL(url)
    val hostName = baseURL.getHost
    val guiProperties = Map("com.openAF.instanceName" -> instanceName, "com.openAF.hostName" -> hostName, "com.openAF.servicesPort" -> servicePort)
    val guiUpdater = new GUIUpdater(baseURL, instanceName)
    val guiConfig = guiUpdater.guiConfig
    val guiInstance = new OSGIInstance(guiConfig.name, guiConfig.bundles, guiProperties)

    new Thread(new Runnable {
      def run() {
        val hostForUpdate = hostName
        val socketForUpdate = new Socket(hostForUpdate, portForUpdates.toInt)
        val inputStream = socketForUpdate.getInputStream
        while (true) {
          inputStream.read
          println("^^^ Update GUI")
          guiInstance.update()
        }
      }
    }, "GUI Updater Thread").start()

    println("Starting GUI")
    guiInstance.start()
  }
}

object StartUtils {
  val MinimumJavaVersion = JavaVersion(8, 0)
  val ActualJavaVersion = {
    val javaVersion = System.getProperty("java.version")
    val (_ :: major :: update :: Nil) = javaVersion.split("\\.").toList
    val updateNumber = update.takeRight(2) match {
      case "ea" => 0
      case other => other.toInt
    }
    JavaVersion(major.toInt, updateNumber)
  }
  def javaVersionValid(minimumJavaVersion:JavaVersion) = {
    if (ActualJavaVersion.major == minimumJavaVersion.major) {
      ActualJavaVersion.update >= minimumJavaVersion.update
    } else {
      ActualJavaVersion.major >= minimumJavaVersion.major
    }
  }
}
case class JavaVersion(major:Int, update:Int)

case class GUIProperties(instanceName:String, hostName:String, servicePort:String)