package com.niro.equalsignoringfields

import org.scalatest.wordspec.AnyWordSpec


class EqualsIgnoringFieldsSpec extends AnyWordSpec {

  case class Test_Class_A(a: String, b: Long, c: Boolean)
  val clazz = Test_Class_A("clazz", 12, true)

  "equalsIgnoringFields" should {
    "returns true when classes are equals and excludes noting" in {
      val class1 = Test_Class_A("clazz", 12, true)
      val class2 = Test_Class_A("clazz", 12, true)
      val result = EqualsIgnoringFields.equalsIgnoringFields(class1, class2, Seq.empty)
      assert(result)
    }

    "returns true when one field is different and been exclude" in {
      val class1 = clazz
      val class2 = clazz.copy(a = "clazz2")
      val result = EqualsIgnoringFields
        .equalsIgnoringFields(class1, class2, Seq("a"))
      assert(result)
    }

    "returns true when excludes all fields" in {
      val class1 = clazz
      val class2 = clazz.copy("clazz2", 13, false)
      val result = EqualsIgnoringFields
        .equalsIgnoringFields(class1, class2, Seq("a", "b", "c"))
      assert(result)
    }

    "returns false when classes are not equals and excludes noting" in {
      val class1 = clazz
      val class2 = clazz.copy("clazz2")
      val result = EqualsIgnoringFields
        .equalsIgnoringFields(class1, class2, Seq.empty)
      assert(!result)
    }

    "returns false when field is not excluded" in {
      val class1 = clazz
      val class2 = clazz.copy(b = 2)
      val result = EqualsIgnoringFields
        .equalsIgnoringFields(class1, class2, Seq("a"))
      assert(!result)
    }
  }

  "equalsCollectionIgnoringFields" should {
    "returns true when both collections are in the same size and order" +
      "and all the elements in are equals" in {
      val class1 = clazz
      val class2 = clazz.copy("clazz2")
      val col1 = Seq(class1, class2)
      val col2 = Seq(class1, class2)
      val result = EqualsIgnoringFields
        .equalsCollectionIgnoringFields(col1, col2, Seq.empty)
      assert(result)
    }

    "returns true when both collections are in the same size and order " +
      "and one field is different in the elements and been exclude" in {
      val class1 = clazz
      val class2 = clazz.copy("clazz2")
      val col1 = Seq(class1, class2)
      val col2 = Seq(class1.copy("clazz3"), class2.copy("clazz4"))
      val result = EqualsIgnoringFields
        .equalsCollectionIgnoringFields(col1, col2, Seq("a"))
      assert(result)
    }

    "returns true when both collections are in the same size and order " +
      "and all the fields are different and all the fields been exclude" in {
      val class1 = clazz
      val class2 = clazz.copy("clazz2")
      val class3 = clazz.copy("clazz3", 1, false)
      val class4 = clazz.copy("clazz4", 1, false)
      val col1 = Seq(class1, class2)
      val col2 = Seq(class3, class4)
      val result = EqualsIgnoringFields
        .equalsCollectionIgnoringFields(col1, col2, Seq("a", "b", "c"))
      assert(result)
    }

    "returns false when both collections are in the same size" in {
      val class1 = clazz
      val class2 = clazz.copy("clazz2")
      val class3 = clazz.copy("clazz3", 1, false)
      val col1 = Seq(class1, class2)
      val col2 = Seq(class3)
      val result = EqualsIgnoringFields
        .equalsCollectionIgnoringFields(col1, col2, Seq("a", "b", "c"))
      assert(!result)
    }

    "returns false when both collections contains the same elements but not order" +
      "and nothing is been exclude" in {
      val class1 = clazz
      val class2 = clazz.copy("clazz2")
      val col1 = Seq(class1, class2)
      val col2 = Seq(class2, class1)
      val result = EqualsIgnoringFields
        .equalsCollectionIgnoringFields(col1, col2, Seq.empty)
      assert(!result)
    }
  }

}
