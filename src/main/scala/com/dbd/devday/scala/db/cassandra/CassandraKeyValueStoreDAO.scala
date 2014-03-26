package com.dbd.devday.scala.db.cassandra

import java.util
import com.datastax.driver.core.BoundStatement
import com.datastax.driver.core.ResultSet
import com.datastax.driver.core.Row
import com.datastax.driver.core.Session
import com.dbd.devday.scala.db.ConnectionManager
import com.dbd.devday.scala.db.KeyValueStoreDAO
import com.dbd.devday.scala.model.KeyValue

object CassandraKeyValueStoreDAO {
  final val DEFAULT_KEYSPACE_NAME: String = "developer_day"
}

class CassandraKeyValueStoreDAO(dataSource: ConnectionManager[Session],
                                keyspaceName: String = CassandraKeyValueStoreDAO.DEFAULT_KEYSPACE_NAME) extends KeyValueStoreDAO {

  lazy val (
    findByUserStatement,
    findByUserAndKeyStatement,
    insertStatement,
    updateStatement,
    deleteByUserStatement,
    deleteByUserAndKeyStatement) = {
    val session: Session = getSession
    (session.prepare("SELECT key, value FROM kv WHERE user = ?"),
      session.prepare("SELECT key, value FROM kv WHERE user = ? AND key = ?"),
      session.prepare("INSERT INTO kv (user, key, value) VALUES (?,?,?)"),
      session.prepare("UPDATE kv SET value = ? WHERE user = ? AND key = ?"),
      session.prepare("DELETE FROM kv WHERE user = ?"),
      session.prepare("DELETE FROM kv WHERE user = ? AND key = ?"))
  }

  lazy val getSession = dataSource.getSession(keyspaceName)

  def findByUser(user: String): util.List[KeyValue] = {
    val boundStatement: BoundStatement = new BoundStatement(findByUserStatement)
    boundStatement.bind(user)
    val results: util.List[KeyValue] = new util.ArrayList[KeyValue]
    val resultSet: ResultSet = getSession.execute(boundStatement)
    import scala.collection.JavaConversions._
    for (row <- resultSet.all) {
      results.add(new KeyValue(row.getString(0), row.getString(1)))
    }
    results
  }

  def findByUserAndKey(user: String, key: String): KeyValue = {
    val boundStatement: BoundStatement = new BoundStatement(findByUserAndKeyStatement)
    boundStatement.bind(user, key)
    val resultSet: ResultSet = getSession.execute(boundStatement)
    if (!resultSet.isExhausted) {
      val row: Row = resultSet.one
      new KeyValue(row.getString(0), row.getString(1))
    }
    else null
  }

  def save(user: String, keyValue: KeyValue) {
    val boundStatement: BoundStatement = new BoundStatement(insertStatement)
    boundStatement.bind(user, keyValue.key, keyValue.value)
    getSession.execute(boundStatement)
  }

  def delete(user: String, key: String) {
    val boundStatement: BoundStatement = new BoundStatement(deleteByUserAndKeyStatement)
    boundStatement.bind(user, key)
    getSession.execute(boundStatement)
  }

  def deleteByUser(user: String) {
    val boundStatement: BoundStatement = new BoundStatement(deleteByUserStatement)
    boundStatement.bind(user)
    getSession.execute(boundStatement)
  }

  def update(user: String, keyValue: KeyValue) {
    val boundStatement: BoundStatement = new BoundStatement(updateStatement)
    boundStatement.bind(keyValue.value, user, keyValue.key)
    getSession.execute(boundStatement)
  }
}

