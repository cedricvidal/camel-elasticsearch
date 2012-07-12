Camel Elasticsearch component
----------------------------------

This Elasticsearch component for Camel supports indexing content into the [Elasticsearch](http://www.elasticsearch.org/) search engine.

Feel free to clone this Github repository and enhance the component.

Regards,

Cedric Vidal
http://blog.proxiad.com

# Maven configuration

#### Add the following Maven repository to your pom.xml

	<repository>
		<id>cedricvidal-cloudbees-release</id>
		<name>cedricvidal-cloudbees-release</name>
		<url>https://repository-cedricvidal.forge.cloudbees.com/release/</url>
		<releases>
			<enabled>true</enabled>
		</releases>
		<snapshots>
			<enabled>false</enabled>
		</snapshots>
	</repository>

#### Add dependencies to your project

	<dependencies>
		<dependency>
			<groupId>com.proxiad.camel</groupId>
			<artifactId>camel-elasticsearch</artifactId>
			<version>0.0.1</version>
		</dependency>
	</dependencies>

# Building from sources

	$ git clone git://github.com/cedricvidal/camel-elasticsearch.git
	$ cd camel-elasticsearch
	$ mvn install

