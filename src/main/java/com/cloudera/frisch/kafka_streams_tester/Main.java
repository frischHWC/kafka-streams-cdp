package com.cloudera.frisch.kafka_streams_tester;

import com.cloudera.frisch.kafka_streams_tester.config.KafkaConfig;
import com.cloudera.frisch.kafka_streams_tester.config.PropertiesLoader;

import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.log4j.Logger;

import java.util.Properties;

@SuppressWarnings("unchecked")
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String [] args) {

        logger.info("Starting to launch Kafka Streams");

        Properties props = KafkaConfig.getKafkaProperties();

        final StreamsBuilder builder = new StreamsBuilder();

        // TODO: Define types of the KStream below
        final KStream<?, ?> inputStream = builder.stream(PropertiesLoader.getProperty("kafka.topic.input"));

        final KStream<?, ?> outputStream = Treatment.treatment(inputStream);
        outputStream.to(PropertiesLoader.getProperty("kafka.topic.output"));

        // Run streams
        final KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.cleanUp();
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

    }

}
