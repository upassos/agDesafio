package br.ubione.adDesafio.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.ubione.adDesafio.application.dto.TaskRequestDTO;
import br.ubione.adDesafio.application.validation.ProjectValidade;
import br.ubione.adDesafio.infraestructure.data.TaskRepository;
import br.ubione.adDesafio.model.entities.Project;
import br.ubione.adDesafio.model.entities.Task;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectValidade projectValidade;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        taskService = new TaskService(taskRepository, projectValidade);
    }

    @Test
    public void testListWithFilters() {
        Task task = new Task();
        task.setName("Task A");
        Page<Task> page = new PageImpl<>(Arrays.asList(task));

        when(taskRepository.findByFilters(any(), anyLong(), any(), any(), any(Pageable.class))).thenReturn(page);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Task> tasks = taskService.list("Task A", 1L, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), pageable);

        assertEquals(1, tasks.getContent().size());
        assertEquals("Task A", tasks.getContent().get(0).getName());
    }

    @Test
    public void testListAll() {
        Task task = new Task();
        task.setName("Task A");
        List<Task> tasks = Arrays.asList(task);

        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.list();

        assertEquals(1, result.size());
        assertEquals("Task A", result.get(0).getName());
    }

    @Test
    public void testSave() {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO();
        taskRequestDTO.setName("New Task");
        taskRequestDTO.setProjectId(1L);

        Project project = new Project();
        project.setId(1L);

        when(projectValidade.projectExists(anyLong())).thenReturn(project);

        Task task = new Task();
        task.setName("New Task");

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task savedTask = taskService.save(taskRequestDTO);

        assertNotNull(savedTask);
        assertEquals("New Task", savedTask.getName());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void testFindById() {
        Task task = new Task();
        task.setName("Task A");

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));

        Task result = taskService.findById(1L);

        assertNotNull(result);
        assertEquals("Task A", result.getName());
    }

    @Test
    public void testFindByIdThrowsException() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> taskService.findById(1L));

        assertEquals("Atividade não encontrada", exception.getMessage());
    }

    @Test
    public void testDelete() {
        Task task = new Task();
        task.setName("Task A");

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));

        taskService.delete(1L);

        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    public void testFindTasksByProjectId() {
        Task task = new Task();
        task.setName("Task A");

        when(taskRepository.findByProjectId(anyLong())).thenReturn(Set.of(task));

        Set<Task> result = taskService.findTasksByProjectId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Task A", result.iterator().next().getName());
    }
}
