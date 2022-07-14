package com.ctd.ClinicaOdontologica.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class DentistIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void test01AddNewDentistAndFindAllDentist() throws Exception {
        this.addDentist();

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/dentist/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        String dentistString = response.getResponse().getContentAsString();
        String esperado = "[{\"id\":1,\"firstName\":\"Dentista\",\"lastName\":\"Test\",\"registration\":0}]";
        Assertions.assertEquals(esperado, dentistString);
    }

    @Test
    void test02AddingNewDentistAndFindAllDentists() throws Exception {
        this.addDentist();

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/dentist/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        String dentist1 = response.getResponse().getContentAsString();

        response = this.mockMvc.perform(MockMvcRequestBuilders.get("/dentist/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        String dentist2 = response.getResponse().getContentAsString();

        String esperado = "{\"id\":1,\"firstName\":\"Dentista\",\"lastName\":\"Test\",\"registration\":0}";
        Assertions.assertEquals(esperado, dentist1);

        esperado = "{\"id\":2,\"firstName\":\"Dentista\",\"lastName\":\"Test\",\"registration\":0}";
        Assertions.assertEquals(esperado, dentist2);
    }

    @Test
    void test03DeletingDentistWithIdOneAndFindAllDentists() throws Exception {
        this.addDentist();

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/dentist/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/dentist/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        String dentist1 = response.getResponse().getContentAsString();

        response = this.mockMvc.perform(MockMvcRequestBuilders.get("/dentist/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        String dentist3 = response.getResponse().getContentAsString();

        String esperado = "{\"id\":1,\"firstName\":\"Dentista\",\"lastName\":\"Test\",\"registration\":0}";
        Assertions.assertEquals(esperado, dentist1);

        esperado = "{\"id\":3,\"firstName\":\"Dentista\",\"lastName\":\"Test\",\"registration\":0}";
        Assertions.assertEquals(esperado, dentist3);
    }

    @Test
    void test04UpdatingDentistWithIdOneAndFindAllDentists() throws Exception {
        String jsonPost = "{\n" +
                "    \"id\" : \"1\",\n" +
                "    \"firstName\" : \"Dentista\",\n" +
                "    \"lastName\" : \"Test\",\n" +
                "    \"registration\" : 1\n" +
                "}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/dentist/")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonPost))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/dentist/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        String dentist1 = response.getResponse().getContentAsString();

        response = this.mockMvc.perform(MockMvcRequestBuilders.get("/dentist/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        String dentist3 = response.getResponse().getContentAsString();

        String esperado = "{\"id\":1,\"firstName\":\"Dentista\",\"lastName\":\"Test\",\"registration\":1}";
        Assertions.assertEquals(esperado, dentist1);

        esperado = "{\"id\":3,\"firstName\":\"Dentista\",\"lastName\":\"Test\",\"registration\":0}";
        Assertions.assertEquals(esperado, dentist3);
    }

    private void addDentist() throws Exception {
        String jsonPost = "{\n" +
                "    \"firstName\" : \"Dentista\",\n" +
                "    \"lastName\" : \"Test\",\n" +
                "    \"registration\" : 0\n" +
                "}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/dentist")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonPost))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }
}
