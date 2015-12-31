package am.opensource.LiftwebHelpers.Streams

/**
  * Created by silvs on 31/12/2015.
  */
object InputStreamPimps {
	implicit def InputStream2ByteArray(value : java.io.InputStream):Array[Byte] = org.apache.commons.io.IOUtils.toByteArray(value)

}
