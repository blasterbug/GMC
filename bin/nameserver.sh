#!/bin/bash
# Run the name server
# author : Benjamin Sientzoff (ens15bsf)
#

cd ../src/Gcom/
if [ "$1" == "compile" ]
  then 
    mvn package
fi
java -jar target/Gcom-4.2-jar-with-dependencies.jar
