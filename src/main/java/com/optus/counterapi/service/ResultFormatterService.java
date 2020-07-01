package com.optus.counterapi.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResultFormatterService {

  /**
   * This method converts a map into CSV format string.
   *
   * @param map The map to be converted.
   * @return CSV formatted string
   */
  public String mapToCSV(Map<String, Long> map) {
    return map.entrySet()
        .stream()
        .map(entry -> entry.getKey() + "|" + entry.getValue())
        .collect(Collectors.joining(System.lineSeparator()));
  }
}