package com.ymcorp.search.client;

import java.net.InetAddress;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ESClient {

	@SuppressWarnings("resource")
	@Bean(name="esClient", destroyMethod="close")
	public TransportClient connection() throws Exception {
		// Settings settings = Settings.builder().put("cluster.name", "logdb").build();
		return new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
	}

}