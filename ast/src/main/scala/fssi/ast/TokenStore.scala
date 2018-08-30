package fssi
package ast

import types._
import bigknife.sop._
import bigknife.sop.macros._
import bigknife.sop.implicits._

import java.io._

@sp trait TokenStore[F[_]] {

  /** initialize a data directory to be a token store
    * @param dataDir directory to save token.
    */
  def initializeTokenStore(dataDir: File): P[F, Unit]

  /** self test for a token store
    * @param block token store should be tested on block
    * @return if the store is sane return true, or false
    */
  def testTokenStore(block: Block): P[F, Boolean]

  /** get current token store state
    * this state should identify current state of token store
    */
  def getTokenStoreState(): P[F, HexString]

  /** verify current state of token store
    */
  def verifyTokenStoreState(state: String): P[F, Boolean]

  /** get token of an account
    */
  def getCurrentToken(account: Account.ID): P[F, Token]

  /** stage the account's token
    */
  def stageToken(height: BigInt, account: Account.ID, token: Token): P[F, Unit]
}