package com.thefitnation.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringRunner.class)
public class UserResourceTest {
    private MockMvc mockMvc;

    @Autowired
    private UserResource userResource;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userResource).build();
    }

    @Test
    public void getAllUsers() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void findOneUserById() throws Exception {

    }

    @Test
    public void createUser() throws Exception {

    }

    @Test
    public void updateUser() throws Exception {

    }

    @Test
    public void deleteUser() throws Exception {

    }

}