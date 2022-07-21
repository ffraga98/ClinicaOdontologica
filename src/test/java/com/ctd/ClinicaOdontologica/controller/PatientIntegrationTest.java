package com.ctd.ClinicaOdontologica.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
public class PatientIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void test01AddNewPatientAndFindAllPatients() throws Exception {
        String patient1 = this.addPatient();

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        String patientString = response.getResponse().getContentAsString();
        String esperado = "[{\"id\":1,\"firstName\":\"Paciente\",\"lastName\":\"Test\","
                + "\"dni\":\"42142342\",\"registrationDate\":\"2022-02-01\","
                + "\"home\":{\"id\":1,\"street\":\"Calle\",\"number\":123,\"location\":\"Localidad\",\"province\":\"Provincia\"}"
                + "}]";
        Assertions.assertEquals(esperado, patientString);
    }

    @Test
    void test02AddNewPatientWithAnExistingResidence() throws Exception {
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

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        String patient2 = response.getResponse().getContentAsString();

        response = this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        String patient1 = response.getResponse().getContentAsString();


        String esperado = "{\"id\":1,\"firstName\":\"Paciente\",\"lastName\":\"Test\","
                + "\"dni\":\"42142342\",\"registrationDate\":\"2022-02-01\","
                + "\"home\":{\"id\":1,\"street\":\"Calle\",\"number\":123,\"location\":\"Localidad\","
                + "\"province\":\"Provincia\"}"
                + "}";
        Assertions.assertEquals(esperado, patient1);

        esperado = "{\"id\":2,\"firstName\":\"Paciente\",\"lastName\":\"Test\","
                + "\"dni\":\"42142342\",\"registrationDate\":\"2022-02-01\","
                + "\"home\":{\"id\":1,\"street\":\"Calle\",\"number\":123,\"location\":\"Localidad\","
                + "\"province\":\"Provincia\"}"
                + "}";
        Assertions.assertEquals(esperado, patient2);
    }

    @Test
    void test03AddingNewPatientAndFindAllPatients() throws Exception {
        String patient3 = this.addPatient();

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        String patient1 = response.getResponse().getContentAsString();

        response = this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        String patient2 = response.getResponse().getContentAsString();


        String esperado = "{\"id\":1,\"firstName\":\"Paciente\",\"lastName\":\"Test\","
                + "\"dni\":\"42142342\",\"registrationDate\":\"2022-02-01\","
                + "\"home\":{\"id\":1,\"street\":\"Calle\",\"number\":123,\"location\":\"Localidad\",\"province\":\"Provincia\"}"
                + "}";
        Assertions.assertEquals(esperado, patient1);

        esperado = "{\"id\":2,\"firstName\":\"Paciente\",\"lastName\":\"Test\","
                + "\"dni\":\"42142342\",\"registrationDate\":\"2022-02-01\","
                + "\"home\":{\"id\":1,\"street\":\"Calle\",\"number\":123,\"location\":\"Localidad\",\"province\":\"Provincia\"}"
                + "}";
        Assertions.assertEquals(esperado, patient2);

        esperado = "{\"id\":3,\"firstName\":\"Paciente\",\"lastName\":\"Test\","
                + "\"dni\":\"42142342\",\"registrationDate\":\"2022-02-01\","
                + "\"home\":{\"id\":2,\"street\":\"Calle\",\"number\":123,\"location\":\"Localidad\",\"province\":\"Provincia\"}"
                + "}";
        Assertions.assertEquals(esperado, patient3);

    }

    @Test
    void test04DeletingPatientWithIdOneAndFindAllPatients() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/patient/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        String patient1 = response.getResponse().getContentAsString();

        response = this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        String patient3 = response.getResponse().getContentAsString();


        String esperado = "{\"id\":1,\"firstName\":\"Paciente\",\"lastName\":\"Test\","
                + "\"dni\":\"42142342\",\"registrationDate\":\"2022-02-01\","
                + "\"home\":{\"id\":1,\"street\":\"Calle\",\"number\":123,\"location\":\"Localidad\","
                + "\"province\":\"Provincia\"}"
                + "}";
        Assertions.assertEquals(esperado, patient1);

        esperado = "{\"id\":3,\"firstName\":\"Paciente\",\"lastName\":\"Test\","
                + "\"dni\":\"42142342\",\"registrationDate\":\"2022-02-01\","
                + "\"home\":{\"id\":2,\"street\":\"Calle\",\"number\":123,\"location\":\"Localidad\","
                + "\"province\":\"Provincia\"}"
                + "}";
        Assertions.assertEquals(esperado, patient3);
    }

    @Test
    void test05UpdatingPatientWithIdOneAndFindAllPatients() throws Exception {
        String jsonPost = "{\n" +
                "    \"id\" : 1,\n" +
                "    \"firstName\" : \"Paciente\",\n" +
                "    \"lastName\" : \"Test\",\n" +
                "    \"dni\" : \"123456789\",\n" +
                "    \"registrationDate\" : 1\n" +
                "    \"home\" : {\n" +
                "       \"id\" : 1\n" +
                "    }\n" +
                "}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/patient/")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonPost))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        String patient1 = response.getResponse().getContentAsString();

        response = this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        String patient3 = response.getResponse().getContentAsString();

        String esperado = "{\"id\":1,\"firstName\":\"Paciente\",\"lastName\":\"Test\",\"dni\":\"42142342\",\"registrationDate\":\"2022-02-01\",\"home\":{\"id\":1,\"street\":\"Calle\",\"number\":123,\"location\":\"Localidad\",\"province\":\"Provincia\"}}";
        Assertions.assertEquals(esperado, patient1);

        esperado = "{\"id\":3,\"firstName\":\"Paciente\",\"lastName\":\"Test\",\"dni\":\"42142342\",\"registrationDate\":\"2022-02-01\",\"home\":{\"id\":2,\"street\":\"Calle\",\"number\":123,\"location\":\"Localidad\",\"province\":\"Provincia\"}}";
        Assertions.assertEquals(esperado, patient3);
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

    
}