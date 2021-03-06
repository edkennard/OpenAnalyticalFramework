package com.openaf.table.gui

import javafx.scene.layout.{Priority, HBox}
import javafx.geometry.Side
import scala.collection.JavaConversions._
import com.openaf.table.lib.api._
import com.openaf.table.lib.api.ColumnHeaderLayout.ColumnHeaderTreeType
import collection.immutable.Seq

class ColumnHeaderLayoutNode(columnHeaderLayout:ColumnHeaderLayout, dragAndDropContainer:DragAndDropContainer,
                             tableFields:OpenAFTableFields) extends HBox {
  getStyleClass.add("column-header-layout-node")
  private val columnHeaderTreeNodes = columnHeaderLayout.columnHeaderTrees.map(columnHeaderTree => {
    val columnHeaderTreeNode = new ColumnHeaderTreeNode(columnHeaderTree, dragAndDropContainer, tableFields)
    HBox.setHgrow(columnHeaderTreeNode, Priority.ALWAYS)
    columnHeaderTreeNode
  })
  getChildren.addAll(columnHeaderTreeNodes :_*)

  def childColumnAreaTreeNodes = getChildren.collect{case (columnHeaderTreeNode:ColumnHeaderTreeNode) => columnHeaderTreeNode}

  def allFieldNodes:Seq[FieldNode[_]] = columnHeaderTreeNodes.flatMap(_.topFieldNodes)

  def generateColumnHeaderLayoutWithAddition(nodeSide:NodeSide, draggableFieldsInfo:DraggableFieldsInfo) = {
    val columnHeaderTrees = columnHeaderTreeNodes.flatMap(_.generateWithAdditionOption(nodeSide, draggableFieldsInfo))
    if (nodeSide.node == this) {
      nodeSide.side match {
        case Side.TOP => {
          val columnHeaderTreeType:ColumnHeaderTreeType = draggableFieldsInfo.fields match {
            case field :: Nil => Left(field)
            case manyFields => Right(ColumnHeaderLayout.fromFields(manyFields))
          }
          val childColumnHeaderLayout = ColumnHeaderLayout(columnHeaderTrees)
          val newColumnAreaTree = ColumnHeaderTree(columnHeaderTreeType, childColumnHeaderLayout)
          ColumnHeaderLayout(newColumnAreaTree)
        }
        case Side.BOTTOM => {
          val newColumnAreaTreeType = Right(ColumnHeaderLayout(columnHeaderTrees))
          val newChildColumnHeaderLayout = ColumnHeaderLayout.fromFields(draggableFieldsInfo.fields)
          val newColumnAreaTree = ColumnHeaderTree(newColumnAreaTreeType, newChildColumnHeaderLayout)
          ColumnHeaderLayout(newColumnAreaTree)
        }
        case unexpected => throw new IllegalStateException(s"A ColumnHeaderLayoutNode should never have this side $unexpected")
      }
    } else {
      ColumnHeaderLayout(columnHeaderTrees)
    }
  }
}
