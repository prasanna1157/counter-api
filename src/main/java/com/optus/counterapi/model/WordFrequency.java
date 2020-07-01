package com.optus.counterapi.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
public class WordFrequency {
  Map<String, Long> counts = new LinkedHashMap<>();
}
