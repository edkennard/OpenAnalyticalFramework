package com.openaf.browser.gui.pages

import com.openaf.browser.gui.api.PageFactory
import com.openaf.pagemanager.api._

trait BrowserPage extends Page {
  def pageDataFacility(serverContext:ServerContext) = BrowserPageDataFacility
}

object BrowserPageDataFacility extends PageDataFacility {
  def pageData(page:Page) = NoPageData
}

object BlankPage extends BrowserPage

object BlankPageFactory extends PageFactory {
  def page = BlankPage
}