package com.openaf.travel.gui

import com.openaf.osgi.OpenAFBundleActivator
import components.HotelsPageComponentFactory
import org.osgi.framework.BundleContext
import com.openaf.browser.gui.{BrowserActionButton, PageContext, OpenAFApplication}
import com.openaf.travel.api.{HotelsPage, FlightsAndHotelsPageFactory, HotelsPageFactory}

class TravelBundleActivator extends OpenAFBundleActivator {
  def start(context:BundleContext) {
    println("TravelBundleActivator gui started")
    context.registerService(classOf[OpenAFApplication], TravelBrowserApplication, null)
  }
  def stop(context:BundleContext) {
    println("TravelBundleActivator gui stopped")
  }
}

object TravelBrowserApplication extends OpenAFApplication {
  def applicationName = "Travel"
  override def applicationButtons(context:PageContext) = {
    List(
      BrowserActionButton("Hotels", HotelsPageFactory),
      BrowserActionButton("Flights and Hotels", FlightsAndHotelsPageFactory)
    )
  }
  override def componentFactoryMap = Map(classOf[HotelsPage].getName -> HotelsPageComponentFactory)
}