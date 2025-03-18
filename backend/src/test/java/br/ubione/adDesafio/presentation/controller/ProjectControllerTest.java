package br.ubione.adDesafio.presentation.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

import br.ubione.adDesafio.application.dto.ProjectRequestDTO;
import br.ubione.adDesafio.application.enums.ProjectStatus;
import br.ubione.adDesafio.application.services.ProjectService;
import br.ubione.adDesafio.model.entities.Customer;
import br.ubione.adDesafio.model.entities.Project;

@WebMvcTest(ProjectController.class) 
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService; 

    @Test
    public void testCreateProject() throws Exception {

        ProjectRequestDTO projectRequestDTO = new ProjectRequestDTO();
        projectRequestDTO.setName("Projeto A");
        projectRequestDTO.setProjectStatus(ProjectStatus.INICIADO);
        projectRequestDTO.setCustomerId(1L);
        projectRequestDTO.setOrcamento(new BigDecimal("1000.00"));


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);  
        Date tomorrow = calendar.getTime();  

        Timestamp dtInicio = new Timestamp(tomorrow.getTime());
        projectRequestDTO.setDtInicio(dtInicio);  

        Project project = new Project("Projeto A", dtInicio, null, null, new BigDecimal("1000.00"), 
                                      new BigDecimal("0.00"), ProjectStatus.INICIADO, new Customer());
        when(projectService.save(any(ProjectRequestDTO.class))).thenReturn(project);

        mockMvc.perform(post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(projectRequestDTO)))  
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Projeto A"))
                .andExpect(jsonPath("$.orcamento").value(1000.00));
    }


    @Test
    public void testGetProjectById() throws Exception {
        Project project = new Project("Projeto B", new Timestamp(System.currentTimeMillis()), null, null, new BigDecimal("1000.00"), new BigDecimal("0.00"), ProjectStatus.EM_ANDAMENTO, new Customer());

        when(projectService.findById(1L)).thenReturn(Optional.of(project));

        mockMvc.perform(get("/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Projeto B"));
    }

    @Test
    public void testGetProjectByIdNotFound() throws Exception {
        when(projectService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/projects/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteProject() throws Exception {
        doNothing().when(projectService).delete(1L);

        mockMvc.perform(delete("/projects/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetProjectsPaginated() throws Exception {
        Page<Project> projects = new PageImpl<>(List.of(new Project("Project 1", new Timestamp(System.currentTimeMillis()), null, null, new BigDecimal("1000.00"), new BigDecimal("0.00"), ProjectStatus.CONCLUIDO, new Customer())));

        when(projectService.getProjectsByFilters(anyString(), anyLong(), any(), any(Pageable.class))).thenReturn(projects);

        mockMvc.perform(get("/projects/paginado")
                .param("name", "Project 1")
                .param("customerId", "1")
                .param("projectStatus", "CONCLUIDO")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Project 1"));
    }
}
