package com.optus.counterapi.service;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FileParserServiceTest {

  private final FileParserService subject = new FileParserService();

  @Test
  void should_parse_input_file_into_strings_based_on_regex() throws IOException {

    //given
    String fileName = "test.txt";
    String regex = "\\w+";

    //when
    List<String> actual = subject.parseFileAsStrings(fileName, regex);

    //then
    List<String> expected = Arrays.asList("The", "cat", "has", "four", "eyes", "and", "four", "legs");
    assertThat(actual).isEqualTo(expected);
  }
}