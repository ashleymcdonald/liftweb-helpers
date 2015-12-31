package am.opensource.LiftwebHelpers.Collections

import collection._
import generic.CanBuildFrom

object TraversableOncePimps {
	import scala.language.implicitConversions
	import scala.language.higherKinds

	class TraversableOnceExt[CC, A](coll: CC, asTraversable: CC => TraversableOnce[A]) {

		def toMultiMap[T, U, That](implicit ev: A <:< (T, U), cbf: CanBuildFrom[CC, U, That]): immutable.Map[T, That] =
			toMultiMapBy(ev)

		def toMultiMapBy[T, U, That](f: A => (T, U))(implicit cbf: CanBuildFrom[CC, U, That]): immutable.Map[T, That] = {
			val mutMap = mutable.Map.empty[T, mutable.Builder[U, That]]
			for (x <- asTraversable(coll)) {
				val (key, value) = f(x)
				val builder = mutMap.getOrElseUpdate(key, cbf(coll))
				builder += value
			}
			val mapBuilder = immutable.Map.newBuilder[T, That]
			for ((k, v) <- mutMap)
				mapBuilder += ((k, v.result()))
			mapBuilder.result()
		}
	}

	implicit def commonExtendTraversable[A, C[A] <: TraversableOnce[A]](coll: C[A]): TraversableOnceExt[C[A], A] =
		new TraversableOnceExt[C[A], A](coll, identity)

}