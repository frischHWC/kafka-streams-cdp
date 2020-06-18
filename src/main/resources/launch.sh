#!/usr/bin/env bash

export DIR="/home/root/kafka-streams-test"

echo "*** Starting to launch program ***"

    cd $DIR

echo "Launching jar via java command"

    java -jar kafka-streams-tester.jar $@

    sleep 1

echo "*** Finished program ***"