package com.optus.counterapi.service;

import com.optus.counterapi.model.WordFrequency;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CounterServiceTest {

  private final CounterService subject = new CounterService();

  private static List<String> content;

  @BeforeAll
  public static void setUp() {
    content = Arrays.asList("the", "cat", "has", "four", "eyes", "and", "four", "legs");
  }

  @Test
  void should_return_highest_frequency_words() {
    //set up
    Map<String, Long> expected = new LinkedHashMap<>();
    expected.put("four", 2L);
    expected.put("the", 1L);

    //given
    Long count = 2L;

    //when
    Map<String, Long> actual = subject.findHighestFrequencyWords(count, content);

    //then
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void should_return_word_frequency_of_input_words() {
    //set up
    Map<String, Long> wordFrequencyMap = new LinkedHashMap<>();
    wordFrequencyMap.put("the", 1L);
    wordFrequencyMap.put("four", 2L);
    wordFrequencyMap.put("cat", 1L);
    WordFrequency expected = new WordFrequency();
    expected.setCounts(wordFrequencyMap);

    //given
    List<String> input = Arrays.asList("the", "four", "cat");

    //when
    WordFrequency actual = subject.countWordFrequencyCaseInsensitive(input, content);

    //then
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void should_return_word_frequency_of_input_words_ignore_case() {
    //set up
    Map<String, Long> wordFrequencyMap = new LinkedHashMap<>();
    wordFrequencyMap.put("The", 1L);
    wordFrequencyMap.put("Four", 2L);
    wordFrequencyMap.put("Cat", 1L);
    WordFrequency expected = new WordFrequency();
    expected.setCounts(wordFrequencyMap);

    //given
    List<String> input = Arrays.asList("The", "Four", "Cat");

    //when
    WordFrequency actual = subject.countWordFrequencyCaseInsensitive(input, content);

    //then
    assertThat(actual).isEqualTo(expected);
  }
}