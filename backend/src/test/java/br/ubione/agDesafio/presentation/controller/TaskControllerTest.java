package br.ubione.agDesafio.presentation.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.ubione.agDesafio.application.dto.ProjectRequestDTO;
import br.ubione.agDesafio.application.dto.TaskRequestDTO;
import br.ubione.agDesafio.application.enums.ProjectStatus;
import br.ubione.agDesafio.application.service.TaskService;
import br.ubione.agDesafio.domain.model.Customer;
import br.ubione.agDesafio.domain.model.Project;
import br.ubione.agDesafio.domain.model.Task;
import br.ubione.agDesafio.presentation.controller.TaskController;

@WebMvcTest(TaskController.class)  // Usando @WebMvcTest para carregar apenas o controlador
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;  // O MockMvc é injetado automaticamente

    @MockBean
    private TaskService taskService;  // MockBean para o serviço TaskService

    @Test
    public void testFilterList() throws Exception {
        // Dado que há tarefas com filtros
        Task task = new Task();
        task.setName("Task A");
        Page<Task> page = new PageImpl<>(Arrays.asList(task));

        when(taskService.list(any(), anyLong(), any(), any(), any(Pageable.class))).thenReturn(page);

        // Quando fazemos uma requisição GET para "/tasks/paginado"
        mockMvc.perform(get("/tasks/paginado")
                .param("name", "Task A")
                .param("projectId", "1")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                // Então o status HTTP deve ser 200 OK
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Task A"));
    }

    @Test
    public void testList() throws Exception {
        // Dado que há tarefas no repositório
        Task task = new Task();
        task.setName("Task A");
        List<Task> tasks = Arrays.asList(task);

        when(taskService.list()).thenReturn(tasks);

        // Quando fazemos uma requisição GET para "/tasks"
        mockMvc.perform(get("/tasks")
                .contentType(MediaType.APPLICATION_JSON))
                // Então o status HTTP deve ser 200 OK
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Task A"));
    }

    @Test
    public void testCreate() throws Exception {
        // Dados de entrada para criar a tarefa
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO();
        taskRequestDTO.setName("Atividade nova");
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);  
        Date tomorrow = calendar.getTime();  

        Timestamp dtInicio = new Timestamp(tomorrow.getTime());
        taskRequestDTO.setDtInicio(dtInicio);

        Task task = new Task();
        task.setName("Atividade nova");
        task.setDtInicio(dtInicio);

        when(taskService.save(any(TaskRequestDTO.class))).thenReturn(task);

        // Quando fazemos uma requisição POST para "/tasks"
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Atividade nova"));

    }   

    @Test
    public void testFindById() throws Exception {
        // Dado que existe uma tarefa com ID 1
        Task task = new Task();
        task.setName("Task A");

        when(taskService.findById(anyLong())).thenReturn(task);

        // Quando fazemos uma requisição GET para "/tasks/1"
        mockMvc.perform(get("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON))
                // Então o status HTTP deve ser 200 OK
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Task A"));
    }

    @Test
    public void testDelete() throws Exception {
        // Quando fazemos uma requisição DELETE para "/tasks/1"
        mockMvc.perform(delete("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON))
                // Então o status HTTP deve ser 204 No Content
                .andExpect(status().isNoContent());
    }
}
