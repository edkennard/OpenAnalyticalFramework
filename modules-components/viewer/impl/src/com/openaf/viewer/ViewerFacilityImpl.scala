package com.openaf.viewer

import api.ViewerFacility

class ViewerFacilityImpl extends ViewerFacility {
  var counter = 0
  def text = {
    Thread.sleep(1000)
    counter += 1
    "This is text from ViewerFacility " + counter
  }
}