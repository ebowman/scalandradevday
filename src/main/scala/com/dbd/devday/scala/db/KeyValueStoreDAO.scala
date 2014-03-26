package com.dbd.devday.scala.db

import java.util
import com.dbd.devday.scala.model.KeyValue

trait KeyValueStoreDAO {

  def findByUser(user: String): util.List[KeyValue]

  def findByUserAndKey(user: String, key: String): KeyValue

  def save(user: String, keyValue: KeyValue)

  def delete(user: String, key: String)

  def deleteByUser(user: String)

  def update(user: String, keyValue: KeyValue)
}

