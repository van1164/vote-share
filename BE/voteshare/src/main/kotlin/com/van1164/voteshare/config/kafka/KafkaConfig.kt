package com.van1164.voteshare.config.kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kafka.streams.StreamsConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.EnableKafkaStreams
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaStreamsConfiguration
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@EnableKafkaStreams
@EnableKafka
@Configuration
class KafkaConfig(
    @Value("\${spring.kafka.bootstrap-servers}")
    var bootStrapServers : String
) {
    @Bean(name = [KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME])
    fun kafkaStreamConfig() : KafkaStreamsConfiguration{
        val kStreamConfig = hashMapOf<String,Any>()
        kStreamConfig[StreamsConfig.APPLICATION_ID_CONFIG] = "stream-test"
        kStreamConfig[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = bootStrapServers
        kStreamConfig[StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG] = Serdes.String().javaClass.name
        kStreamConfig[StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG] = Serdes.String().javaClass.name
        kStreamConfig[StreamsConfig.NUM_STREAM_THREADS_CONFIG] =1
        return KafkaStreamsConfiguration(kStreamConfig)
    }

    @Bean
    fun kafkaTemplate() : KafkaTemplate<String, Any> {
        return KafkaTemplate<String,Any>(producerFactory());
    }

    @Bean
    fun producerFactory() : ProducerFactory<String,Any>{
        val producerConfig = HashMap<String,Any>()
        producerConfig[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootStrapServers
        producerConfig[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        producerConfig[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        return DefaultKafkaProducerFactory(producerConfig)
    }

    @Bean
    fun consumerFactory() : ConsumerFactory<String,Any>{
        val consumerConfig = HashMap<String,Any>()
        consumerConfig[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootStrapServers
        consumerConfig[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        consumerConfig[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        return DefaultKafkaConsumerFactory(consumerConfig)
    }

    @Bean
    fun kafkaListenerContainerFactory() : ConcurrentKafkaListenerContainerFactory<String, Any>{
        val conCurrentListener = ConcurrentKafkaListenerContainerFactory<String,Any>()
        conCurrentListener.consumerFactory = consumerFactory()
        return conCurrentListener
    }
}