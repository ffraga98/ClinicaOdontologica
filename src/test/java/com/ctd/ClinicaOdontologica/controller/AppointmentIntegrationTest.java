package com.ctd.ClinicaOdontologica.controller;

import org.aspectj.lang.annotation.Before;
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
@TestMethodOrder(MethodOrderer.MethodName.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AppointmentIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void test01createNewAppointment() throws Exception {


        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/dentist/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        String dentistString = response.getResponse().getContentAsString();
        Assertions.assertFalse(dentistString.isEmpty());

        response = this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        String patientString = response.getResponse().getContentAsString();
        Assertions.assertFalse(patientString.isEmpty());


        this.addAppointment(1L,1L);

        response = this.mockMvc.perform(MockMvcRequestBuilders.get("/appointment")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().equals("[]"));


        String expected = "[{\"id\":1," +
                "\"dentist\":"+ dentistString +
                ",\"patient\":" + patientString +
                ",\"dateTime\":\"2022-07-15@19:50:12\"}]";
        Assertions.assertEquals( expected, response.getResponse().getContentAsString());
    }

    @Test
    void test02addSecondAppointmentSameDoctorNewPatient() throws Exception {
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/dentist/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        String dentistString = response.getResponse().getContentAsString();
        Assertions.assertFalse(dentistString.isEmpty());

        response = this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        String patientString = response.getResponse().getContentAsString();
        Assertions.assertFalse(patientString.isEmpty());


        this.addAppointment(1L,3L);

        response = this.mockMvc.perform(MockMvcRequestBuilders.get("/appointment/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().equals(""));


        String expected = "{\"id\":2," +
                "\"dentist\":"+ dentistString +
                ",\"patient\":" + patientString +
                ",\"dateTime\":\"2022-07-15@19:50:12\"}";
        Assertions.assertEquals( expected, response.getResponse().getContentAsString());
    }

    @Test
    void test03UpdateAppointment() throws Exception {

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/dentist/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        String dentistString = response.getResponse().getContentAsString();
        Assertions.assertFalse(dentistString.isEmpty());

        response = this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        String patientString = response.getResponse().getContentAsString();
        Assertions.assertFalse(patientString.isEmpty());

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

        response = this.mockMvc.perform(MockMvcRequestBuilders.put("/appointment/")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonPost))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        response = this.mockMvc.perform(MockMvcRequestBuilders.get("/appointment/1")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonPost))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().equals(""));


        String expected = "{\"id\":1," +
                "\"dentist\":"+ dentistString +
                ",\"patient\":" + patientString +
                ",\"dateTime\":\"2022-07-20@13:30:00\"}";
        Assertions.assertEquals( expected, response.getResponse().getContentAsString());
    }

    @Test
    void test04DeleteAnAppointment() throws Exception {
        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.delete("/appointment/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        response = this.mockMvc.perform(MockMvcRequestBuilders.get("/dentist/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        String dentistString = response.getResponse().getContentAsString();
        Assertions.assertFalse(dentistString.isEmpty());

        response = this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        String patientString = response.getResponse().getContentAsString();
        Assertions.assertFalse(patientString.isEmpty());


        response = this.mockMvc.perform(MockMvcRequestBuilders.get("/appointment")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().equals("[]"));

        String expected = "[{\"id\":1," +
                "\"dentist\":"+ dentistString +
                ",\"patient\":" + patientString +
                ",\"dateTime\":\"2022-07-20@13:30:00\"}]";
        Assertions.assertEquals( expected, response.getResponse().getContentAsString());
    }


    private void addDentist(Integer registration) throws Exception {
        String jsonPost = "{\n" +
                "    \"firstName\" : \"Dentista\",\n" +
                "    \"lastName\" : \"Test\",\n" +
                "    \"registration\" : "+ registration +"\n" +
                "}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/dentist")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonPost))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }
    private void addPatient(String DNI) throws Exception {
        String jsonPost = "{\n"
                + "\"firstName\" : \"Paciente\",\n"
                + "\"lastName\" : \"Test\",\n"
                + "\"DNI\" : "+ DNI +",\n"
                + "\"registrationDate\" : \"2022-02-01\",\n"
                + "\"home\" : {\n"
                + "\"street\" : \"Calle\",\n"
                + "\"number\" : 123,\n"
                + "\"location\" : \"Localidad\",\n"
                + "\"province\" : \"Provincia\"\n"
                + "}\n"
                + "}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/patient")
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
                "\t\"dateTime\": \"2022-07-15T19:50:12\"\n" +
                "}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/appointment")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonPost))
                .andDo(MockMvcResultHandlers.print()).andReturn();

    }


    @BeforeAll
    void beforeAllTest() throws Exception {
        this.addDentist(1);
        this.addDentist(2);
        this.addPatient("1111111");
        this.addPatient("222222 ");
        this.addPatient("333333 ");
    }
}