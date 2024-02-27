package com.van1164.voteshare.service

import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class KafkaStreamService {

    val stringSerde: Serde<String> = Serdes.String()


    @Autowired
    fun buildPipeline(sb : StreamsBuilder) {
        sb.stream("testTopic", Consumed.with(stringSerde, stringSerde)).run{
            selectKey{key,value->
                value.substring(0,4)
            }.filter { key, value ->
                println("testTopic = $key||$value")
                value.contains("test")
            }.to("vote")

        }
    }
}