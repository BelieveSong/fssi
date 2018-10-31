package fssi.scp.types
import fssi.utils.BytesUtil

case class NodeID(value: Array[Byte]) extends AnyVal {
  def ===(other: NodeID): Boolean = value sameElements other.value

  override def toString: String = BytesUtil.toBase64(value)
}
