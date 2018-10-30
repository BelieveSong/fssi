package fssi.ast

import bigknife.sop._
import bigknife.sop.macros._
import bigknife.sop.implicits._

import fssi.types.biz._

@sp trait Consensus[F[_]] {
  def initialize(node: Node):P[F, Unit]
  def destroy(): P[F, Unit]
  def tryAgree(transaction: Transaction, receipt: Receipt): P[F, Unit]
}