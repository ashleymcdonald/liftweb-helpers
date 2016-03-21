package am.opensource.LiftwebHelpers.DateTime

import java.text.DateFormat
import java.util.Date

import net.liftweb.util.Helpers._
import org.ocpsoft.prettytime.PrettyTime

/**
  * Created by Ashley McDonald on 31/12/2015.
  */
object DateTimePimps {

	import scala.language.implicitConversions

	implicit def dateToAmPimpedDate(date: Date): AmPimpedDate = new AmPimpedDate(date)

	implicit def stringToAmStringDateParser(potentialDate: String): AmStringDateParser = new AmStringDateParser(potentialDate)

	class AmStringDateParser(potentialDate: String)
		extends ParseDateTime {
		val theString: String = potentialDate
	}

	trait ParseDateTime {
		val theString: String

		def parseDateTime: Date = tryo(DateFormat.getDateTimeInstance().parse(theString)).openOr(now)

		def parseDateTime(default: Date): Date = tryo(DateFormat.getDateTimeInstance().parse(theString)).openOr(default)
	}


	class AmPimpedDate(date: Date)
		extends FormatDateTime
		with FormatPrettyTime {
		val theDate: Date = date
	}

	trait FormatDateTime {
		val theDate: Date

		def formatDateTime = tryo(DateFormat.getDateTimeInstance().format(theDate)).openOr("")
	}

	trait FormatPrettyTime {
		val theDate: Date

		def prettyTime = tryo(new PrettyTime(new Date()).format(theDate).replace("from now", "")).openOr("")
	}

}
