package am.opensource.LiftwebHelpers.Collections

import net.liftweb.common.Box

/**
  * Created by silvs on 13/01/2016.
  */
object ListPimps {

	//TODO Utilise this
	def unboxList[B <: Any]: List[Box[B]] ⇒ List[B] = lba ⇒ lba filter (_.isDefined) map (_.openOrThrowException("This should not happen as we filter on defined!"))

}
