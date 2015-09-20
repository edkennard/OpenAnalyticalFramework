package com.openaf.table.lib.api

case class TableState(tableLayout:TableLayout) {
  def allFields = tableLayout.allFields
  def distinctFieldIDs = tableLayout.distinctFieldIDs
  def rowHeaderFields = tableLayout.rowHeaderFields
  def withRowHeaderFields(newRowHeaderFields:List[Field[_]]) = {
    copy(tableLayout = tableLayout.withRowHeaderFields(newRowHeaderFields))
  }
  def columnHeaderLayout = tableLayout.columnHeaderLayout
  def withColumnHeaderLayout(newColumnHeaderLayout:ColumnHeaderLayout) = {
    copy(tableLayout = tableLayout.withColumnHeaderLayout(newColumnHeaderLayout))
  }
  def filterFields = tableLayout.filterFields
  def withFilterFields(newFilterFields:List[Field[_]]) = {
    copy(tableLayout = tableLayout.withFilterFields(newFilterFields))
  }
  def remove(fields:List[Field[_]]) = copy(tableLayout = tableLayout.remove(fields))
  def replaceField(oldField:Field[_], newField:Field[_]) = copy(tableLayout = tableLayout.replaceField(oldField, newField))
  def generateFieldKeys = copy(tableLayout = tableLayout.generateFieldKeys)
  def isColumnHeaderField(field:Field[_]) = tableLayout.isColumnHeaderField(field)
}
object TableState {
  val Blank = TableState(TableLayout.Blank)
}