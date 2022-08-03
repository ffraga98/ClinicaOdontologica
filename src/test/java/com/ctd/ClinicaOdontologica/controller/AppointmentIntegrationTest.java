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

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AppointmentIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void test01getAppoinmentWithId1() throws Exception {
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Integer getStatus = response.getResponse().getStatus();
        Integer expectedStatus = 200;
        Assertions.assertEquals( expectedStatus , getStatus);

        Assertions.assertFalse(response.getResponse().getContentAsString().equals(""));

        String expected = "{\"id\":1," +
                "\"dentist\":"+ getDentistJson(1L) +
                ",\"patient\":" + getPatientJson(2L) +
                ",\"dateTime\":\"2022-07-15@19:50:12\"}";

        Assertions.assertEquals( expected, response.getResponse().getContentAsString());
    }
    @Test
    void test02createNewAppointment() throws Exception {
        String jsonPost = "{\n" +
                "\t\"dentist\": {\n" +
                "\t\t\"id\": " + 1L +"\n" +
                "\t},\n" +
                "\t\"patient\": {\n" +
                "\t\t\"id\": "+ 1L +"\n" +
                "\t},\n" +
                "\t\"dateTime\": \"2022-07-15T19:50:12.123\"\n" +
                "}";

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.post("/appointment")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonPost))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Integer postStatus = response.getResponse().getStatus();
        Integer expectedStatus = 200;
        Assertions.assertEquals( expectedStatus , postStatus);
    }

    @Test
    void test03UpdateAppointment() throws Exception {
        String jsonPost = "{\n" +
                "\t\t\"id\": 1,\n" +
                "\t\"dentist\": {\n" +
                "\t\t\"id\": 1\n" +
                "\t},\n" +
                "\t\"patient\": {\n" +
                "\t\t\"id\": 1\n" +
                "\t},\n" +
                "\t\"dateTime\": \"2022-07-20T13:30:00\"\n" +
                "}";

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.put("/appointment/")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonPost))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Integer putStatus = response.getResponse().getStatus();
        Integer expectedStatus = 200;
        Assertions.assertEquals( expectedStatus , putStatus);

        String result = getAppointment(1L);
        String expected = "{\"id\":1," +
                "\"dentist\":" + getDentistJson(1L) +
                ",\"patient\":" + getPatientJson(1L) +
                ",\"dateTime\":\"2022-07-20@13:30:00\"}";

        Assertions.assertEquals( expected, result);
    }

    @Test
    void test04DeleteAnAppointment() throws Exception {
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.delete("/appointment/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Integer deleteStatus = response.getResponse().getStatus();
        Integer expectedStatus = 200;
        Assertions.assertEquals( expectedStatus , deleteStatus);

        response = this.mockMvc.perform(MockMvcRequestBuilders.get("/appointment/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Integer getStatus = response.getResponse().getStatus();
        expectedStatus = 404;
        Assertions.assertEquals( expectedStatus , getStatus);

    }

    private void addDentist(Integer registration) throws Exception {
        String jsonPost = "{\n" +
                "    \"firstName\" : \"Dentista\",\n" +
                "    \"lastName\" : \"Test\",\n" +
                "    \"registration\" : "+ registration +"\n" +
                "}";

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.post("/dentist")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonPost))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }
    private void addPatient(String DNI) throws Exception {
        String jsonPost = "{\n"
                + "\"firstName\" : \"Paciente\",\n"
                + "\"lastName\" : \"Test\",\n"
                + "\"dni\" : "+ DNI +",\n"
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
    }

    private void addAppointment(Long idDentist, Long idPatient) throws Exception {
        String jsonPost = "{\n" +
                "\t\"dentist\": {\n" +
                "\t\t\"id\": " + idDentist +"\n" +
                "\t},\n" +
                "\t\"patient\": {\n" +
                "\t\t\"id\": "+ idPatient +"\n" +
                "\t},\n" +
                "\t\"dateTime\": \"2022-07-15T19:50:12.121\"\n" +
                "}";

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.post("/appointment")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonPost))
                .andDo(MockMvcResultHandlers.print()).andReturn();

    }

    private String getDentistJson(Long id) throws Exception {
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/dentist/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        return response.getResponse().getContentAsString();
    }

    private String getPatientJson(Long id) throws Exception {
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/"+id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        return response.getResponse().getContentAsString();
    }

    private String getAppointment(Long id) throws Exception {
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/appointment/"+ id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        return response.getResponse().getContentAsString();
    }

    @BeforeAll
    void beforeAllTest() throws Exception {
        this.addDentist(1);
        this.addDentist(2);
        this.addPatient("1111111");
        this.addPatient("222222 ");

        this.addAppointment(1L,2L);
        this.addAppointment(2L,1L);
    }
}