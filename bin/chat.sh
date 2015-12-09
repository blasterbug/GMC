#!/bin/bash
# Run the chat application
# author : Benjamin Sientzoff (ens15bsf)
#

function help {
  echo ""
  echo "###     HELP     ###"
  echo "USAGE : $0 <multicast strategy> <ordering strategy> <name server host> [debug]"
  echo "Run Gchat"
  echo ""
  echo "Mandatory arguments :"
  echo "  <multicast strategy> :"
  echo "    unreliable : use unrealiable multicast strategy"
  echo "    reliable : use realiable multicast strategy"
  echo "    tree : use tree based multicast strategy (under development)"
  echo "  <ordering strategy> :"
  echo "    unordered : use unsorted ordering strategy"
  echo "    causal : use causal ordering strategy"
  echo "    fifo : use first in first out ordering strategy (use with your risk)"
  echo "  <name server host> stands for the host name where the nameserver runs"
  echo ""
  echo "Exemples :"
  echo "Run the application : $0 <multicast strategy> <ordering strategy> <name server host>"
  echo "Run the application with debug features : $0 <multicast strategy> <ordering strategy>  <name server host> debug"
  echo "Compile the application and generate jar files : $0 compile"
  echo "Print this help message : $0 help"
}

cd ../src/Gchat/

if [ "$1" == "help" ]
  then
    help
    exit 0
fi

if [ "$1" == "compile" ]
  then
    mvn package
  else
    if [ -z $1 ]
      then
        help
        exit 0
      else
        java -jar target/Gchat-jar-with-dependencies.jar $1 $2 $3 $4
        if [ 0 != $? ]
          then
            help
        fi
    fi
fi
