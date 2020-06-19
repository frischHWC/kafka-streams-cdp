package com.cloudera.frisch.kafka_streams_tester.config;

import com.cloudera.frisch.kafka_streams_tester.Utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.log4j.Logger;

import java.util.Properties;


public class KafkaConfig {

    private final static Logger logger = Logger.getLogger(KafkaConfig.class);


    public static Properties getKafkaProperties() {
        java.util.Properties props = new java.util.Properties();
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, PropertiesLoader.getProperty("kafka.brokers"));
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, PropertiesLoader.getProperty("kafka.application.id"));
        //props.put(StreamsConfig.CLIENT_ID_CONFIG, "kafka-streamer_2");

        String securityProtocol = PropertiesLoader.properties.getProperty("kafka.security.protocol");
        props.put(StreamsConfig.SECURITY_PROTOCOL_CONFIG, securityProtocol);

        //Kerberos config
        if (securityProtocol.equalsIgnoreCase("SASL_PLAINTEXT")
                || securityProtocol.equalsIgnoreCase("SASL_SSL")) {
            logger.debug("Kerberos config is added");
            Utils.createJaasConfigFile("kafka-jaas-streamtest.config", "KafkaClient",
                    PropertiesLoader.getProperty("kafka.auth.kerberos.keytab"), PropertiesLoader.getProperty("kafka.auth.kerberos.user"),
                    true, false);
            System.setProperty("java.security.auth.login.config", "kafka-jaas-streamtest.config");

            props.put(SaslConfigs.SASL_MECHANISM, PropertiesLoader.getProperty("kafka.sasl.mechanism"));
            props.put(SaslConfigs.SASL_KERBEROS_SERVICE_NAME, PropertiesLoader.getProperty("kafka.sasl.kerberos.service.name"));

            Utils.loginUserWithKerberos(PropertiesLoader.getProperty("kafka.auth.kerberos.user"),
                    PropertiesLoader.getProperty("kafka.auth.kerberos.keytab"), new Configuration());
        }

        // SSL configs
        if (securityProtocol.equalsIgnoreCase("SASL_SSL") || securityProtocol.equalsIgnoreCase("SSL")) {
            logger.debug("SSL config is added");
            props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, PropertiesLoader.getProperty("kafka.keystore.location"));
            props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, PropertiesLoader.getProperty("kafka.truststore.location"));
            props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, PropertiesLoader.getProperty("kafka.keystore.key.password"));
            props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, PropertiesLoader.getProperty("kafka.keystore.pasword"));
            props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, PropertiesLoader.getProperty("kafka.truststore.password"));
        }

        return props;

    }
}
