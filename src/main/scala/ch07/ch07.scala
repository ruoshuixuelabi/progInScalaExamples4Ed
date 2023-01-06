package ch07

import scala.io.StdIn.readLine

object ch07 {
  def main(args: Array[String]): Unit = {
    //    var filename = "default.txt"
    //    if (!args.isEmpty)
    //      filename = args(0)
    val filename =
    if (!args.isEmpty) args(0)
    else "default.txt"
    println(if (!args.isEmpty) args(0) else "default.txt")
  }
  //用while循环计算最大公约数
  def gcdLoop(x: Long, y: Long): Long = {
    var a = x
    var b = y
    while (a != 0) {
      val temp = a
      a = b % a
      b = temp
    }
    b
  }
  //用do-while读取标准输入
  def gcdDoLoop(x: Long, y: Long): Long = {
    var line = ""
    do {
      line = readLine()
      println("Read: " + line)
    } while (line != "")
  }
}
