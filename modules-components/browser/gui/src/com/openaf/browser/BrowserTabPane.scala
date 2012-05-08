package com.openaf.browser

import javafx.event.EventHandler
import javafx.scene.control.{Tab, TabPane}
import javafx.scene.input.{KeyCode, KeyEvent}

class BrowserTabPane(initialPage:Page, stage:BrowserStage, manager:BrowserStageManager) extends TabPane {
  setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE)
  createTab(initialPage)

  def createTab(page:Page, goToNewTab:Boolean=true) {
    val browser = new Browser(page, this, stage, manager)
    val tab = new BrowserTab(page.name, page.image, this)
    tab.setContent(browser)
    getTabs.add(getTabs.size(), tab)
    if (goToNewTab) {
      getSelectionModel.select(tab)
    }
  }

  def closeTab(tab:Tab) {
    if (getTabs.size > 1) {
      getTabs.remove(tab)
      ensureTabSelected()
    } else {
      manager.closeBrowserStage(stage)
    }
  }

  def closeOtherTabs(tabToKeepOpen:Tab) {
    getTabs.retainAll(tabToKeepOpen)
    ensureTabSelected()
  }

  def closeTabsToTheRight(tab:Tab) {
    val indexOfTab = getTabs.indexOf(tab)
    getTabs.subList(indexOfTab + 1, getTabs.size).clear()
    ensureTabSelected()
  }

  private def ensureTabSelected() {
    if (getSelectionModel.getSelectedIndex >= getTabs.size) {
      getSelectionModel.select(getTabs.size - 1)
    }
  }

  setOnKeyPressed(new EventHandler[KeyEvent] {
      def handle(e:KeyEvent) {
        if ((e.getCode == KeyCode.W) && e.isShortcutDown) {
          closeTab(getSelectionModel.getSelectedItem)
        }
      }
  })
}
