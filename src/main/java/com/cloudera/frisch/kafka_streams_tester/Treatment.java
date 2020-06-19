package com.cloudera.frisch.kafka_streams_tester;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.log4j.Logger;

class Treatment {

    private final static Logger logger = Logger.getLogger(Treatment.class);

    static KStream<?, ?> treatment(final KStream<?, ?> inputStream) {

        // TODO: Write Kafka Streams code here
        inputStream.foreach((k,v) -> logger.debug("Message: " + v));

        return inputStream;
    }
}
