package com.github.trionfettinicoUNICAM.PPTeam_DOIT.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    protected MockMvc mvc;

    protected String toJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T toObject(String json, Class<T> clazz)
            throws JsonParseException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    protected <T> Set<T> toObjectSet(String json, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(Set.class,clazz));
    }

    protected String get(String uri, int expectedStatus, String expectedContent) throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus,status);
        String content = mvcResult.getResponse().getContentAsString();
        if(expectedContent!=null)
            assertTrue(content.contains(expectedContent));
        return content;
    }

    protected <T> T get(String uri, int expectedStatus, T expectedContent, Class<T> contentType) throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus,status);
        String content = mvcResult.getResponse().getContentAsString();
        T responseObject = toObject(content, contentType);
        assertEquals(expectedContent,responseObject);
        return responseObject;
    }

    protected String post(String uri, Object parameter, int expectedStatus, String expectedContent) throws Exception {
        String userJson = toJson(parameter);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(userJson)).andReturn();

        //checks the response from the server
        int status = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains(expectedContent));
        return content;
    }

    protected <T> T post(String uri, T parameter, int expectedStatus, T expectedContent, Class<T> contentType) throws Exception {
        String userJson = toJson(parameter);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(userJson)).andReturn();

        //checks the response from the server
        int status = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus, status);
        String content = mvcResult.getResponse().getContentAsString();
        T responseObject = toObject(content,contentType);
        assertEquals(expectedContent,responseObject);
        return responseObject;
    }

    protected String delete(String uri, int expectedStatus, String expectedContent) throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus,status);
        String content = mvcResult.getResponse().getContentAsString();
        if(expectedContent!=null)
            assertTrue(content.contains(expectedContent));
        return content;
    }

    protected String put(String uri, Object parameter, int expectedStatus, String expectedContent) throws Exception {
        String userJson = toJson(parameter);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(userJson)).andReturn();

        //checks the response from the server
        int status = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus, status);
        String content = mvcResult.getResponse().getContentAsString();
        if(expectedContent != null)
            assertTrue(content.contains(expectedContent));
        return content;
    }

    protected <T> T put(String uri, T parameter, int expectedStatus, T expectedContent, Class<T> contentType) throws Exception {
        String userJson = toJson(parameter);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(userJson)).andReturn();

        //checks the response from the server
        int status = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus, status);
        String content = mvcResult.getResponse().getContentAsString();
        T responseObject = toObject(content,contentType);
        assertEquals(expectedContent,responseObject);
        return responseObject;
    }
}
