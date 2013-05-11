package com.openaf.browser.gui.api.utils

import java.util.prefs.Preferences
import javafx.stage.{Stage, Screen}
import collection.mutable
import javafx.scene.image.{ImageView, Image}
import com.openaf.browser.gui.api.shortcutkeys.{LinuxShortCutKeys, WindowsShortCutKeys, OSXShortCutKeys}
import javafx.application.Platform
import javafx.scene.Node
import javafx.scene.control.SeparatorMenuItem
import javafx.geometry.Insets
import com.openaf.browser.gui.api.FrameLocation

object BrowserUtils {
  private val FrameLocationName = "frameLocation"
  private val Prefs = Preferences.userNodeForPackage(this.getClass)
  def initialFrameLocation = {
    val frameLocationString = Prefs.get(FrameLocationName, FrameLocation.default().asString)
    FrameLocation(frameLocationString).valid(Screen.getPrimary)
  }
  def storeFrameLocation(stage:Stage) {Prefs.put(FrameLocationName, FrameLocation(stage).asString)}
  def deletePreferences() {Prefs.remove(FrameLocationName)}
  private val ImageMap = new mutable.WeakHashMap[String,Image]
  def icon(iconName:String) = {
    val name = "/com/openaf/browser/gui/api/resources/" + iconName
    val image = ImageMap.getOrElseUpdate(name, new Image(resourceAsInputStream(name)))
    new ImageView(image)
  }
  private def resourceAsInputStream(name:String) = getClass.getResourceAsStream(name)
  def resource(resource:String) = getClass.getResource(resource).toExternalForm

  lazy val OS = {
    val osName = System.getProperty("os.name").toLowerCase
    if (osName.startsWith("mac")) OSX
    else if (osName.startsWith("linux")) Linux
    else if (osName.startsWith("windows")) WindowsUnknown
  }

  lazy val keyMap = {
    OS match {
      case OSX => new OSXShortCutKeys
      case Linux => new LinuxShortCutKeys
      case _ => new WindowsShortCutKeys
    }
  }

  def runLater(function: =>Unit) {
    Platform.runLater(new Runnable {def run() {function}})
  }

  def checkFXThread() {require(Platform.isFxApplicationThread, "This must be called on the FX Application Thread")}

  def imageViewOfNode(node:Node) = {
    val image = node.snapshot(null, null)
    new ImageView(image)
  }

  def separatorMenuItem = new SeparatorMenuItem

  def standardInsets = new Insets(5)
}