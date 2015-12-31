package am.opensource.LiftwebHelpers.DateTime

/**
  * Created by silvs on 31/12/2015.
  */
object TimespanEnum extends Enumeration {
	type TimespanEnum = Value
	val Infinite = Value(0, "Infinite")
	val Second = Value(1, "Second")
	val Minute = Value(2, "Minute")
	val Hour = Value(3, "Hour")
	val Day = Value(4, "Day")
	val Month = Value(5, "Month")
	val Year = Value(6, "Year")
}