package br.com.fernanda.todoapp;

import br.com.fernanda.todoapp.dto.EventDTO;
import br.com.fernanda.todoapp.model.Tarefa;
import br.com.fernanda.todoapp.model.enums.StatusTarefa;
import br.com.fernanda.todoapp.service.TarefaService;
import br.com.fernanda.todoapp.util.CustomLocalDateTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class APIExternaTest {

    public static final String API_EXTERNA_EVENTS = "http://api-externa.com/api/events";

    @Autowired
    private TarefaService tarefaService;

    @Mock
    private RestTemplate restTemplate;

    @Value("classpath:api-externa-events.json")
    private Resource resourceArquivoApiExterna;

    @Test
    public void deveReceberEventosDaAPIMockada() throws IOException {

        mockApiExterna();

        List<EventDTO> eventos = Arrays.asList(restTemplate.getForObject(API_EXTERNA_EVENTS, EventDTO[].class));

        Assertions.assertNotNull(eventos);
        Assertions.assertEquals(4, eventos.size());

    }

    @Test
    public void deveCadastrarEventosDaAPIMockada() throws IOException {

        mockApiExterna();

        List<EventDTO> eventos = Arrays.asList(restTemplate.getForObject(API_EXTERNA_EVENTS, EventDTO[].class));

        List<Tarefa> tarefasCriadas = Lists.newArrayList();

        eventos.forEach(evento -> {
            Tarefa tarefa = Tarefa.builder()
                    .id(evento.getId())
                    .titulo(evento.getSummary())
                    .descricao(evento.getDescription())
                    .dtFim(evento.getEnd())
                    .status(StatusTarefa.convert(evento.getStatus()))
                    .dtCadastro(evento.getCreated())
                    .dtAlteracao(evento.getUpdated())
                    .build();

            Tarefa tarefaCriada = tarefaService.create(tarefa);
            tarefasCriadas.add(tarefaCriada);

            Assertions.assertNotNull(tarefaCriada);
        });

        List<Tarefa> tarefasCadastradas = tarefaService.list();

        Assertions.assertNotNull(tarefasCadastradas);

        tarefasCadastradas.forEach(tarefaCriada -> {
            Assertions.assertTrue(tarefasCadastradas.contains(tarefaCriada));
        });

    }

    private void mockApiExterna() throws IOException {

        Reader reader = Files.newBufferedReader(Paths.get(resourceArquivoApiExterna.getURI()));

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new CustomLocalDateTypeAdapter());
        Gson gson = gsonBuilder.create();

        EventDTO[] eventos = gson.fromJson(reader, EventDTO[].class);

        Mockito.when(restTemplate.getForObject(API_EXTERNA_EVENTS, EventDTO[].class))
                .thenReturn(eventos);

        reader.close();

    }
}
