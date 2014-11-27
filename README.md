elasticimmo
===========

The main purpose of this project is to import real estate information provided in the OpenImmo format [1] into Elasticsearch. Since the imported data is converted into an Elasticsearch mapping that is independent of OpenImmo it is conceivable to extend the import to other formats in the future.

[1] http://www.openimmo.de/

#Fixing XML Files before importing
##Immonet
$ sed -i 's/<openimmo xmlns="http:\/\/www.openimmo.de">/<openimmo>/' immonet_export.xml

#Running OpenImmo import with Maven

`mvn install exec:java -Dexec.mainClass="de.joeakeem.elasticimmo.openimmo.dataimport.OpenImmoImporter"`

Specifying the elasticsearch cluster nodes and an OpenImmo XML file:

`mvn install exec:java -Dcluster-nodes=localhost:9301 -Dopenimmo.import.file=/home/joeakeem/openimmo/openimmo-data_127.xml Dexec.mainClass="de.joeakeem.elasticimmo.openimmo.dataimport.OpenImmoImporter"`

Defaults are:
* cluster-nodes=localhost:9300
* openimmo.import.file=classpath:de/joeakeem/elasticimmo/openimmo/dataimport/openimmo-data_127.xml