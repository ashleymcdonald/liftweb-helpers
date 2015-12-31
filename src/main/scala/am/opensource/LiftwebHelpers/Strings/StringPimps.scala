package am.opensource.LiftwebHelpers.Strings

import java.text.NumberFormat

import net.liftweb.util.BasicTypesHelpers.{AsDouble, AsInt, AsLong}
import net.liftweb.util.StringHelpers._
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import scala.io.Source
import scala.xml.NodeSeq

/**
  * Created by silvs on 31/12/2015.
  */
object StringPimps {

	import scala.language.implicitConversions

	implicit def stringToAmPimpedString(s: String): AmPimpedString = new AmPimpedString(s)

	class AmPimpedString(s: String)
		extends CurrencyFormat
		with Truncate
		with StringToNodeSeq {
		val theString = blankForNull(s)
	}

	trait CurrencyFormat {
		val theString: String

		def asCurrencyString: String = theString match {
			case AsDouble(v) => NumberFormat.getCurrencyInstance.format(v)
			case AsLong(v) => NumberFormat.getCurrencyInstance.format(v)
			case AsInt(v) => NumberFormat.getCurrencyInstance.format(v)
			case _ => NumberFormat.getCurrencyInstance.format(0)
		}
	}

	trait StringToNodeSeq {
		val theString: String

		def toNodeSeq: NodeSeq = xml.parsing.XhtmlParser(Source.fromString(theString))

		def toJsoupedNodeSeq: NodeSeq = xml.parsing.XhtmlParser(Source.fromString(Jsoup.parse(theString).html))

		def toJsoup: Document = Jsoup.parse(theString)

		def toJsoupText: String = Jsoup.parse(theString).text()
	}

	trait Truncate {
		val theString: String

		def truncate(length: Int, elipsis: String = "…") =
			if (theString.length() > length)
				theString.substring(0, length) + elipsis
			else
				theString
	}

}
