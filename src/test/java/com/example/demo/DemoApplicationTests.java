package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class DemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    private String taskId;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void setup_vars() {
        Dotenv dotenv = Dotenv.configure().directory("./").load();
        System.setProperty("spring.datasource.url", dotenv.get("ORACLE_DB_URL"));
        System.setProperty("spring.datasource.username", dotenv.get("ORACLE_DB_USERNAME"));
        System.setProperty("spring.datasource.password", dotenv.get("ORACLE_DB_PASSWORD"));
    }

    @BeforeEach
    public void setup() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Dummy Task\", \"description\":\"This is a dummy task\", \"completed\":false}"))
                .andExpect(status().isCreated())
                .andReturn();
    
        String responseString = result.getResponse().getContentAsString();
        JsonNode rootNode = objectMapper.readTree(responseString);
        taskId = rootNode.path("id").asText();
    

        System.out.println("Task ID captured: " + taskId);
    
        if (taskId == null || taskId.equals("null")) {
            throw new IllegalStateException("Task ID was not captured correctly.");
        }
    }
    
    @AfterEach
    public void cleanup() throws Exception {
        if (taskId != null && !taskId.equals("null")) {
            mockMvc.perform(delete("/api/tasks/" + taskId))
                .andExpect(status().isOk());
        } else {
            System.out.println("Skipping cleanup because taskId is null");
        }
    }

    @Test
    public void getAllTasks() throws Exception {
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getTaskById() throws Exception {
        mockMvc.perform(get("/api/tasks/" + taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(Integer.parseInt(taskId))))
                .andExpect(jsonPath("$.title", is("Dummy Task")))
                .andExpect(jsonPath("$.description", is("This is a dummy task")))
                .andExpect(jsonPath("$.completed", is(false)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createTask() throws Exception {
        String newTaskJson = "{\"title\":\"New Task\", \"description\":\"New task description\", \"completed\":false}";
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newTaskJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("New Task")));
    }

    @Test
    public void updateTask() throws Exception {
        String updatedTaskJson = "{\"title\":\"Updated Task\", \"description\":\"Updated description\", \"completed\":true}";
        mockMvc.perform(put("/api/tasks/" + taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedTaskJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Task")));
    }

    @Test
    public void deleteTask() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Task to Delete\", \"description\":\"Delete this task\", \"completed\":false}"))
                .andExpect(status().isCreated())
                .andReturn();
    
        String responseString = result.getResponse().getContentAsString();
        JsonNode rootNode = objectMapper.readTree(responseString);
        String idToDelete = rootNode.path("id").asText();
    
        mockMvc.perform(delete("/api/tasks/" + idToDelete))
                .andExpect(status().isOk());
    }
}