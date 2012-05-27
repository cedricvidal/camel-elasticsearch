package com.proxiad.camel.elasticsearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.Map;

import org.apache.camel.util.URISupport;
import org.junit.Test;

import com.proxiad.camel.component.elasticsearch.ElasticsearchConfiguration;


/**
 * @author <a href="mailto:c.vidal@proxiad.com">Cedric Vidal</a>
 *
 */
public class ElasticsearchConfigurationTest {

	@Test
	public void localNode() throws Exception {
		ElasticsearchConfiguration conf = new ElasticsearchConfiguration();
		URI uri = new URI("elasticsearch://local?indexName=twitter&indexType=tweet");
        Map<String, Object> parameters = URISupport.parseParameters(uri);
		conf.parseURI(uri, parameters, null);
		assertTrue(conf.isLocal());
		assertEquals("twitter", conf.getIndexName());
		assertEquals("tweet", conf.getIndexType());
		assertTrue(conf.isData());
		assertNull(conf.getClusterName());
	}

	@Test(expected=IllegalArgumentException.class)
	public void localNonDataNodeThrowsIllegalArgumentException() throws Exception {
		ElasticsearchConfiguration conf = new ElasticsearchConfiguration();
		URI uri = new URI("elasticsearch://local?indexName=twitter&indexType=tweet&data=false");
        Map<String, Object> parameters = URISupport.parseParameters(uri);
		conf.parseURI(uri, parameters, null);
	}

	@Test
	public void localConfDefaultsToDataNode() throws Exception {
		ElasticsearchConfiguration conf = new ElasticsearchConfiguration();
		URI uri = new URI("elasticsearch://local?indexName=twitter&indexType=tweet");
        Map<String, Object> parameters = URISupport.parseParameters(uri);
		conf.parseURI(uri, parameters, null);
		assertTrue(conf.isLocal());
		assertTrue(conf.isData());
	}

	@Test
	public void clusterConfDefaultsToNonDataNode() throws Exception {
		ElasticsearchConfiguration conf = new ElasticsearchConfiguration();
		URI uri = new URI("elasticsearch://clustername?indexName=twitter&indexType=tweet");
        Map<String, Object> parameters = URISupport.parseParameters(uri);
		conf.parseURI(uri, parameters, null);
		assertEquals("clustername", conf.getClusterName());
		assertFalse(conf.isLocal());
		assertFalse(conf.isData());
	}

	@Test
	public void localDataNode() throws Exception {
		ElasticsearchConfiguration conf = new ElasticsearchConfiguration();
		URI uri = new URI("elasticsearch://local?indexName=twitter&indexType=tweet&data=true");
        Map<String, Object> parameters = URISupport.parseParameters(uri);
		conf.parseURI(uri, parameters, null);
		assertTrue(conf.isLocal());
		assertEquals("twitter", conf.getIndexName());
		assertEquals("tweet", conf.getIndexType());
		assertTrue(conf.isData());
		assertNull(conf.getClusterName());
	}

}
