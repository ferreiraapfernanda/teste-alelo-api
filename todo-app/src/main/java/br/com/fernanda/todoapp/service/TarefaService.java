package br.com.fernanda.todoapp.service;

import br.com.fernanda.todoapp.model.Tarefa;
import br.com.fernanda.todoapp.repository.TarefaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public List<Tarefa> list(){
        return tarefaRepository.findAll();
    }

    public Tarefa create(Tarefa tarefa){
        return tarefaRepository.save(tarefa);
    }

    public void delete(Long id){
        tarefaRepository.deleteById(id);
    }

    public Tarefa get(Long id) { return tarefaRepository.findById(id).orElse(null); }

    public Tarefa update(Long id, Tarefa tarefa) throws EmptyResultDataAccessException{

        Tarefa tarefaSalva = tarefaRepository.findById(id).orElse(null);

        if(tarefaSalva == null){
            throw new EmptyResultDataAccessException(1);
        }

        tarefa.setDtAlteracao(LocalDate.now());

        BeanUtils.copyProperties(tarefa, tarefaSalva, "id", "dtCadastro");

        return tarefaRepository.save(tarefaSalva);
    }

}
