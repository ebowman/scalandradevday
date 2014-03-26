package com.dbd.devday.scala.db.cassandra

import org.scalatest.{FunSuite, Matchers}
import com.dbd.devday.scala.model.KeyValue

class CassandraKeyValueStoreDAOTest extends FunSuite with Matchers {
  val testUser: String = "testUser"
  val testKey: String = "testKey"
  val testValue: String = "testValue"

  lazy val connectionManager = new CassandraConnectionManager(Array("127.0.0.1"))
  lazy val dao = new CassandraKeyValueStoreDAO(connectionManager)


  test("create") {
    dao.save(testUser, KeyValue(testKey, testValue))
    val kv = dao.findByUserAndKey(testUser, testKey)
    kv should be(KeyValue(testKey, testValue))
  }

  test("delete") {
    dao.save(testUser, KeyValue(testKey, testValue))
    dao.delete(testUser, testKey)
    dao.findByUserAndKey(testUser, testKey) should be (null)
  }

  test("deleteRow") {
    dao.save(testUser, KeyValue(testKey, testValue))
    dao.deleteByUser(testUser)
    val kvs = dao.findByUser(testUser)
    kvs.size should be(0)
  }

  test("update") {
    dao.save(testUser, KeyValue(testKey, testValue))
    dao.update(testUser, KeyValue(testKey, testValue + "1"))
    dao.findByUserAndKey(testUser, testKey) should be(KeyValue(testKey, testValue + "1"))
  }

  test("getByUserAndKey") {
    dao.save(testUser, KeyValue(testKey, testValue))
    dao.findByUserAndKey(testUser, testKey) should be(KeyValue(testKey, testValue))
  }

  test("getAll") {
    dao.save(testUser, KeyValue(testKey, testValue))
    dao.save(testUser, KeyValue(testKey + "1", testValue))
    val kvs = dao.findByUser(testUser)
    kvs.size should be (2)
    kvs.get(0) should be (KeyValue(testKey, testValue))
    kvs.get(1) should be (KeyValue(testKey + "1", testValue))
  }
}
