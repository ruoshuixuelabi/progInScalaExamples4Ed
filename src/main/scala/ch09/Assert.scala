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

object Assert {
  var assertionsEnabled = true
  
  def myAssert(predicate: () => Boolean) =
    if (assertionsEnabled && !predicate())
      throw new AssertionError

  /**
   * 要让参数成为传名参数，需要给参数一个以=>开头的类型声明，而不是() =>。
   * 例如，可以像这样将myAssert的predicate参数转成传名参数：
   * 把类型“() => Boolean”改成“=> Boolean”。示例9.5给出了具体的样子
   * @param predicate
   */
  def byNameAssert(predicate: => Boolean) =
    if (assertionsEnabled && !predicate)
      throw new AssertionError

  def main(args: Array[String]): Unit = {
    try {
      myAssert(() => 5 > 3)
      println("5 > 3")
      myAssert(() => 5 < 3)
    } catch {
      case ex: AssertionError => println("ex [" + ex + "]")
    }

    try {
      byNameAssert(5 > 3)
      println("5 > 3")
      byNameAssert(5 < 3)
    } catch {
      case ex: AssertionError => println("ex [" + ex + "]")
    }
  }
}
