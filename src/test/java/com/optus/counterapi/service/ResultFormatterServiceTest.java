package com.optus.counterapi.service;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ResultFormatterServiceTest {

  ResultFormatterService subject = new ResultFormatterService();

  @Test
  void should_return_csv_from_map() throws IOException {

    //given
    Map<String, Long> input = new LinkedHashMap<>();
    input.put("the", 2L);
    input.put("cat", 1L);

    //when
    String actual = subject.mapToCSV(input);

    //then
    String expected = "the|2" + System.lineSeparator() + "cat|1";
    assertThat(actual).isEqualTo(expected);
  }
}