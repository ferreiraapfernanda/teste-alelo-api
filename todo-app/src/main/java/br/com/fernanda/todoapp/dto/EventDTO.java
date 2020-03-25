package br.com.fernanda.todoapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {

    private Long id;
    private String status;
    private LocalDate created;
    private LocalDate updated;
    private String summary;
    private String description;
    private LocalDate end;

}
