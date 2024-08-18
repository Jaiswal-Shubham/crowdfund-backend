package com.jaisshu.crowdfund.service;

import com.jaisshu.crowdfund.dto.ProjectDTO;
import com.jaisshu.crowdfund.entity.Project;
import com.jaisshu.crowdfund.entity.ProjectStatus;
import com.jaisshu.crowdfund.entity.User;
import com.jaisshu.crowdfund.repository.ProjectRepository;
import com.jaisshu.crowdfund.repository.UserRepository;
import com.jaisshu.crowdfund.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private User innovator;
    private ProjectDTO projectDTO;
    private Project project;

    @BeforeEach
    public void setUp() {
        innovator = User.builder()
                .userId(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .build();

        projectDTO = ProjectDTO.builder()
                .title("Innovative Project")
                .description("This is a very innovative project.")
                .requestedAmount(BigDecimal.valueOf(1000))
                .innovatorId(innovator.getUserId())
                .build();

        project = Project.builder()
                .id(1L)
                .title("Innovative Project")
                .description("This is a very innovative project.")
                .requestedAmount(BigDecimal.valueOf(1000))
                .currentFunding(BigDecimal.ZERO)
                .status(ProjectStatus.INACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .innovator(innovator)
                .build();
    }

    @Test
    public void testCreateProject_Success() {
        when(userRepository.findByUserId(innovator.getUserId())).thenReturn(Optional.of(innovator));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project createdProject = projectService.createProject(projectDTO);

        assertNotNull(createdProject);
        assertEquals(project.getTitle(), createdProject.getTitle());
        assertEquals(project.getDescription(), createdProject.getDescription());
        assertEquals(project.getRequestedAmount(), createdProject.getRequestedAmount());

        verify(userRepository, times(1)).findByUserId(innovator.getUserId());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    public void testCreateProject_InnovatorNotFound() {
        when(userRepository.findByUserId(innovator.getUserId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.createProject(projectDTO);
        });

        assertEquals("Innovator not found", exception.getMessage());

        verify(userRepository, times(1)).findByUserId(innovator.getUserId());
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    public void testGetAllActiveProjects() {
        when(projectRepository.findByStatus(ProjectStatus.ACTIVE)).thenReturn(List.of(project));

        List<ProjectDTO> activeProjects = projectService.getAllActiveProjects();

        assertNotNull(activeProjects);
        assertEquals(1, activeProjects.size());
        assertEquals(project.getTitle(), activeProjects.get(0).getTitle());

        verify(projectRepository, times(1)).findByStatus(ProjectStatus.ACTIVE);
    }

    @Test
    public void testGetProjectsByInnovator_Success() {
        when(userRepository.findByUserId(innovator.getUserId())).thenReturn(Optional.of(innovator));
        when(projectRepository.findByInnovator(innovator)).thenReturn(List.of(project));

        List<Project> projectsByInnovator = projectService.getProjectsByInnovator(innovator.getUserId());

        assertNotNull(projectsByInnovator);
        assertEquals(1, projectsByInnovator.size());
        assertEquals(project.getTitle(), projectsByInnovator.get(0).getTitle());

        verify(userRepository, times(1)).findByUserId(innovator.getUserId());
        verify(projectRepository, times(1)).findByInnovator(innovator);
    }

    @Test
    public void testGetProjectsByInnovator_InnovatorNotFound() {
        when(userRepository.findByUserId(innovator.getUserId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.getProjectsByInnovator(innovator.getUserId());
        });

        assertEquals("Innovator not found", exception.getMessage());

        verify(userRepository, times(1)).findByUserId(innovator.getUserId());
        verify(projectRepository, never()).findByInnovator(any(User.class));
    }

    @Test
    public void testFindById() {
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));

        Optional<Project> foundProject = projectService.findById(project.getId());

        assertTrue(foundProject.isPresent());
        assertEquals(project.getTitle(), foundProject.get().getTitle());

        verify(projectRepository, times(1)).findById(project.getId());
    }

    @Test
    public void testArchiveProject() {
        projectService.archiveProject(project);

        assertEquals(ProjectStatus.ARCHIVED, project.getStatus());
        verify(projectRepository, never()).save(any(Project.class)); // Assuming you don't save during archive in this method
    }
}