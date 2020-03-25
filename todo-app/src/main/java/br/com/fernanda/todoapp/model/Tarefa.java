package br.com.fernanda.todoapp.model;

import br.com.fernanda.todoapp.model.enums.StatusTarefa;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@ApiModel("Tarefa")
@Table(name = "tarefa")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 250)
    private String titulo;

    @Size(min = 0, max = 250)
    private String descricao;

    @Column(name = "dtfim")
    private LocalDate dtFim;

    @Enumerated(EnumType.STRING)
    private StatusTarefa status;

    @NotNull
    @Column(name = "dtcadastro")
    private LocalDate dtCadastro;

    @NotNull
    @Column(name = "dtalteracao")
    private LocalDate dtAlteracao;

}
