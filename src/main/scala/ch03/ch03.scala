package ch03

object ch03 {
  def main(args: Array[String]): Unit = {
    val oneTwo = List(1, 2)
    val threeFour = List(3, 4)
    //::: 用于列表拼接
    val oneTwoThreeFour = oneTwo ::: threeFour
    println(oneTwo + " and " + threeFour + " were not mutated.")
    println("Thus, " + oneTwoThreeFour + " is a new list.")

    val twoThree = List(2, 3)
    //也许列表上用得最多的操作是“::”，读作“cons”。它在一个已有列表的最前面添加一个新的元素，并返回这个新的列表
    //在表达式“1 :: twoThree”中，::是它右操作元( right operand，即twoThree这个列表)的方法。
    // 你可能会觉得::方法的结合性(associativity)有些奇怪，实际上其背后的规则很简单：
    // 如果一个方法被用在操作符表示法(operator notation)当中时，比如a * b，方法调用默认都发生在左操作元(left operand)，
    // 除非方法名以冒号(:)结尾。如果方法名的最后一个字符是冒号，该方法的调用会发生在它的右操作元上。
    // 因此，在1 :: twoThree中，::方法调用发生在twoThree上，传入的参数是1，就像这样：twoThree.::(1)
    val oneTwoThree = 1 :: twoThree
    println(oneTwoThree)
    /*
     * 表示空列表的快捷方式是Nil，初始化一个新的列表的另一种方式是用::将元素串接起来，
     * 并将Nil作为最后一个元素(之所以需要在末尾放一个Nil，是因为::是List类上定义的方法。
     * 如果只是写成1::2::3，编译是不会通过的，因为3是个Int，而Int并没有::方法)
     */
    val oneTwoThree1 = 1 :: 2 :: 3 :: Nil
    println(oneTwoThree1)

    val pair = (99, "Luftballons")
    println(pair._1)
    println(pair._2)

    var jetSet = Set("Boeing", "Airbus")
    jetSet += "Lear"
    println(jetSet.contains("Cessna"))
    //如果你想要的是一个可变集，需要做一次 引入 ( import )
    import scala.collection.mutable
    val movieSet = mutable.Set("Hitch", "Poltergeist")
    movieSet += "Shrek"
    println(movieSet)
    //尽管由可变和不可变Set的工厂方法生产出来的默认集的实现对于大多数情况来说都够用了，偶尔可能也需要一类特定的集。
    // 幸运的是，语法上面并没有大的不同。只需要简单地引入你需要的类，然后使用其伴生对象上的工厂方法即可。
    // 例如，如果需要一个不可变的HashSet
    import scala.collection.immutable.HashSet
    val hashSet = HashSet("Tomatoes", "Chilies")
    println(hashSet + "Coriander")

    import scala.collection.mutable
    val treasureMap = mutable.Map[Int, String]()
    treasureMap += (1 -> "Go to island.")
    treasureMap += (2 -> "Find big X on ground.")
    treasureMap += (3 -> "Dig.")
    println(treasureMap(2))

    val romanNumeral = Map(
      1 -> "I", 2 -> "II", 3 -> "III", 4 -> "IV", 5 -> "V"
    )
    println(romanNumeral(4))

    val res = formatArgs(Array("zero", "one", "two"))
    assert(res == "zero\none\ntwo")

    import scala.io.Source
    if (args.length > 0) {
      for (line <- Source.fromFile(args(0)).getLines())
        println(line.length.toString + " " + line)
    }
    else
      Console.err.println("Please enter filename")
  }

  def printArgs(args: Array[String]): Unit = {
    var i = 0
    while (i > args.length) {
      println(args(i))
      i += 1
    }
  }

  //  def printArgs(args: Array[String]): Unit = {
  //    for (arg <- args)
  //    println(arg)
  //  }

  //  def printArgs(args: Array[String]): Unit = {
  //    args.foreach(println)
  //  }

  /**
   * 不过你可以走得更远。重构后的printArgs方法并不是“ 纯 ”的函数式代码，因为它有副作用(本例中它的副作用是向标准输出流打印)。
   * 带有副作用的函数的标志性特征是结果类型为Unit。如果一个函数并不返回任何有意义的值，
   * 也就是Unit这样的结果类型所表达的意思，那么这个函数存在于世上唯一的意义就是产生某种副作用。
   * 函数式编程的做法是定义一个将传入的args作为格式化(用于打印)的方法，但只是返回这个格式化的字符串
   * @param args
   * @return
   */
  def formatArgs(args: Array[String]) = args.mkString("\n")
}