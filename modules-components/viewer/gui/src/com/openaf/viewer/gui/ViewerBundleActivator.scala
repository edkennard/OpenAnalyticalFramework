package com.openaf.viewer.gui

import org.osgi.framework.{BundleActivator, BundleContext}
import com.openaf.viewer.gui.components.ViewerPageComponentFactory
import com.openaf.viewer.api.{ViewerPage, ViewerPageFactory}
import com.openaf.browser.gui.api.{PageContext, BrowserActionButton, OpenAFApplication}

class ViewerBundleActivator extends BundleActivator {
  def start(context:BundleContext) {
    println("GUI Viewer Started!!")
    context.registerService(classOf[OpenAFApplication], ViewerBrowserApplication, null)
  }
  def stop(context:BundleContext) {
    println("GUI Viewer Stopped!!")
  }
}

object ViewerBrowserApplication extends OpenAFApplication {
  def applicationName = "Viewer"
  override def applicationButtons(context:PageContext) = List(BrowserActionButton("View", ViewerPageFactory))
  override def componentFactoryMap = Map(classOf[ViewerPage].getName -> ViewerPageComponentFactory)
}