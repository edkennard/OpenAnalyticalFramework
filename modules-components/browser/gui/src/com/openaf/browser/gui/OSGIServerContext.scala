package com.openaf.browser.gui

import org.osgi.framework.BundleContext
import org.osgi.util.tracker.ServiceTracker

class OSGIServerContext(context:BundleContext) extends ServerContext {
  def facility[T](klass:Class[T]) = {
    val serviceTracker = new ServiceTracker(context, klass, null)
    serviceTracker.open()
    val services = serviceTracker.getServices
    services(0).asInstanceOf[T]
  }
}