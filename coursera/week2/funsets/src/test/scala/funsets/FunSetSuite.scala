package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  
  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }
  
  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   * 
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   * 
   *   val s1 = singletonSet(1)
   * 
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   * 
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   * 
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)
    val s5 = singletonSet(5)
    val s6 = singletonSet(6)
    val s7 = singletonSet(7)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   * 
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {
    
    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("test intersection"){
    new TestSets {
      val set1  = union(union(union(s1, s2),s3),s4)
      val set2  = union(union(union(s4, s5),s6),s7)

      assert(contains(intersect(set1,set2), 4), "Intersected set contains 4")
      assert(!contains(intersect(set1,set2), 1), "Intersected set doesn't contain 1")
      assert(!contains(intersect(set1,set2), 7), "Intersected set doesn't contain 7")
    }
  }

  test("test diff"){
    new TestSets {
      val set1  = union(union(union(s1, s2),s3),s4)
      val set2  = union(union(union(s4, s5),s6),s7)

      assert(contains(diff(set1,set2), 1), "Diff set contains 1")
      assert(contains(diff(set1,set2), 1), "Diff set contains 2")
      assert(contains(diff(set1,set2), 1), "Diff set contains 3")
      assert(!contains(diff(set1,set2), 4), "Diff set doesn't contain 4")
      assert(!contains(diff(set1,set2), 5), "Diff set doesn't contain 5")
      assert(!contains(diff(set1,set2), 6), "Diff set doesn't contain 6")
      assert(!contains(diff(set1,set2), 7), "Diff set doesn't contain 7")
    }
  }

  test("test filter"){
    new TestSets {
      val set1  = union(union(union(union(union(union(s1, s2),s3),s4),s5),s6),s7)

      val filteredSet = filter(set1, (x:Int)=> x%2 == 0)
      assert(contains(filteredSet, 2))
      assert(contains(filteredSet, 4))
      assert(contains(filteredSet, 6))

      assert(!contains(filteredSet, 1))
      assert(!contains(filteredSet, 3))
      assert(!contains(filteredSet, 5))
      assert(!contains(filteredSet, 7))

    }
  }

  test("test forAll"){
    new TestSets {
      val set1  = union(union(union(union(union(union(s1, s2),s3),s4),s5),s6),s7)

      assert(!forall(set1, (x:Int)=> x % 2 == 0))
      assert(forall(set1, (x:Int)=> x>0 && x<8),"All items greate then 0 and less or equal to 7")

    }
  }

  test("test exists"){
    new TestSets {
      val set1  = union(union(union(union(union(union(s1, s2),s3),s4),s5),s6),s7)
      assert(exists(set1, (x:Int)=> x % 2 == 0))
      assert(!exists(set1, (x:Int)=> x>7 && x<9),"All items greate then 0 and less or equal to 7")

    }
  }

  test("test map"){
    new TestSets {
      val set1  = union(union(union(union(union(union(s1, s2),s3),s4),s5),s6),s7)
      assert(contains(map(set1, (x:Int)=> x * 10), 10))
      assert(contains(map(set1, (x:Int)=> x * 100), 700))
      assert(contains(map(set1, (x:Int)=> 0), 0))
      assert(!contains(map(set1, (x:Int)=> x * 10), 1))
    }
  }
}
