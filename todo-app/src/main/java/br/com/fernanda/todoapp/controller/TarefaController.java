package br.com.fernanda.todoapp.controller;

import br.com.fernanda.todoapp.model.Tarefa;
import br.com.fernanda.todoapp.service.TarefaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @ApiOperation("Lista todas as tarefas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso", response = ResponseEntity.class),
            @ApiResponse(code = 204, message = "Sem conteúdo"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")})
    @GetMapping("")
    public ResponseEntity<List<Tarefa>> listTarefas() {

        List<Tarefa> tarefas = tarefaService.list();

        if (tarefas == null || tarefas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(tarefas);
    }

    @ApiOperation("Encontra uma tarefa pelo id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso", response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "Não encontrado")})
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> getTarefa(@PathVariable Long id) {

        Tarefa tarefa = tarefaService.get(id);

        if (tarefa == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(tarefa);
    }

    @ApiOperation("Cadastra uma nova tarefa")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Erro interno no servidor")})
    @PostMapping("")
    public ResponseEntity<Tarefa> createTarefa(@RequestBody Tarefa tarefa) {

        Tarefa tarefaCriada = tarefaService.create(tarefa);

        if (tarefaCriada == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(tarefaCriada);
    }

    @ApiOperation("Remove uma tarefa")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteTarefa(@PathVariable Long id) {
        tarefaService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualiza uma tarefa")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Erro interno no servidor")})
    public ResponseEntity<Tarefa> updateTarefa(@PathVariable Long id, @RequestBody Tarefa tarefa) {

        try {
            Tarefa tarefaAlterada = tarefaService.update(id, tarefa);
            return ResponseEntity.status(HttpStatus.OK).body(tarefaAlterada);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

}
