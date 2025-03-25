package br.ubione.agDesafio.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.ubione.agDesafio.application.dto.ProjectRequestDTO;
import br.ubione.agDesafio.application.enums.ProjectStatus;
import br.ubione.agDesafio.application.service.ProjectService;
import br.ubione.agDesafio.application.validation.CustomerValidate;
import br.ubione.agDesafio.domain.model.Customer;
import br.ubione.agDesafio.domain.model.Project;
import br.ubione.agDesafio.domain.repository.ProjectRepository;
import br.ubione.agDesafio.presentation.exception.NoDataException;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private CustomerValidate customerValidate;

    @InjectMocks
    private ProjectService projectService;

    @Test
    public void testList() {
        Project project = new Project("Projeto A", new Timestamp(System.currentTimeMillis()), null, null, new BigDecimal("1000.00"), new BigDecimal("0.00"), ProjectStatus.INICIADO, new Customer());
        when(projectRepository.findAll()).thenReturn(Arrays.asList(project));

        List<Project> projects = projectService.list();
        assertEquals(1, projects.size());
        assertEquals("Projeto A", projects.get(0).getName());
    }

    @Test
    void testFindByIdThrowsException() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoDataException.class, () -> projectService.findById(1L));
    }

    @Test
    void testFindByIdNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());
        
        Exception exception = assertThrows(RuntimeException.class, () -> {
            projectService.findById(1L);
        });

        assertEquals("Projeto não encontrado", exception.getMessage());
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    public void testSave() {
        ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO();
        projectRequestDTO.setName("Novo Projeto");
        projectRequestDTO.setCustomerId(1L);
        projectRequestDTO.setOrcamento(new BigDecimal("1000.00"));
        projectRequestDTO.setDtInicio(new Timestamp(System.currentTimeMillis()));
        projectRequestDTO.setProjectStatus(ProjectStatus.INICIADO);

        Customer customer = new Customer();
        customer.setId(1L);
        
        Project project = new Project("Novo Projeto", new Timestamp(System.currentTimeMillis()), null, null, 
                                      new BigDecimal("1000.00"), new BigDecimal("0.00"), 
                                      ProjectStatus.INICIADO, customer);

        when(customerValidate.customerExists(1L)).thenReturn(customer);
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project savedProject = projectService.save(projectRequestDTO);

        assertNotNull(savedProject);
        assertEquals("Novo Projeto", savedProject.getName());
        assertEquals(new BigDecimal("1000.00"), savedProject.getOrcamento());
    }

    @Test
    public void testDelete() {
        Project project = new Project("Projeto A", new Timestamp(System.currentTimeMillis()), null, null, new BigDecimal("1000.00"), new BigDecimal("0.00"), ProjectStatus.INICIADO, new Customer());
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        doNothing().when(projectRepository).deleteById(1L);
        projectService.delete(1L);
        verify(projectRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetProjectsByFilters() {
        Project project = new Project("Projeto A", new Timestamp(System.currentTimeMillis()), null, null, new BigDecimal("1000.00"), new BigDecimal("0.00"), ProjectStatus.CONCLUIDO, new Customer());
        Page<Project> page = new PageImpl<>(Arrays.asList(project));

        when(projectRepository.findByFilters(anyString(), anyLong(), any(ProjectStatus.class), any(Pageable.class))).thenReturn(page);
        Page<Project> result = projectService.getProjectsByFilters("Projeto", 1L, ProjectStatus.CONCLUIDO, PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Projeto A", result.getContent().get(0).getName());
    }
}
