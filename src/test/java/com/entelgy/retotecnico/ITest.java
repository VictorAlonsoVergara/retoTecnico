package com.entelgy.retotecnico;

import com.entelgy.retotecnico.controller.PersonController;
import com.entelgy.retotecnico.service.PersonService;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ITest {
    private static final String EXPECTED_STRING = "{\"data\":[\"1|Bluth|george.bluth@reqres.in\",\"2|Weaver|janet.weaver@reqres.in\",\"3|Wong|emma.wong@reqres.in\",\"4|Holt|eve.holt@reqres.in\",\"5|Morris|charles.morris@reqres.in\",\"6|Ramos|tracey.ramos@reqres.in\"]}";

    @Autowired
    private PersonController personController;

    @Mock
    private PersonService personService;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test //Prueba del servicio post que envia un json con una cadena reestructurada
    public void testRestructure() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/restructure")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk()).andDo(print())
                        .andReturn();
        String data = result.getResponse().getContentAsString();
        System.out.println(data);
        Assert.assertEquals(EXPECTED_STRING,data);
    }

    @Test
    public void contextLoads() throws Exception {
        assertThat(personController).isNotNull();
    }

    @Test //Prueba del getJson que obtiene el json de la url
    public void testGetJSON() throws Exception {
        JsonObject jsonObject = null;
        try {
            jsonObject = personController.getPersonService().getJSON();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //Se realizan algunas validaciones
        Assert.assertNotNull(jsonObject);
        Assert.assertEquals(1, jsonObject.get("page").getAsInt());
        Assert.assertEquals(6, jsonObject.get("per_page").getAsInt());
        Assert.assertEquals(12, jsonObject.get("total").getAsInt());
        Assert.assertEquals(2, jsonObject.get("total_pages").getAsInt());
        Assert.assertEquals("https://reqres.in/#support-heading",
                jsonObject.get("support").getAsJsonObject().get("url").getAsString());
        Assert.assertEquals("To keep ReqRes free, contributions towards server costs are appreciated!",
                jsonObject.get("support").getAsJsonObject().get("text").getAsString());
        //Por ejemplo el id de la primera persona
        Assert.assertEquals(1,
                jsonObject.get("data").getAsJsonArray().get(0).getAsJsonObject().get("id").getAsInt());
    }
}
