package com.openaf.browser

import javafx.scene.layout.BorderPane
import javafx.beans.binding.BooleanBinding
import javafx.beans.property.{SimpleBooleanProperty, SimpleIntegerProperty}
import javafx.collections.FXCollections

class Browser(initialPage:Page, tabPane:BrowserTabPane, stage:BrowserStage, manager:BrowserStageManager) extends BorderPane {
  private val currentPagePosition = new SimpleIntegerProperty(-1)
  private val pages = FXCollections.observableArrayList[Page]()
  private val working = new SimpleBooleanProperty(true)
  val backAndUndoDisabledProperty = new BooleanBinding {
    bind(currentPagePosition, working)
    def computeValue = (currentPagePosition.get <= 0) || working.get
  }
  val redoAndForwardDisabledProperty = new BooleanBinding {
    bind(currentPagePosition, pages, working)
    def computeValue = (currentPagePosition.get >= (pages.size - 1)) || working.get
  }
  private val refreshable = new SimpleBooleanProperty(false)
  val refreshDisabledProperty = new BooleanBinding {
    bind(refreshable, working)
    def computeValue = !refreshable.get || working.get
  }
  val homeDisabledProperty = new BooleanBinding {
    bind(working)
    def computeValue = working.get
  }

  private val browserBar = new BrowserBar(this, tabPane, stage)
  setTop(browserBar)

  def back() {
    if (!backAndUndoDisabledProperty.get) {
      println("back")
    }
  }

  def undo() {
    if (!backAndUndoDisabledProperty.get) {
      println("undo")
    }
  }

  def redo() {
    if (!redoAndForwardDisabledProperty.get) {
      println("redo")
    }
  }

  def forward() {
    if (!redoAndForwardDisabledProperty.get) {
      println("forward")
    }
  }

  def refresh() {
    if (!refreshDisabledProperty.get) {
      println("refresh")
    }
  }

  def home() {
    if (!homeDisabledProperty.get) {
      println("home")
    }
  }
}
