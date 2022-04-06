package com.niro.equalsignoringfields

import scala.annotation.tailrec
import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

object EqualsIgnoringFields {

  def equalsIgnoringFields[T](obj1: T,obj2: T, excludeFields: Seq[String])(
    implicit classTag: ClassTag[T], ttag: TypeTag[T]): Boolean = {
    val obj1Fields = extractCaseClassFields(obj1, excludeFields)
    val obj2Fields = extractCaseClassFields(obj2, excludeFields)
    obj1Fields.equals(obj2Fields)
  }

  @tailrec
  def equalsCollectionIgnoringFields[T](collection1: Seq[T], collection2: Seq[T], excludeFields: Seq[String])(
    implicit classTag: ClassTag[T], ttag: TypeTag[T]): Boolean = {
    (collection1.isEmpty && collection2.isEmpty) ||
      (collection1.length == collection2.length &&
        equalsIgnoringFields(collection1.head, collection2.head, excludeFields) &&
        equalsCollectionIgnoringFields(collection1.tail, collection2.tail, excludeFields)
        )
  }

  private def extractCaseClassFields[T](obj: T, excludeFields: Seq[String])(
    implicit classTag: ClassTag[T], ttag: TypeTag[T]) = {
    val mirror = runtimeMirror(this.getClass.getClassLoader)
    typeOf[T].members.collect {
      case m: MethodSymbol if m.isCaseAccessor =>
        m.asTerm.accessed.asTerm
    }.toList
      .map(mirror.reflect(obj).reflectField)
      .filterNot(x => excludeFields.contains(x.symbol.name.toString.trim))
      .map(x => (x.symbol.name, x.get))
      .toMap
  }


}
