#!/usr/bin/env bash

export DIR="/root/kafka-streams-test"

echo "*** Starting to launch program ***"

    cd $DIR

echo "Launching jar via java command"

    java --add-opens java.base/jdk.internal.ref=ALL-UNNAMED -jar kafka-streams-tester.jar $@

    sleep 1

echo "*** Finished program ***"