import java.math.BigInteger

object demo2 {
  def main(args: Array[String]): Unit = {
    println(factorial(new BigInteger("30")))
  }

  def factorial(x: BigInteger): BigInteger =
    if (x == BigInteger.ZERO)
      BigInteger.ONE
    else
      x.multiply(factorial(x.subtract(BigInteger.ONE)))
}