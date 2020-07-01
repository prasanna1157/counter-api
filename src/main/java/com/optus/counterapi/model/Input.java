package com.optus.counterapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class Input {
  @NotNull(message = "List can't be null")
  @NotEmpty(message = "List can't be empty")
  private List<String> searchText;
}