package com.ctd.ClinicaOdontologica.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PatientIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void test01GetPatientWithIdOne() throws Exception{
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Integer getStatus = response.getResponse().getStatus();
        Integer expectedStatus = 200;
        Assertions.assertEquals( expectedStatus , getStatus);

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        String patient1 = response.getResponse().getContentAsString();
        String esperado = "{\"id\":3,\"firstName\":\"Paciente\",\"lastName\":\"Test\","
                + "\"dni\":\"42142342\",\"registrationDate\":\"2022-02-01\","
                + "\"home\":{\"id\":3,\"street\":\"Calle\",\"number\":123,\"location\":\"Localidad\","
                + "\"province\":\"Provincia\"}"
                + "}";
        Assertions.assertEquals(esperado, patient1);
    }

    @Test
    void test02FindAllPatients() throws Exception {
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/patient")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Integer getStatus = response.getResponse().getStatus();
        Integer expectedStatus = 200;
        Assertions.assertEquals( expectedStatus , getStatus);

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }
    @Test
    void test03AddNewPatient() throws Exception {
        String jsonPost = "{\n"
                + "\"firstName\" : \"Paciente\",\n"
                + "\"lastName\" : \"Test\",\n"
                + "\"dni\" : \"42142342\",\n"
                + "\"registrationDate\" : \"2022-02-01\",\n"
                + "\"home\" : {\n"
                + "\"street\" : \"Calle\",\n"
                + "\"number\" : 123,\n"
                + "\"location\" : \"Localidad\",\n"
                + "\"province\" : \"Provincia\"\n"
                + "}\n"
                + "}";

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.post("/patient")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonPost))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Integer postStatus = response.getResponse().getStatus();
        Integer expectedStatus = 200;
        Assertions.assertEquals( expectedStatus , postStatus);
    }
    @Test
    void test04AddNewPatientWithAnExistingResidence() throws Exception {
        String jsonPost = "{\n"
                            + "\"firstName\" : \"Paciente\",\n"
                            + "\"lastName\" : \"Test\",\n"
                            + "\"dni\" : \"42142342\",\n"
                            + "\"registrationDate\" : \"2022-02-01\",\n"
                            + "\"home\" : {\n"
                                    + "\"id\" : 1\n"
                            + "}\n"
                         + "}";

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.post("/patient")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonPost))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Integer postStatus = response.getResponse().getStatus();
        Integer expectedStatus = 200;
        Assertions.assertEquals( expectedStatus , postStatus);
    }

    @Test
    void test05DeletingPatientWithIdOneAndFindAllPatients() throws Exception {
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.delete("/patient/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Integer deleteStatus = response.getResponse().getStatus();
        Integer expectedStatus = 200;
        Assertions.assertEquals( expectedStatus , deleteStatus);

        response = this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertTrue(response.getResponse().getContentAsString().isEmpty());

        Integer getStatus = response.getResponse().getStatus();
        expectedStatus = 404;
        Assertions.assertEquals( expectedStatus , getStatus);
    }

    @Test
    void test05UpdatingPatientWithIdOne() throws Exception {
        String jsonPost = "{\n"
                + "\"id\" : 1,\n"
                + "\"firstName\" : \"Paciente1\",\n"
                + "\"lastName\" : \"Test1\",\n"
                + "\"dni\" : \"123\",\n"
                + "\"registrationDate\" : \"2022-02-10\",\n"
                + "\"home\" : {\n"
                + "}\n"
                + "}";

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.put("/patient/")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonPost))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Integer putStatus = response.getResponse().getStatus();
        Integer expectedStatus = 200;
        Assertions.assertEquals( expectedStatus , putStatus);
    }

    private String addPatient() throws Exception {
            String jsonPost = "{\n"
                    + "\"firstName\" : \"Paciente\",\n"
                    + "\"lastName\" : \"Test\",\n"
                    + "\"dni\" : \"42142342\",\n"
                    + "\"registrationDate\" : \"2022-02-01\",\n"
                    + "\"home\" : {\n"
                            + "\"street\" : \"Calle\",\n"
                            + "\"number\" : 123,\n"
                            + "\"location\" : \"Localidad\",\n"
                            + "\"province\" : \"Provincia\"\n"
                            + "}\n"
                    + "}";

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.post("/patient")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonPost))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        return response.getResponse().getContentAsString();
    }

    @BeforeAll
    void beforeAllTestsAddThreePatients() throws Exception {
        this.addPatient();
        this.addPatient();
        this.addPatient();
    }
}