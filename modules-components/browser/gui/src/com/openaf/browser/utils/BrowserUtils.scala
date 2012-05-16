package com.openaf.browser.utils

import java.util.prefs.Preferences
import com.openaf.browser.FrameLocation
import javafx.stage.{Stage, Screen}
import collection.mutable.WeakHashMap
import javafx.scene.image.Image
import com.openaf.browser.shortcutkeys.{WindowsShortCutKeys, OSXShortCutKeys}

object BrowserUtils {
  private val FrameLocationName = "frameLocation"
  private val Prefs = Preferences.userNodeForPackage(this.getClass)
  def initialFrameLocation = {
    val frameLocationString = Prefs.get(FrameLocationName, FrameLocation.default().asString)
    FrameLocation(frameLocationString).valid(Screen.getPrimary)
  }
  def storeFrameLocation(stage:Stage) {Prefs.put(FrameLocationName, FrameLocation(stage).asString)}
  def deletePreferences() {Prefs.remove(FrameLocationName)}
  private val ImageMap = new WeakHashMap[String,Image]()
  def icon(iconName:String) = {
    val name = "/com/openaf/browser/resources/" + iconName
    ImageMap.getOrElseUpdate(name, new Image(resourceAsInputStream(name)))
  }
  private def resourceAsInputStream(name:String) = getClass.getResourceAsStream(name)

  lazy val OS = {
    val osName = System.getProperty("os.name").toLowerCase
    if (osName.startsWith("mac")) OSX
    else if (osName.startsWith("linux")) Linux
    else if (osName.startsWith("windows")) WindowsUnknown
  }

  lazy val keyMap = {
    OS match {
      case OSX => new OSXShortCutKeys
      case _ => new WindowsShortCutKeys
    }
  }
}