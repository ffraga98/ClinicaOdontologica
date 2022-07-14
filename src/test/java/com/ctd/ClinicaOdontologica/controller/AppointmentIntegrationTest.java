package com.ctd.ClinicaOdontologica.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
class AppointmentIntegrationTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    void createNewDentistNewPatientAndNewAppointmentAndThenGetTheResults() throws Exception {
        String jsonPost = "{\n" +
                "    \"firstName\" : \"bebito\",\n" +
                "    \"lastName\" : \"fiufiu\",\n" +
                "    \"registration\" : 3462\n" +
                "}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/dentist")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonPost))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/dentist/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
        String dentistString = response.getResponse().getContentAsString();
        jsonPost = "{\n" +
                "\t\"firstName\": \"christian\",\n" +
                "\t\"lastName\": \"sancho\",\n" +
                "\t\"DNI\": \"102369\",\n" +
                "\t\"home\": {\n" +
                "\t\t\"street\": \"backend\",\n" +
                "\t\t\"number\": 1,\n" +
                "\t\t\"location\": \"argentina\",\n" +
                "\t\t\"province\": \"bsas\"\n" +
                "\t}\n" +
                "}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/patient")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonPost))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        String registrationDate = LocalDate.now().toString();

        response = this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        String patientString = response.getResponse().getContentAsString();
        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());

        jsonPost = "{\n" +
                "\t\"dentist\": {\n" +
                "\t\t\"id\": \"1\"\n" +
                "\t},\n" +
                "\t\"patient\": {\n" +
                "\t\t\"id\": \"3\"\n" +
                "\t}\n" +
                "}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/appointment")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonPost))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        response = this.mockMvc.perform(MockMvcRequestBuilders.get("/appointment")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().equals("[]"));


        String expected = "[{\"id\":4,\"dentist\":"+ dentistString +",\"patient\":"+ patientString +"}]";
        Assertions.assertEquals( expected, response.getResponse().getContentAsString());
    }
}