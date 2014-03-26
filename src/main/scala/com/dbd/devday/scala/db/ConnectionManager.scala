package com.dbd.devday.scala.db

/**
 * Created by ebowman on 26/03/2014.
 */
trait ConnectionManager[T] {
  def getSession(tableName: String): T

  def close()
}
