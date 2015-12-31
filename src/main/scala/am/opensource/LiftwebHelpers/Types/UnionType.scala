package am.opensource.LiftwebHelpers.Types

/**
  * Created by Ash on 11/12/2015.
  */

trait UnionType {

	private[this] type ¬[A] = A => Nothing                  // Defines negation of types
	private[this] type ∨[T, U] = ¬[¬[T] with ¬[U]]          // De Morgan's law; this allows definition of union types
	private[this] type ¬¬[A] = ¬[¬[A]]                      // Auxiliary construct
	type |∨|[T, U] = { type λ[X] = ¬¬[X] <:< (T ∨ U) }      // Auxiliary construct

}
