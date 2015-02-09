package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + "  ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if(r==0 || r==1 || c ==0 || r==c)
      return 1
    pascal(c-1, r-1) + pascal(c, r-1)

  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def balanceInner(chars: List[Char], opened: Integer): Boolean={
      if(chars.isEmpty && opened == 0)
        return true
      if(chars.head=='(')
        return balanceInner(chars.tail, opened + 1)
      if(chars.head==')' && opened <= 0)
        return false
      if(chars.head==')')
        return balanceInner(chars.tail, opened - 1)

      balanceInner(chars.tail, opened)
    }
    balanceInner(chars, 0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins:  List[Int]): Int = {
    def countChangeRec(money: Int, coins:  List[Int]): Int = {
       if(money == 0)
         return 1
      if(money < 0)
        return 0
      if(coins.size <= 0 && money >=0)
        return 0
     countChangeRec(money, coins.tail) + countChangeRec( money - coins.head, coins )
    }

    countChangeRec(money, coins.sorted)
  }

  def mapReduce(f:Int => Int, combiner:(Int, Int)=>Int, unit: Int)(a:Int, b:Int):Int={
    if(a>b) {
      unit
    }else{
      combiner(f(a), mapReduce(f, combiner, unit)(a+1, b))
    }
  }
}
