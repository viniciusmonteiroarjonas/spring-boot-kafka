package br.com.springKafka.kafka.consumer;

import br.com.springKafka.domain.entity.Book;
import br.com.springKafka.repository.PeopleRepository;
import br.com.springkafka.People;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class PeopleConsumer {

    @Autowired
    private PeopleRepository peopleRepository;

    // Acknowledgment -> Serve para falar para o kafka que ele já leu a mensagem e não repetir.

    @KafkaListener(topics = "${topic.name}")
    public void consumer(ConsumerRecord<String, People> record, Acknowledgment ack) {

        var people = record.value();

        log.info("Mensagem consumida ->" + people.toString());
        ack.acknowledge();

        // Persistindo informação no banco de dados

        var peopleEntity = br.com.springKafka.domain.entity.People.builder().build();

        System.out.println("ID" + people.getId().toString());
        System.out.println("NAME" + people.getName().toString());
        System.out.println("CPF" + people.getCpf());

        peopleEntity.setId(people.getId().toString());
        peopleEntity.setName(people.getName().toString());
        peopleEntity.setCpf(people.getCpf().toString());
        peopleEntity.setBooks(people.getBooks().stream()
                .map(book -> Book.builder()
                        .people(peopleEntity)
                        .name(book.toString())
                        .build()).collect(Collectors.toList()));


        peopleRepository.save(peopleEntity);
    }

}
