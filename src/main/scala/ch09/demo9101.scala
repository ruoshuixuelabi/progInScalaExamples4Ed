package ch09

object demo9101 {
  //本例中的op类型为Double => Double，意思是这是一个接收一个Double作为入参，返回另一个Double的函数。
  def twice(op: Double => Double, x: Double) = op(op(x))

  def main(args: Array[String]): Unit = {
    println(twice(_ + 1, 5))
  }
}