package br.ubione.adDesafio.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.ubione.adDesafio.application.dto.ProjectRequestDTO;
import br.ubione.adDesafio.application.enums.ProjectStatus;
import br.ubione.adDesafio.application.validation.CustomerValidate;
import br.ubione.adDesafio.infraestructure.data.ProjectRepository;
import br.ubione.adDesafio.model.entities.Customer;
import br.ubione.adDesafio.model.entities.Project;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private CustomerValidate customerValidate;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa mocks
    }

    @Test
    public void testList() {
        // Dado que há projetos no repositório
        Project project = new Project("Projeto A", new Timestamp(System.currentTimeMillis()), null, null, new BigDecimal("1000.00"), new BigDecimal("0.00"), ProjectStatus.INICIADO, new Customer());
        when(projectRepository.findAll()).thenReturn(Arrays.asList(project));

        // Quando listamos os projetos
        List<Project> projects = projectService.list();

        // Debugging: Adicionando print para verificar
        System.out.println("Projects size: " + projects.size());  // Verifique se a lista está sendo preenchida corretamente.

        // Então o resultado deve ter o projeto esperado
        assertEquals(1, projects.size());
        assertEquals("Projeto A", projects.get(0).getName());
    }


    @Test
    public void testFindById() {
        // Dado que existe um projeto com ID 1
        Project project = new Project("Projeto A", new Timestamp(System.currentTimeMillis()), null, null, new BigDecimal("1000.00"), new BigDecimal("0.00"), ProjectStatus.INICIADO, new Customer());
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        // Quando procuramos por um projeto com ID 1
        Optional<Project> result = projectService.findById(1L);

        // Então o resultado deve ser o projeto esperado
        assertTrue(result.isPresent());
        assertEquals("Projeto A", result.get().getName());
    }

    @Test
    public void testFindByIdNotFound() {
        // Dado que o projeto não existe
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        // Quando procuramos por um projeto com ID 1
        Optional<Project> result = projectService.findById(1L);

        // Então o resultado deve ser vazio
        assertFalse(result.isPresent());
    }

    @Test
    public void testSave() {
        // Dados de entrada
        ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO();
        projectRequestDTO.setName("Novo Projeto");
        projectRequestDTO.setProjectStatus(ProjectStatus.INICIADO);
        projectRequestDTO.setCustomerId(1L);
        projectRequestDTO.setOrcamento(new BigDecimal("1000.00"));
        projectRequestDTO.setDtInicio(new Timestamp(System.currentTimeMillis()));

        Customer customer = new Customer();
        customer.setId(1L);

        Project project = new Project("Novo Projeto", new Timestamp(System.currentTimeMillis()), null, null, new BigDecimal("1000.00"), new BigDecimal("0.00"), ProjectStatus.INICIADO, customer);

        // Configura os mocks
        when(customerValidate.customerExists(1L)).thenReturn(customer);
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        // Quando salvamos o projeto
        Project savedProject = projectService.save(projectRequestDTO);

        // Então o projeto deve ser salvo corretamente
        assertNotNull(savedProject);
        assertEquals("Novo Projeto", savedProject.getName());
        assertEquals(new BigDecimal("1000.00"), savedProject.getOrcamento());
    }

    @Test
    public void testDelete() {
        // Configura o mock para deletar o projeto
        doNothing().when(projectRepository).deleteById(1L);

        // Quando deletamos um projeto com ID 1
        projectService.delete(1L);

        // Então o método deleteById do repositório deve ter sido chamado
        verify(projectRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetProjectsByFilters() {
        Project project = new Project("Projeto A", new Timestamp(System.currentTimeMillis()), null, null, new BigDecimal("1000.00"), new BigDecimal("0.00"), ProjectStatus.CONCLUIDO, new Customer());
        Page<Project> page = new PageImpl<>(Arrays.asList(project));

        // Ajustando o mock para usar eq() para o ProjectStatus
        when(projectRepository.findByFilters(anyString(), anyLong(), any(ProjectStatus.class), any(Pageable.class))).thenReturn(page);

        Page<Project> result = projectService.getProjectsByFilters("Projeto", 1L, ProjectStatus.CONCLUIDO, PageRequest.of(0, 10));

        assertNotNull(result, "O resultado não deve ser nulo");
        assertEquals(1, result.getTotalElements(), "Deve ter 1 elemento");
        assertEquals("Projeto A", result.getContent().get(0).getName(), "O nome do projeto deve ser 'Projeto A'");
    }
}
