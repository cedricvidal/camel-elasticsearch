/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.proxiad.camel.component.elasticsearch;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:c.vidal@proxiad.com">Cedric Vidal</a>
 *
 */
public class ElasticsearchConfiguration {
	private URI uri;
	private String protocolType;
	private String authority;
	private String clusterName;
	private String indexName;
	private String indexType;
	private boolean local;
	private Boolean data;
	private static String LOCAL_NAME = "local";
	private static final Logger log = LoggerFactory.getLogger(ElasticsearchConfiguration.class);

	public ElasticsearchConfiguration() {
	}

	public ElasticsearchConfiguration(URI uri) throws Exception {
		this();
		this.uri = uri;
	}

	public void parseURI(URI uri, Map<String, Object> parameters,
			ElasticsearchComponent component) throws Exception {
		String protocol = uri.getScheme();

		if (!protocol.equalsIgnoreCase("elasticsearch")) {
			throw new IllegalArgumentException(
					"Unrecognized elasticsearch protocol: " + protocol
							+ " for uri: " + uri);
		}
		setUri(uri);
		setAuthority(uri.getAuthority());
		if (!isValidAuthority()) {
			throw new URISyntaxException(
					uri.toASCIIString(),
					"Incorrect URI syntax specified for the elasticsearch endpoint."
							+ "Please specify the syntax as \"elasticsearch:[Cluster Name | 'local']?[Query]\"");
		}

		if (LOCAL_NAME.equals(getAuthority())) {
			setLocal(true);
			setClusterName(null);
		} else {
			setLocal(false);
			setClusterName(getAuthority());
		}

		data = toBoolean(parameters.remove("data"));
		if(data == null) {
			data = local;
		}
		if(local && !data) {
			throw new IllegalArgumentException("It makes no sens using a local node with no data");
		}
		indexName = (String) parameters.remove("indexName");
		indexType = (String) parameters.remove("indexType");

	}

	protected Boolean toBoolean(Object string) {
		if("true".equals(string))
			return true;
		if("false".equals(string))
			return false;
		return null;
	}

	public Node buildNode() {
		NodeBuilder builder = nodeBuilder().local(isLocal()).data(isData());
		if(!isLocal() && getClusterName() != null) {
			builder.clusterName(getClusterName());
		}
		return builder.node();
	}

	private boolean isValidAuthority() throws URISyntaxException {
		if (authority.contains(":")) {
			return false;
		}
		return true;

	}

	private String retrieveTokenFromAuthority(String token)
			throws URISyntaxException {
		String retval;

		if (token.equalsIgnoreCase("clusterName")) {
			retval = uri.getAuthority().split(":")[0];
		} else {
			retval = uri.getAuthority().split(":")[1];
		}
		return retval;
	}

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public String getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getIndexType() {
		return indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	public boolean isLocal() {
		return local;
	}

	public void setLocal(boolean local) {
		this.local = local;
	}

	public boolean isData() {
		return data;
	}

	public void setData(boolean data) {
		this.data = data;
	}

}
