package am.opensource.LiftwebHelpers.Numbers

import java.text.NumberFormat

/**
  * Created by silvs on 31/12/2015.
  */
object NumberPimps {

	import scala.language.implicitConversions

	implicit def longToAmPimpedLong(num: Long): AmPimpedLong = new AmPimpedLong(num)

	class AmPimpedLong(num: Long)
		extends LongCurrencyFormat {
		val theNumber = num
	}

	trait LongCurrencyFormat {
		val theNumber: Long

		def asCurrencyString: String = NumberFormat.getCurrencyInstance.format(theNumber)
	}

}
