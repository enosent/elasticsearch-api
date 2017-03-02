package com.ymcorp.search.core

import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.{ElasticsearchClientUri, TcpClient}
import com.ymcorp.search.client.ESClient
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy
import org.elasticsearch.search.aggregations.bucket.terms.Terms

import scala.collection.immutable.HashMap

/**
  * Created by enosent on 2017. 3. 2..
  */
object ElasticsearchService {

  // 상품 전체 조회
  def list: String = {

    // Here we create an instance of the TCP client
    val client = ESClient.client

    val resp = client.execute {
      search("test_deal_list") query {
        matchAllQuery
      }
    }.await

    resp.toString
  }

  // Aggregation
  def aggregate: String = {

    // Here we create an instance of the TCP client
    val client = ESClient.client

    val resp = client.execute {
      search ("test_deal_list") query {
        matchAllQuery
      } postFilter {
        boolQuery.not(termQuery("dealname", ""))
      } aggregations {
        termsAggregation("dealname_cnt") field "dealname.keyword" order Terms.Order.count(false)
      } from 0 size 0

    }.await

    println(resp)

    resp.toString
  }

  def scoreSearch: String = {

    // Here we create an instance of the TCP client
    val client = ESClient.client

    val keyword = "신사동"

    val fieldInfo = HashMap("category.cid" -> 3.0f, "dealname" -> 1.5f)

    val resp = client.execute {
      search ("test_deal_list") query {
        multiMatchQuery(keyword) fields(fieldInfo)
      } postFilter {
        boolQuery.not(termQuery("dealname", ""))
      }
    }.await

    println(resp)

    resp.toString
  }

}