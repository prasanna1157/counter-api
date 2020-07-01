package com.optus.counterapi.endtoend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Base64Utils;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EndToEndTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void should_return_unauthorized_when_called_without_authentication_header() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
        .get("/counter-api"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void should_return_400_when_input_is_bad() throws Exception {

    String searchText = "{\"searchText\": [\"Duis\", \"Sed\"]";

    mockMvc.perform(MockMvcRequestBuilders
        .post("/counter-api")
        .header(HttpHeaders.AUTHORIZATION,
            "Basic " + Base64Utils.encodeToString("optus:candidates".getBytes()))
        .contentType(MediaType.APPLICATION_JSON)
        .content(searchText))
        .andExpect(status().is4xxClientError());
  }

  @Test
  void should_return_word_frequency_of_input_words() throws Exception {

    String searchText = "{\"searchText\": [\"Duis\", \"Sed\"]}";
    String expected = "{\"counts\": {\"Duis\" : 11, \"Sed\" : 16}}";

    mockMvc.perform(MockMvcRequestBuilders
        .post("/counter-api/search")
        .header(HttpHeaders.AUTHORIZATION,
            "Basic " + Base64Utils.encodeToString("optus:candidates".getBytes()))
        .contentType(MediaType.APPLICATION_JSON)
        .content(searchText))
        .andExpect(status().isOk())
        .andExpect(content().json(expected));
  }

  @Test
  void should_return_highest_frequency_words() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
        .get("/counter-api/top/2")
        .header(HttpHeaders.AUTHORIZATION,
            "Basic " + Base64Utils.encodeToString("optus:candidates".getBytes())))
        .andExpect(status().isOk())
        .andExpect(content().string("vel|17" + System.lineSeparator() + "eget|17"));
  }
}
