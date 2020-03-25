package br.com.fernanda.todoapp;

import br.com.fernanda.todoapp.dto.EventDTO;
import br.com.fernanda.todoapp.model.Tarefa;
import br.com.fernanda.todoapp.model.enums.StatusTarefa;
import br.com.fernanda.todoapp.service.TarefaService;
import com.google.gson.Gson;
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
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class TarefaTest {

    @Autowired
    private TarefaService tarefaService;

    private List<Tarefa> listaDeTarefas;

    @Test
    public void deveRetornarTarefaCriada() {
        Tarefa tarefa = criaNovaTarefaComId(6L);
        Tarefa tarefaCriada = tarefaService.create(tarefa);

        Assertions.assertEquals(tarefa, tarefaCriada);
    }

    @Test
    public void deveRetornarTarefaComIdGeradoAutomaticamente() {
        Tarefa tarefa = criaNovaTarefaComId(600L);

        Tarefa tarefaCriada = tarefaService.create(tarefa);

        Assertions.assertNotEquals(tarefa, tarefaCriada);
        Assertions.assertNotEquals(tarefa.getId(), tarefaCriada.getId());
        Assertions.assertEquals(6L, tarefaCriada.getId());
    }

    @Test
    public void deveRetornarListaDeTarefasBase() {

        listaDeTarefas = criaListaDeTarefas();

        List<Tarefa> tarefaList = tarefaService.list();

        Assertions.assertEquals(listaDeTarefas.size(), tarefaList.size());
        Assertions.assertEquals(listaDeTarefas.get(0), tarefaList.get(0));
        Assertions.assertEquals(listaDeTarefas.get(1), tarefaList.get(1));
        Assertions.assertEquals(listaDeTarefas.get(2), tarefaList.get(2));
        Assertions.assertEquals(listaDeTarefas.get(3), tarefaList.get(3));
        Assertions.assertEquals(listaDeTarefas.get(4), tarefaList.get(4));

    }

    @Test
    public void deveRetornarATarefaCriadaNaListagem() {

        listaDeTarefas = criaListaDeTarefas();

        Tarefa tarefaCriada = tarefaService.create(criaNovaTarefaComId(6L));
        listaDeTarefas.add(tarefaCriada);

        List<Tarefa> tarefaList = tarefaService.list();

        Assertions.assertEquals(listaDeTarefas.size(), tarefaList.size());
        Assertions.assertEquals(listaDeTarefas.get(0), tarefaList.get(0));
        Assertions.assertEquals(listaDeTarefas.get(1), tarefaList.get(1));
        Assertions.assertEquals(listaDeTarefas.get(2), tarefaList.get(2));
        Assertions.assertEquals(listaDeTarefas.get(3), tarefaList.get(3));
        Assertions.assertEquals(listaDeTarefas.get(4), tarefaList.get(4));
        Assertions.assertEquals(listaDeTarefas.get(5), tarefaList.get(5));

    }

    @Test
    public void deveRemoverUmaTarefa() {

        tarefaService.delete(1L);

        listaDeTarefas = criaListaDeTarefas();
        listaDeTarefas.remove(0);

        List<Tarefa> tarefaList = tarefaService.list();

        Assertions.assertEquals(listaDeTarefas.size(), tarefaList.size());
        Assertions.assertEquals(listaDeTarefas.get(0), tarefaList.get(0));
        Assertions.assertEquals(listaDeTarefas.get(1), tarefaList.get(1));
        Assertions.assertEquals(listaDeTarefas.get(2), tarefaList.get(2));
        Assertions.assertEquals(listaDeTarefas.get(3), tarefaList.get(3));

    }

    @Test
    public void deveAtualizarUmaTarefa() {

        Tarefa tarefa = tarefaService.get(3L);
        //tarefa.setId(400L);
        tarefa.setTitulo("tarefa 400");
        tarefa.setDescricao("essa tarefa foi alterada");
        tarefa.setStatus(StatusTarefa.FEITO);

        tarefa.setDtFim(LocalDate.now());
        //tarefa.setDtCadastro(LocalDate.now());
        tarefa.setDtAlteracao(LocalDate.now());

        Tarefa tarefaAlterada = tarefaService.update(3L, tarefa);

        Assertions.assertNotNull(tarefaAlterada);
        Assertions.assertEquals(tarefa, tarefaAlterada);

    }

    @Test
    public void deveAtualizarSomenteCamposPermitidosDaTarefa() {

        Tarefa tarefa = tarefaService.get(3L);
        tarefa.setId(400L);
        tarefa.setTitulo("tarefa 400");
        tarefa.setDescricao("essa tarefa foi alterada");
        tarefa.setStatus(StatusTarefa.FEITO);

        tarefa.setDtFim(LocalDate.now());
        tarefa.setDtCadastro(LocalDate.now());
        tarefa.setDtAlteracao(LocalDate.now());

        Tarefa tarefaAlterada = tarefaService.update(3L, tarefa);

        Assertions.assertNotNull(tarefaAlterada);
        Assertions.assertNotEquals(tarefa, tarefaAlterada);
        Assertions.assertNotEquals(tarefa.getId(), tarefaAlterada.getId());
        Assertions.assertNotEquals(tarefa.getDtCadastro(), tarefaAlterada.getDtCadastro());

    }

    private List<Tarefa> criaListaDeTarefas() {

        List<Tarefa> tarefas = Lists.newArrayList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate data = LocalDate.parse("2020-03-24", formatter);

        for (int index = 0; index < 5; index++) {
            Long id = Long.parseLong(String.valueOf(index + 1));
            tarefas.add(new Tarefa(id, ("TAREFA " + id), "TASK", null, StatusTarefa.FAZER, data, data));
        }

        return tarefas;

    }

    private Tarefa criaNovaTarefaComId(long id) {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(id);
        tarefa.setTitulo("tarefa 100");
        tarefa.setDescricao("essa tarefa é um teste");
        tarefa.setStatus(StatusTarefa.FAZENDO);

        tarefa.setDtFim(LocalDate.now());
        tarefa.setDtCadastro(LocalDate.now());
        tarefa.setDtAlteracao(LocalDate.now());
        return tarefa;
    }

}
