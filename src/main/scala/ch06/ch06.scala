package ch06

object ch04 {
  def main(args: Array[String]): Unit = {
    val oneHalf = new Rational(1, 2)
    println(oneHalf)
    val twoThirds = new Rational(2, 3)
    println(oneHalf add twoThirds)
    println(new Rational(66, 42))
    //    println((oneHalf / 7) + (1 - twoThirds))
    //    new Rational(5, 0)//Exception in thread "main" java.lang.IllegalArgumentException: requirement failed

    val x = new Rational(1, 2)
    val y = new Rational(2, 3)
    println(x + y)
    //另一个值得注意的点是，按照Scala的操作符优先级(在5.9节介绍过)，对于Rational来说，`*`方法会比+方法绑得更紧。
    // 换句话说，涉及Rational的+和*操作，其行为会按照我们预期的那样。比如，x + x * y 会被当作x + (x * y)执行，而不是(x + x) * y
    println(x + x * y)
    println((x + x) * y)
    println(x + (x * y))
    //可以创建一个隐式转换(implicit conversion)，在需要时自动将整数转换成有理数
    //为了让隐式转换能工作，它需要在作用域内。如果你将隐式方法的定义放在Rational类内部，对解释器而言它是没有在作用域的
    implicit def intToRational(x: Int) = new Rational(x)
    val r = new Rational(2,3)
    println(2 * r)
  }
}

/**
 * 由于已经决定Rational对象是不可变的,将要求使用者在构造Rational实例的时候就提供所有需要的数据(也就是分子和分母)
 * <p>
 * 首先要注意的一点是如果一个类没有定义体，并不需要给出空的花括号(只要你想，当然也可以)。
 * 类名Rational后的圆括号中的标识符n和d称作类参数(class parameter)。
 * Scala编译器将会采集到这两个类参数，并且创建一个主构造方法(primary constructor)，接收同样的这两个参数
 * <p>
 * 这个Rational示例突出显示了Java和Scala的一个区别。在Java中，类有构造方法，构造方法可以接收参数；
 * 而在Scala中，类可以直接接收参数，Scala的表示法更为精简(类定义体内可以直接使用类参数，
 * 不需要定义字段并编写将构造方法参数赋值给字段的代码)。这可以大幅节省样板代码，尤其对于小型的类而言。
 * <p>
 *
 * @param n 分母
 * @param d 分子
 */
class Rational(n: Int, d: Int) {
  require(d != 0)

  //  override def toString = s"$n/$d"

  private val g = gcd(n.abs, d.abs)
  //  val numer: Int = n
  //  val denom: Int = d
  val numer = n / g
  val denom = d / g

  /**
   * Scala的辅助构造方法以def this(...)开始。
   * Rational的辅助构造方法的方法体只是调用一下主构造方法,透传它唯一的参数n作为分子,1作为分母
   * <p>
   * 在Scala中，每个辅助构造方法都必须首先调用同一个类的另一个构造方法。
   * 换句话说，Scala每个辅助构造方法的第一条语句都必须是这样的形式：“this(...)”。
   * 被调用的这个构造方法要么是主构造方法(就像Rational示例那样)，
   * 要么是另一个出现在发起调用的构造方法之前的另一个辅助构造方法。
   * 这个规则的净效应是Scala的每个构造方法最终都会调用到该类的主构造方法。
   * 这样一来，主构造方法就是类的单一入口
   * <p>
   * 如果你熟悉Java，可能会好奇为什么Scala的构造方法规则比Java更严格。
   * 在Java中，构造方法要么调用同一个类的另一个构造方法，要么直接调用超类的构造方法。
   * 而在Scala类中，只有主构造方法可以调用超类的构造方法。Scala这个增强的限制实际上是一个设计的取舍，
   * 用来换取更精简的代码和跟Java相比更为简单的构造方法。我们将会在第10章详细介绍超类，以及构造方法和继承的相互作用
   * <p>
   */
  def this(n: Int) = this(n, 1) // auxiliary constructor

  override def toString = s"$numer/$denom"

  //  println("Created " + n + "/" + d)

  /**
   * 现在主构造器已经正确地保证了它的前置条件，我们将注意力转向如何支持加法。
   * 我们将给Rational类定义一个add方法，接收另一个Rational作为参数。
   * 为了保持Rational不可变，这个add方法不能将传入的有理数加到自己身上，
   * 它必须创建并返回一个新的持有这两个有理数的和的Rational对象。你可能会认为这样写add是OK的
   * <p>
   * 虽然类参数n和d在你的add方法中是在作用域内的，但只能访问执行add调用的那个对象上的n和d的值。
   * 因此，当你在add实现中用到n或d时，编译器会提供这些类参数对应的值，但它并不允许使用that.n或that.d，
   * 因为that并非指向你执行add调用的那个对象(实际上，可以把Rational跟自己相加，这时that会指向执行add调用的那个对象。
   * 但由于你可以传入任何Rational对象到add方法，编译器仍然不允许你用that.n)。要访问that的分子和分母，需要将它们做成字段。
   * 示例6.1展示了如何将这些字段添加到Rational类(在10.6节你将找到更多关于参数化字段(parametric field)的内容，提供了同样代码的简写方式)。
   *
   * @param that
   * @return
   */
  //  def add(that: Rational): Rational =
  //    new Rational(n * that.d + that.n * d, d * that.d)
  def add(that: Rational): Rational =
    new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom
    )

  private def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)

  /**
   * 第一步是将add替换成通常的那个数学符号。这个做起来很直截了当，因为在Scala中+是一个合法的标识符。
   * 可以简单地定义一个名为+的方法。在这么做的同时，完全可以顺手实现一个*方法，来执行乘法操作
   *
   * @param that
   * @return
   */
  def +(that: Rational): Rational =
    new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom
    )

  def +(i: Int): Rational =
    new Rational(numer + i * denom, denom)

  def -(that: Rational): Rational =
    new Rational(
      numer * that.denom - that.numer * denom,
      denom * that.denom
    )

  def -(i: Int): Rational =
    new Rational(numer - i * denom, denom)

  def *(that: Rational): Rational =
    new Rational(numer * that.numer, denom * that.denom)

  def *(i: Int): Rational =
    new Rational(numer * i, denom)

  def /(that: Rational): Rational =
    new Rational(numer * that.denom, denom * that.numer)

  def /(i: Int): Rational =
    new Rational(numer, denom * i)
}