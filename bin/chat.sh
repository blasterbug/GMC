#!/bin/bash

# Run the chat application
#

cd ../src/Gchat/
if [ "$1" == "compile" ]
  then
    mvn package
    debug="$2"
  else
    debug="$1"
fi
java -jar target/Gchat-jar-with-dependencies.jar $debug
