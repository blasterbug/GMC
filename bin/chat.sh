#!/bin/bash

# Run the chat application
#

cd ../src/Gchat/
mvn package
java -jar target/Gchat-jar-with-dependencies.jar $1
