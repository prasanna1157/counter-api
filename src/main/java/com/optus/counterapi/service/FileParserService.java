package com.optus.counterapi.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class FileParserService {

  /**
   * This method parses a given file into strings based on a regex.
   *
   * @param fileName The name of the file to be parsed.
   * @param regex    The regex to be used for parsing.
   * @return list of strings
   */
  public List<String> parseFileAsStrings(String fileName, String regex) throws IOException {
    Resource resource = new ClassPathResource(fileName);
    Path path = Paths.get(resource.getFile().getAbsolutePath());
    return new Scanner(path).findAll(Pattern.compile(regex))
        .map(MatchResult::group)
        .collect(Collectors.toList());
  }
}