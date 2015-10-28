package com.openaf.table.lib.api

import collection.mutable

trait Combiner[C,V] {
  def combine(value:V):Unit
  def value:C
}

case object NullCombiner extends Combiner[Null,Null] {
  override def combine(value:Null) = {}
  override def value = null
}

class AnyCombiner extends Combiner[Set[Any],Any] {
  private val set = new mutable.HashSet[Any]
  override def combine(value:Any) = {set.add(value)}
  override def value = set.toSet
}

class StringCombiner extends Combiner[Set[String],String] {
  private val set = new mutable.HashSet[String]
  override def combine(value:String) = {set += value}
  override def value = set.toSet
}

class IntCombiner extends Combiner[Int,Int] {
  private var count = 0
  override def combine(value:Int) = {count += value}
  override def value = count
}

class IntegerCombiner extends Combiner[Int,Integer] {
  private var count = 0
  def combine(value:Integer) = {count += value.intValue}
  override def value = count
}

object IntegerCombiner {
  val One = new Integer(1)
}

sealed trait CombinerType {
  def nameId:String
}

object CombinerType {
  val Types = List(Sum, Average)
}

case object Sum extends CombinerType {
  override val nameId = "combiner.sum"
}

case object Average extends CombinerType {
  override val nameId = "combiner.average"
}

class AverageIntCombiner extends Combiner[Int,Int] {
  private var counter = 0
  private var runningSum = 0
  override def combine(value:Int) = {
    counter += 1
    runningSum += value
  }
  override def value = runningSum / counter
}

class AverageIntegerCombiner extends Combiner[Int,Integer] {
  private var counter = 0
  private var runningSum = 0
  def combine(value:Integer) = {
    counter += 1
    runningSum += value.intValue
  }
  override def value = runningSum / counter
}