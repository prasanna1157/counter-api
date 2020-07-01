package com.optus.counterapi.controller;

import com.optus.counterapi.model.Input;
import com.optus.counterapi.model.WordFrequency;
import com.optus.counterapi.service.CounterService;
import com.optus.counterapi.service.FileParserService;
import com.optus.counterapi.service.ResultFormatterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;

@RestController
@RequestMapping("/counter-api")
@Validated
public class CounterController {

  @Autowired
  private CounterService counterService;

  @Autowired
  private FileParserService fileParserService;

  @Autowired
  private ResultFormatterService resultFormatterService;

  private final String SAMPLE_FILE = "sample.txt";
  private final String WORDS_ONLY_REGEX = "\\w+";

  /**
   * API to find word frequency for a given input from sample contents.
   *
   * @param input List of words to find frequencies for.
   * @return frequency of all words in the given input
   */

  @ApiOperation(value = "View the frequency of all input words")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully returned word frequency"),
      @ApiResponse(code = 400, message = "There was an error in the request body"),
      @ApiResponse(code = 401, message = "You are not authorised to use this resource")
  }
  )
  @PostMapping(value="/search", produces = "application/json")
  public WordFrequency countWordFrequency(@RequestBody @Valid Input input) throws IOException {
    return counterService.countWordFrequencyCaseInsensitive(input.getSearchText()
        , fileParserService.parseFileAsStrings(SAMPLE_FILE, WORDS_ONLY_REGEX));
  }

  /**
   * API to find highest frequency words from the sample content.
   *
   * @param count Number of words to be displayed in the result.
   * @return highest frequency words
   */
  @ApiOperation(value = "View the top n (based on count value) highest frequency words")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully returned highest frequency words"),
      @ApiResponse(code = 400, message = "There was an error in the request"),
      @ApiResponse(code = 401, message = "You are not authorised to use this resource")
  }
  )
  @GetMapping(value = "/top/{count}", produces = "text/csv")
  public String findHighestFrequencyWords(@PathVariable @Min(1) Long count) throws IOException {
    return resultFormatterService.mapToCSV(counterService.findHighestFrequencyWords(count,
        fileParserService.parseFileAsStrings(SAMPLE_FILE, WORDS_ONLY_REGEX)));
  }
}