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
    //在编译这段代码时，Scala编译器会给出一个警告：用!=对类型为Unit的值和String做比较将永远返回true。
    // 在Java中，赋值语句的结果是被赋上的值(在本例中就是从标准输入读取的一行文本)，而在Scala中赋值语句的结果永远是单元值()。
    // 因此，赋值语句“line = readLine()”将永远返回()，而不是""。这样一来，while循环的条件检查永远都不会为false，循环将无法终止
    //    var line = ""
    //    while ((line = readLine()) != "") // This doesn't work!
    //      println("Read: " + line)

    //用for能做的最简单的事，是遍历某个集合的所有元素。例如，示例7.5展示了一组打印出当前目录所有文件的代码。
    // I/O操作用到了Java API。首先，我们对当前目录(".")创建一个java.io.File对象，然后调用它的listFiles方法。
    // 这个方法返回一个包含File对象的数组，这些对象分别对应当前目录中的每个子目录或文件。我们将结果数组保存在filesHere变量中。
    val filesHere = new java.io.File(".").listFiles
    for (file <- filesHere)
      println(file)

    //for表达式的语法可以用于任何种类的集合，而不仅仅是数组(准确地说，
    // 在for表达式的<-符号右侧的表达式可以是任何拥有某些特定的带有正确签名的方法的类型。
    // 第23章将会详细介绍Scala编译器对for表达式的处理机制)。Range(区间)是一类特殊的用例，
    // 在表5.4中(91页)简略地提到过。可以用“1 to 5”这样的语法来创建Range，并用for来遍历它们。以下是一个简单的例子：
    for (i <- 1 to 4)
      println("Iteration " + i)

    //如果你不想在被遍历的值中包含区间的上界，可以用until而不是to：
    for (i <- 1 until 4)
      println("Iteration " + i)

    //有时你并不想完整地遍历集合，你想把它过滤成一个子集。这时可以给for表达式添加 过滤器(filter)，
    // 过滤器是for表达式的圆括号中的一个if子句。举例来说，示例7.6的代码仅列出当前目录中以“.scala”结尾的那些文件：
    val filesHere2 = new java.io.File(".").listFiles
    for (file <- filesHere2 if file.getName.endsWith(".scala"))
      println(file)
    //也可以用如下代码达到同样的目的：这段代码跟前一段产生的输出没有区别，可能有指令式编程背景的程序员看上去更为熟悉。
    // 这种指令式的代码风格只是一种选项(不是默认和推荐的做法)，因为这个特定的for表达式被用作打印的副作用，其结果是单元值()。
    // 稍后你将看到，for表达式之所以被称作“表达式”，是因为它能返回有意义的值，一个类型可以由for表达式的<-子句决定的集合。
    for (file <- filesHere2)
      if (file.getName.endsWith(".scala"))
        println(file)
    //若想随意包含更多的过滤器，直接添加if子句即可。例如，为了让我们的代码具备额外的防御性，
    // 示例7.7的代码只输出文件名，不输出目录名。实现方式是添加一个检查file的isFile方法的过滤器。
    for (
      file <- filesHere2
      if file.isFile
      if file.getName.endsWith(".scala")
    ) println(file)

    //如果你添加多个<-子句，将得到嵌套的“循环”。例如，示例7.8中的for表达式有两个嵌套迭代。
    // 外部循环遍历filesHere，内部循环遍历每个以.scala结尾的file的fileLines(file)。
    def fileLines(file: java.io.File) =
      scala.io.Source.fromFile(file).getLines().toArray

    def grep(pattern: String) =
      for (
        file <- filesHere
        if file.getName.endsWith(".scala");
        line <- fileLines(file)
        if line.trim.matches(pattern)
      ) println(s"$file:${line.trim}")

    grep(".*gcd.*")

    //你大概注意到前一例中line.trim重复了两遍。这并不是一个很无谓的计算,因此你可能想最好只算一次。
    // 可以用=来将表达式的结果绑定到新的变量上。被绑定的这个变量引入和使用起来都跟val一样,只不过去掉了val关键字。示例7.9给出了一个例子。
    def grep1(pattern: String) =
      for {
        file <- filesHere
        if file.getName.endsWith(".scala")
        line <- fileLines(file)
        trimmed = line.trim
        if trimmed.matches(pattern)
      } println(s"$file:$trimmed")

    grep1(".*gcd.*")

    //虽然到目前为止所有示例都是对遍历到的值进行操作然后忘掉它们，也完全可以在每次迭代中生成一个可以被记住的值。
    // 具体做法是在for表达式的代码体之前加上关键字yield。例如，如下函数识别出.scala文件并将它们保存在数组中：
    def scalaFiles =
      for {
        file <- filesHere
        if file.getName.endsWith(".scala")
      } yield file

    //for表达式的代码体每次被执行，都会产出一个值，本例中就是file。
    // 当for表达式执行完毕后，其结果将包含所有交出的值，包含在一个集合当中。
    // 结果集合的类型基于迭代子句中处理的集合种类。在本例中，结果是Array[File]，因为filesHere是个数组，而交出的表达式类型为File。
    //yield关键字必须出现在整个代码体之前。哪怕代码体是由花括号包起来的，
    // 也要将yield放在花括号之前，而不是在代码块最后一个表达式前面。避免像这样使用yield：
    //    for (file <- filesHere if file.getName.endsWith(".scala")) {
    //      yield file // Syntax error!
    //    }
    //举例来说，示例7.10里的for表达式首先将包含当前目录所有文件的名为filesHere的Array[File]转换成一个只包含.scala文件的数组。
    // 对每一个文件，再用fileLines方法(参见示例7.8)的结果生成一个Array[String]。
    // 这个Array[string]里的每个元素都各自包含了当前被处理文件中的一行。
    // 这个初始的Array[String]又被转换成另一个Array[String]，这一次只包含那些包含子串"for"的被去边的字符串。
    // 最后，对这些字符串再交出其长度的整数。这个for表达式的结果是包含这些长度整数的Array[Int]。
    val forLineLengths =
    for {
      file <- filesHere
      if file.getName.endsWith(".scala")
      line <- fileLines(file)
      trimmed = line.trim
      if trimmed.matches(".*for.*")
    } yield trimmed.length
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
  //  def gcdDoLoop(x: Long, y: Long): Long = {
  //    var line = ""
  //    do {
  //      line = readLine()
  //      println("Read: " + line)
  //    } while (line != "")
  //  }

  /**
   * 例如，示例7.4给出了一个计算两个数的最大公约数的另一种实现方式。
   * (示例7.4中的gcd函数使用了跟示例6.3中类似命名的，用来帮Rational计算最大公约数的函数相同的算法。
   * 主要的区别在于示例7.4的gcd针对的是Long类型的参数而不是Int)给x和y同样的两个值，
   * 示例7.4的gcd函数将返回跟示例7.2中的gcdLoop函数相同的结果。这两种方案的区别在于gcdLoop是指令式风格的，
   * 用到了var和while循环，而gcd是更加函数式风格的，用到了递归(gcd调用了自己)，并且不需要var。
   *
   * @param x
   * @param y
   * @return
   */
  def gcd(x: Long, y: Long): Long =
    if (y == 0) x else gcd(y, x % y)
}
