package com.openaf.table.lib.api

import java.time.{LocalDate, Duration}

case object AnyOrdering extends Ordering[Any] {
  override def compare(anyX:Any,anyY:Any) = {
    (anyX,anyY) match {
      case (x:Int,y:Int) => Integer.compare(x,y)
      case (x:String,y:String) => x.compareTo(y)
      case _ => 0
    }
  }
}

case object NullOrdering extends Ordering[Null] {
  override def compare(anyX:Null,anyY:Null) = 0
}

case object StringOrdering extends Ordering[String] {
  override def compare(stringX:String,stringY:String) = stringX.compareTo(stringY)
}

case object IntOrdering extends Ordering[Int] {
  override def compare(intX:Int,intY:Int) = Integer.compare(intX, intY)
}

case object IntegerOrdering extends Ordering[Integer] {
  override def compare(intX:Integer,intY:Integer) = Integer.compare(intX.intValue, intY.intValue)
}

case object DurationOrdering extends Ordering[Duration] {
  override def compare(durationX:Duration, durationY:Duration) = durationX.compareTo(durationY)
}

case object LocalDateOrdering extends Ordering[LocalDate] {
  override def compare(localDateX:LocalDate, localDateY:LocalDate) = localDateX.compareTo(localDateY)
}