package com.universe.kafka.raw.demos;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Random;

/**
 * @author agui93
 * @since 2020/7/22
 */
public class DemoKafkaProducer {

    public static void main(String[] args) {
        String randomId = Integer.toString(10000 + new Random().nextInt(10000));

        String topic = "demo-topic-a1";
        String bootstraps = "localhost:9092";

        Properties props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "DemoKafkaProducer");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstraps);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props = ProducerConfig.addSerializerToConfig(props, new StringSerializer(), new StringSerializer());

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(props);

        System.out.println();
        System.out.println();
        System.out.println("METRICS");
        kafkaProducer.metrics().forEach((n, m) -> {
            System.out.println("metrics: MetricName={" + n.group() + "},Metric={" + m + "}");
        });


        System.out.println();
        System.out.println();
        System.out.println("PartitionInfo");
        for (PartitionInfo partitionInfo : kafkaProducer.partitionsFor(topic)) {
            System.out.println("partitionInfo={" + partitionInfo + "}");
        }
        System.out.println();


        for (int i = 0; i < 10; i++) {
            String key = randomId + "-k-" + i;
            String value = randomId + "-v-" + i;
            kafkaProducer.send(new ProducerRecord<>(topic, key, value));
        }

        for (int i = 10; i < 20; i++) {
            String key = randomId + "-k-" + i;
            String value = randomId + "-v-" + i;
            kafkaProducer.send(
                    new ProducerRecord<>(topic, key, value),
                    new DemoCallBack(System.currentTimeMillis(), key, value)
            );
        }


        kafkaProducer.close();
        System.out.println("DONE");
    }

}

class DemoCallBack implements Callback {

    private final long startTime;
    private final String key;
    private final String message;

    public DemoCallBack(long startTime, String key, String message) {
        this.startTime = startTime;
        this.key = key;
        this.message = message;
    }


    public void onCompletion(RecordMetadata metadata, Exception exception) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        if (metadata != null) {
            System.out.println("message(" + key + ", " + message + ") sent to partition(" + metadata.partition() + "), " + "offset(" + metadata.offset() + ") in " + elapsedTime + " ms");
        } else {
            exception.printStackTrace();
        }
    }

}