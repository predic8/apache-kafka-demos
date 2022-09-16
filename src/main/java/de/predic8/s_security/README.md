# Kopiervorlagen

## SCRAM

### server.properties

    listeners=SASL_PLAINTEXT://127.0.0.1:9092,PLAINTEXT://127.0.0.1:9093
    advertised.listeners=SASL_PLAINTEXT://127.0.0.1:9092,PLAINTEXT://127.0.0.1:9093
    
    sasl.mechanism.inter.broker.protocol=PLAINTEXT
    security.inter.broker.protocol=PLAINTEXT
    
    sasl.enabled.mechanisms=SCRAM-SHA-256
    
    listener.name.sasl_plaintext.scram-sha-256.sasl.jaas.config=org.apache.kafka.common.security.scram.ScramLoginModule required;

## TLS

### server.properties

    ssl.keystore.location=config/broker-1.p12
    ssl.keystore.password=abc123
    ssl.key.password=abc123
    ssl.truststore.location=config/ca.jks
    ssl.truststore.password=abc123