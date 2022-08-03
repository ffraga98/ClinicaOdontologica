package com.ctd.ClinicaOdontologica.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DentistIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void test01getDentistWithIdThree() throws Exception {
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/dentist/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Integer getStatus = response.getResponse().getStatus();
        Integer expectedStatus = 200;
        Assertions.assertEquals( expectedStatus , getStatus);

        String dentist3 = response.getResponse().getContentAsString();
        String expected = "{\"id\":3,\"firstName\":\"Dentista\",\"lastName\":\"Test\",\"registration\":0}";
        Assertions.assertEquals(expected, dentist3);
    }

    @Test
    void test02AddNewDentist() throws Exception {
        String jsonPost = "{\n" +
                "    \"firstName\" : \"Dentista\",\n" +
                "    \"lastName\" : \"Test\",\n" +
                "    \"registration\" : 0\n" +
                "}";

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.post("/dentist")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonPost))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Integer postStatus = response.getResponse().getStatus();
        Integer expectedStatus = 200;
        Assertions.assertEquals( expectedStatus , postStatus);
    }

    @Test
    void test03DeletingDentistWithIdAndFindAllDentists() throws Exception {
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.delete("/dentist/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();


        Integer deleteStatus = response.getResponse().getStatus();
        Integer expectedStatus = 200;
        Assertions.assertEquals( expectedStatus , deleteStatus);
        
        response = this.mockMvc.perform(MockMvcRequestBuilders.get("/dentist/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertTrue(response.getResponse().getContentAsString().isEmpty());
        
        Integer getStatus = response.getResponse().getStatus();
        expectedStatus = 404;
        Assertions.assertEquals( expectedStatus , getStatus);
    }

    @Test
    void test04UpdatingDentistWithIdOneAndFindAllDentists() throws Exception {
        String jsonPost = "{\n" +
                "    \"id\" : \"1\",\n" +
                "    \"firstName\" : \"Dentista\",\n" +
                "    \"lastName\" : \"Test\",\n" +
                "    \"registration\" : 1\n" +
                "}";

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.put("/dentist/")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonPost))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Integer putStatus = response.getResponse().getStatus();
        Integer expectedStatus = 200;
        Assertions.assertEquals( expectedStatus , putStatus);
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

    @BeforeAll
    void beforeAllTestsAddThreeDentists() throws Exception {
        this.addDentist();
        this.addDentist();
        this.addDentist();
    }

}
