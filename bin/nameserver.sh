#!/bin/bash

# Run the Naming Server
#

cd ../src/Gcom/
mvn package
java -jar target/Gcom-4.2-jar-with-dependencies.jar
