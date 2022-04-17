package br.com.springKafka.domain.dto;

import lombok.Data;
import br.com.springKafka.domain.entity.Book;
import java.util.List;

@Data
public class PeopleDTO {

    private String name;

    private String cpf;

    private List<String> books;
}
