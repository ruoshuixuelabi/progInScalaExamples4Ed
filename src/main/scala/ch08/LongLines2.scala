package ch08

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

import scala.io.Source

object LongLines2 {
  object LongLines {
    /**
     * 既然现在processLine定义在processFile内部，我们还可以做另一项改进。
     * 注意到filename和width被直接透传给助手函数，完全没有变吗？
     * 这里的传递不是必需的，因为局部函数可以访问包含它们的函数的参数。可以直接使用外部的processFile函数的参数
     */
    def processFile(filename: String, width: Int) = {
      def processLine(filename: String, width: Int, line: String) = {
        if (line.length > width)
          println(filename + ": " + line.trim)
      }

      val source = Source.fromFile(filename)
      for (line <- source.getLines()) {
        processLine(filename, width, line)
      }
    }
  }

  object FindLongLines {
    def main(args: Array[String]) = {
      val width = args(0).toInt
      for (arg <- args.drop(1))
        LongLines.processFile(arg, width)
    }
  }

  def main(args: Array[String]) = FindLongLines.main(args)
}