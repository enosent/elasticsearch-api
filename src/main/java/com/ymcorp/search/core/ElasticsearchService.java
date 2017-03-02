package com.ymcorp.search.core;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;

/**
 * @author enosent
 *
 */
@Service
public class ElasticsearchService {

	@Autowired
	private TransportClient esClient;

	// 상품 조회
	public void search() {

		SearchResponse response = esClient.prepareSearch("test_deal_list")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(matchAllQuery())
				.setPostFilter(boolQuery().mustNot(termQuery("dealname", "")))
				.execute()
				.actionGet();

		response.getHits().forEach( hit -> {
			hit.getSource().forEach((k, v) -> {
				System.out.println( k + " => " + v);
			});

			System.out.println();
		});

	}

	// Aggregation
	public void aggregation() {

		SearchResponse response = esClient.prepareSearch("test_deal_list")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(matchAllQuery())
				.setPostFilter(
						boolQuery().mustNot(termsQuery("dealname", "")) //keyword(빈문자는 제외)
						)
				.addAggregation(AggregationBuilders.terms("dealname_cnt").field("dealname.keyword").order(Terms.Order.count(false)))
				.setFrom(0).setSize(0)
				.execute()
				.actionGet();

		Terms keywords = response.getAggregations().get("dealname_cnt");
		List<Bucket> datas = keywords.getBuckets();

		for(Bucket b : datas){
			String keyword = b.getKeyAsString();
			long searchCnt = b.getDocCount();

			System.out.println(keyword + " ( "+ searchCnt + " )");
		}
		System.out.println();
	}

	// score
	public void scoreSearch() {
		Map<String, Float> fields = new HashMap<>();

		fields.put("category.cid", 3.0f);
		fields.put("dealname", 1.5f);

		String dealname = "신사동";

		SearchResponse response = esClient.prepareSearch("test_deal_list")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(
						multiMatchQuery(dealname).fields(fields)
						)
				.setPostFilter(
						boolQuery().mustNot(termsQuery("dealname", "")) //keyword(빈문자는 제외)
						)
				.execute()
				.actionGet();

		response.getHits().forEach( hit -> {
			hit.getSource().forEach((k, v) -> {
				System.out.println( k + " : " + v);
			});

			System.out.println();
		});

	}

}