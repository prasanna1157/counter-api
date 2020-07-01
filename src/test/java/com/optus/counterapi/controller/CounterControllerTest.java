package com.optus.counterapi.controller;

import com.optus.counterapi.model.Input;
import com.optus.counterapi.model.WordFrequency;
import com.optus.counterapi.service.CounterService;
import com.optus.counterapi.service.FileParserService;
import com.optus.counterapi.service.ResultFormatterService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CounterControllerTest {

  @Mock
  CounterService counterService;
  @Mock
  FileParserService fileParserService;
  @Mock
  ResultFormatterService resultFormatterService;

  @InjectMocks
  private final CounterController subject = new CounterController();

  private static List<String> content;
  private static String fileName;
  private static String regex;

  @BeforeAll
  public static void setUp() {
    content = Arrays.asList("the", "cat", "has", "four", "eyes", "and", "four", "legs");
    fileName = "sample.txt";
    regex = "\\w+";
  }

  @Test
  void should_return_word_frequency_of_input_words() throws IOException {
    //setup
    Map<String, Long> wordFrequencyMap = new LinkedHashMap<>();
    wordFrequencyMap.put("the", 1L);
    wordFrequencyMap.put("four", 2L);
    wordFrequencyMap.put("cat", 1L);
    WordFrequency expected = new WordFrequency();
    expected.setCounts(wordFrequencyMap);
    when(counterService.countWordFrequencyCaseInsensitive(any(), any())).thenReturn(expected);
    when(fileParserService.parseFileAsStrings(any(), any())).thenReturn(content);

    //given
    List<String> searchText = Arrays.asList("the", "four", "cat");
    Input input = new Input();
    input.setSearchText(searchText);

    //when
    WordFrequency actual = subject.countWordFrequency(input);

    //then
    verify(counterService).countWordFrequencyCaseInsensitive(input.getSearchText(), content);
    verify(fileParserService).parseFileAsStrings(fileName, regex);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void should_return_highest_frequency_words() throws IOException {
    //setup
    Map<String, Long> map = new LinkedHashMap<>();
    map.put("four", 2L);
    map.put("the", 1L);
    String expected = "four|2" + System.lineSeparator() + "the|1";
    when(counterService.findHighestFrequencyWords(any(), any())).thenReturn(map);
    when(fileParserService.parseFileAsStrings(any(), any())).thenReturn(content);
    when(resultFormatterService.mapToCSV(any())).thenReturn(expected);

    //given
    Long count = 2L;

    //when
    String actual = subject.findHighestFrequencyWords(count);

    //then
    verify(counterService).findHighestFrequencyWords(count, content);
    verify(fileParserService).parseFileAsStrings(fileName, regex);
    verify(resultFormatterService).mapToCSV(map);
    assertThat(actual).isEqualTo(expected);
  }
}
