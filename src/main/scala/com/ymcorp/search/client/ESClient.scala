package com.ymcorp.search.client

import com.sksamuel.elastic4s.{ElasticsearchClientUri, TcpClient}

/**
  * Created by enosent on 2017. 3. 2..
  */
object ESClient {

  def client: TcpClient = {
    TcpClient.transport(ElasticsearchClientUri("127.0.0.1", 9300))
  }

}
