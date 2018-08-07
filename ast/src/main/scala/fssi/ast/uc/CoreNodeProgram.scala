package fssi
package ast
package uc

import types._
import types.syntax._
import bigknife.sop._
import bigknife.sop.implicits._

trait CoreNodeProgram[F[_]] {
  val model: components.Model[F]
  import model._

  /** Start up a core node.
    * @return node info
    */
  def startup(handler: JsonMessageHandler): SP[F, Node] = for {
    n1 <- network.startupP2PNode(handler)
    n2 <- network.bindAccount(n1)
    //TODO: some other components should be initialized here
  } yield n2

  /** Shutdown core node
    */
  def shutdown(node: Node): SP[F, Unit] = for {
    _ <- network.shutdownP2PNode(node)
  } yield()
}
object CoreNodeProgram {
  def apply[F[_]](implicit M: components.Model[F]): CoreNodeProgram[F] = new CoreNodeProgram[F] {
    val model: components.Model[F] = M
  }
}