package com.optus.counterapi.service;

import com.optus.counterapi.model.WordFrequency;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

@Service
public class CounterService {

  /**
   * This method finds the frequency of all input words from the given content ignoring case.
   *
   * @param input       List of Strings to find frequencies for.
   * @param textContent List of Strings of contents.
   * @return frequencies of all words in input
   */
  public WordFrequency countWordFrequencyCaseInsensitive(List<String> input, List<String> textContent) {
    WordFrequency wordFrequency = new WordFrequency();

    List<String> inputInLowerCase = toLowerCase(input);
    textContent = toLowerCase(textContent);

    // create map of inputInLowerCase list by frequency
    Map<String, Long> map = mapInputWordsByFrequency(textContent, inputInLowerCase);

    // put default value to map and preserve inputInLowerCase order
    wordFrequency.setCounts(input.stream().collect(toMap(Function.identity(), x -> map.getOrDefault(x.toLowerCase()
        , 0L), (x, y) -> y, LinkedHashMap::new)));

    return wordFrequency;
  }


  /**
   * This method finds the highest frequency words in the given content.
   *
   * @param count       Number of words to be displayed in the result.
   * @param textContent List of Strings of contents.
   * @return a map of highest frequency words
   */
  public Map<String, Long> findHighestFrequencyWords(Long count, List<String> textContent) {
    textContent = toLowerCase(textContent);
    Map<String, Long> collect = mapAllWordsByFrequency(textContent);
    LinkedHashMap<String, Long> map = collect.entrySet()
        .stream()
        .sorted((Map.Entry.<String, Long>comparingByValue().reversed())).limit(count)
        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    return map;
  }

  private Map<String, Long> mapAllWordsByFrequency(List<String> input) {
    return input.stream().collect(groupingBy(Function.identity(), counting()));
  }

  private Map<String, Long> mapInputWordsByFrequency(List<String> content, List<String> input) {
    return content.stream().filter(input::contains)
        .collect(groupingBy(Function.identity(), LinkedHashMap::new, counting()));
  }

  private List<String> toLowerCase(List<String> input) {
    return input.stream()
        .map(String::toLowerCase)
        .collect(toList());
  }
}