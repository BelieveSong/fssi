package fssi.types
package biz

import scala.collection._
import base._
import fssi.types.implicits._

/** block contains a head and some transactions
  */
case class Block(
    head: Block.Head,
    transactions: immutable.TreeSet[Transaction],
    hash: Hash
)

object Block {
  def emptyWordStates: WorldStates = WorldStates(
    WorldState.empty,
    WorldState.empty,
    WorldState.empty,
    WorldState.empty,
    WorldState.empty
  )

  /** a total world state
    * include:
    * 1. block store state
    * 2. token store state
    * 3. contract store state
    * 4. receipt of transaction state
    */
  case class WorldStates(
      blockState: WorldState,
      tokenState: WorldState,
      contractState: WorldState,
      dataState: WorldState,
      receiptState: WorldState
  )

  /** block head contains state of the preivous block and current block
    * and additional: current block height and chainID
    */
  case class Head(
      previousStates: WorldStates,
      currentStates: WorldStates,
      height: BigInt,
      chainId: String
  )

  trait Implicits {
    implicit def wordStatesToBytesValue(a: WorldStates): Array[Byte] = {
      import a._
      (blockState.asBytesValue ++ tokenState.asBytesValue ++
        contractState.asBytesValue ++ dataState.asBytesValue ++
        receiptState.asBytesValue).bytes
    }

    implicit def blockHeadToBytesValue(a: Head): Array[Byte] = {
      import a._
      (previousStates.asBytesValue.any ++ currentStates.asBytesValue.any ++
        height.asBytesValue.any ++ chainId.asBytesValue.any).bytes
    }

    implicit def blockToBytesValue(a: Block): Array[Byte] = {
      import a._
      (head.asBytesValue.any ++ transactions
        .foldLeft(BytesValue.empty[Transaction])((acc, n) => acc ++ n.asBytesValue)
        .any ++ hash.asBytesValue.any).bytes
    }
  }

}