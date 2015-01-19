package recfun

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class MapReduceSuite extends FunSuite{
  import Main.mapReduce
  test("aa"){
    assert(mapReduce(x=>x, (x, y)=>x*y, 0)(1,10) == 55)
  }
}
