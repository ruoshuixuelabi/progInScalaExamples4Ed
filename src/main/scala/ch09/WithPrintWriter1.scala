package ch09

/*
 * Copyright (C) 2007-2019 Artima, Inc. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Example code from:
 *
 * Programming in Scala, Fourth Edition
 * by Martin Odersky, Lex Spoon, Bill Venners
 *
 * http://booksites.artima.com/programming_in_scala_4ed
 */

import java.io._

object WithPrintWriter1 {
  /**
   * 每当你发现某个控制模式在代码中多处出现时，就应该考虑将这个模式实现为新的控制结构。
   * 在本章前面的部分看到了filesMatching这个非常特殊的控制模式，现在来看一个更加常用的编码模式：
   * 打开某个资源，对它进行操作，然后关闭这个资源。可以用类似如下的方法，将这个模式捕获成一个控制抽象：
   *
   * @param file
   * @param op
   */
  def withPrintWriter(file: File, op: PrintWriter => Unit) = {
    val writer = new PrintWriter(file)
    try {
      op(writer)
    } finally {
      writer.close()
    }
  }

  /**
   * 使用这个方法的好处是，确保文件在最后被关闭的是withPrintWriter而不是用户代码。
   * 因此不可能出现使用者忘记关闭文件的情况。这个技巧被称作 贷出模式(loan pattern)，
   * 因为是某个控制抽象函数，比如withPrintWriter，打开某个资源并将这个资源“贷出”给函数。
   * 例如，前一例中的withPrintWriter将一个PrintWriter“贷出”给函数op。
   * 当函数完成时，它会表明自己不再需要这个“贷入”的资源。
   * 这时这个资源就在finally代码块中被关闭了，这样能确保不论函数是正常返回还是抛出异常，资源都会被正常关闭。
   * @param args
   */
  def main(args: Array[String]): Unit = {
    withPrintWriter(
      new File("date.txt"),
      writer => writer.println(new java.util.Date)
    )
  }
}