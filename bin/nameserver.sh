#!/bin/bash

# Run the Naming Server
#

cd ../src/Gcom/
if [ "$1" == "compile" ]
  then 
    mvn package
fi
java -jar target/Gcom-4.2-jar-with-dependencies.jar
