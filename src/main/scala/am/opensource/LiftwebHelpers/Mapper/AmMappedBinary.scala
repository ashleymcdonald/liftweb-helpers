package am.opensource.LiftwebHelpers.Mapper



import java.lang.reflect.Method
import java.sql.Types
import java.util.Date

import net.liftweb.common._
import net.liftweb.http.js._
import net.liftweb.json._
import net.liftweb.mapper._
import net.liftweb.util.Helpers._
import net.liftweb.util._


abstract class AmMappedBinary[T<:Mapper[T]](val fieldOwner: T) extends MappedField[Array[Byte], T] with Loggable {
	private val data : FatLazy[Array[Byte]] =  FatLazy(defaultValue)
	private val orgData: FatLazy[Array[Byte]] = FatLazy(defaultValue)

	protected def real_i_set_!(value : Array[Byte]) : Array[Byte] = {
		data() = value
		this.dirty_?( true)
		value
	}

	def dbFieldClass = classOf[Array[Byte]]

	/**
	  * Get the JDBC SQL Type for this field
	  */
	//  def getTargetSQLType(field : String) = Types.BINARY
	def targetSQLType = Types.BINARY

	def defaultValue: Array[Byte] = null
	override def writePermission_? = true
	override def readPermission_? = true

	protected def i_is_! = data.get

	protected def i_was_! = orgData.get

	//	protected[mapper] def doneWithSave() {orgData.setFrom(data)}
	def doneWithSave() {orgData.setFrom(data)}

	protected def i_obscure_!(in : Array[Byte]) : Array[Byte] = {
		new Array[Byte](0)
	}

	override def renderJs_? = false

	def asJsExp: JsExp = throw new NullPointerException("No way")

	def asJsonValue: Box[JsonAST.JValue] = Full(get match {
		case null => JsonAST.JNull
		case value => JsonAST.JString(base64Encode(value))
	})

	override def setFromAny(f: Any): Array[Byte] = f match {
		case null | JsonAST.JNull => this.set(null)
		case JsonAST.JString(base64) => this.set(base64Decode(base64))
		case array: Array[Byte] => this.set(array)
		case s => this.set(s.toString.getBytes("UTF-8"))
	}

	def jdbcFriendly(field : String) : Object = get

	def real_convertToJDBCFriendly(value: Array[Byte]): Object = value

	def buildSetActualValue(accessor: Method, inst: AnyRef, columnName: String): (T, AnyRef) => Unit =
		(inst, v) => doField(inst, accessor, {case f: AmMappedBinary[T] =>
			val toSet = v match {
				case null => null
				case ba: Array[Byte] => ba
				// Converts InputStream into Byte Array
				// TODO: Work out how not to need this
				case bo: java.io.InputStream => org.apache.commons.io.IOUtils.toByteArray(bo)
				case other => { type v = other.type; logger.warn(v) ; other.toString.getBytes("UTF-8") }
			}
			f.data() = toSet
			f.orgData() = toSet
		})

	def buildSetLongValue(accessor : Method, columnName : String): (T, Long, Boolean) => Unit = null
	def buildSetStringValue(accessor : Method, columnName : String): (T, String) => Unit  = null
	def buildSetDateValue(accessor : Method, columnName : String): (T, Date) => Unit = null
	def buildSetBooleanValue(accessor : Method, columnName : String): (T, Boolean, Boolean) => Unit = null

	/**
	  * Given the driver type, return the string required to create the column in the database
	  */
	def fieldCreatorString(dbType: DriverType, colName: String): String = colName + " " + dbType.binaryColumnType + notNullAppender()
}