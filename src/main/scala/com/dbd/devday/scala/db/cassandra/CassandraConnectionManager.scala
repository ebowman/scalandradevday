package com.dbd.devday.scala.db.cassandra

import com.datastax.driver.core.Cluster
import com.datastax.driver.core.Session
import com.dbd.devday.scala.db.ConnectionManager

/**
 * Simple class for getting a Cassandra Session
 *
 * TODO - exception handling, input validation etc. 
 */
object CassandraConnectionManager {
  final val DEFAULT_CASSANDRA_PORT: Int = 9042
}

class CassandraConnectionManager(nodes: Array[String],
                                 port: Int = CassandraConnectionManager.DEFAULT_CASSANDRA_PORT) extends ConnectionManager[Session] {

  val cluster = Cluster.builder.addContactPoints(nodes:_*).withPort(port).build

  def getSession(keyspace: String): Session = {
    cluster.connect(keyspace)
  }

  def close() {
    cluster.close()
  }
}

