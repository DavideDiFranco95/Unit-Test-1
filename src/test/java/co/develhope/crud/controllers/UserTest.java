package co.develhope.crud.controllers;

import co.develhope.crud.entities.User;
import co.develhope.crud.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
class UserTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserController userController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void checkEntity(){
        assertThat(userController).isNotNull();
    }


    @Test
    void createUser() throws Exception {
        User user = new User(1L,"Aldo","Baglio",50,"via fatti i ca tuoi");
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getUser()  throws Exception{
        mockMvc.perform(get("/users/1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getAllUsers() throws Exception {
        mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void updateUser() throws Exception {
        createUser();
        User user = new User();
        user.setName("Aldox");
        user.setSurname("Bagliox");
        user.setAge(30);
        user.setAddress("SperoCheFungi");
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteById() throws Exception {
        this.mockMvc.perform(delete("/users/1")).andDo(print()).andExpect(status().isOk()).andReturn();
    }
}