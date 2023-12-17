package com.co.rentautos.service;


import com.co.rentautos.config.kafka.KafkaConfig;
import com.co.rentautos.dto.AlquilerDTO;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.TopicPartitionOffset;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class ReservaDetalleKafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public String obtenerAlquilerDTOKafka(String topico){
        ConsumerRecord<String, String> alquiler;
        KafkaConfig kafkaConfig = new KafkaConfig();
        kafkaTemplate.setConsumerFactory(kafkaConfig.consumerFactory());
        alquiler = kafkaTemplate.receive(topico, 0, 0);
        return Objects.requireNonNull(alquiler.value());
    }

    public Flux<AlquilerDTO> obtenerAlquileresDesdeKafka(String topico){
        ConsumerRecords<String, String> alquileresDetalleRecibidos;
        KafkaConfig kafkaConfig = new KafkaConfig();
        kafkaTemplate.setConsumerFactory(kafkaConfig.consumerFactory());
        alquileresDetalleRecibidos = kafkaTemplate.receive(List.of(new TopicPartitionOffset(topico, 0)));
        return convertirAlquilerDesdeKafkaHaciaAlquilerDetalle(alquileresDetalleRecibidos);
    }

    private Flux<AlquilerDTO> convertirAlquilerDesdeKafkaHaciaAlquilerDetalle(ConsumerRecords<String, String> alquileresDTOSRecibidos){
        List<AlquilerDTO> alquilerDTOS = new LinkedList<>();
        for(ConsumerRecord<String, String> consumerRecord : alquileresDTOSRecibidos){
            alquilerDTOS.add(AlquilerDTO.convertirStringAProducto(consumerRecord.value()));
        }
        return Flux.fromIterable(alquilerDTOS);
    }
}
