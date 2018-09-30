package com.kpf.reconcile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GL extends CsvWriterInterface {
  String description;
  Double amount;
  Long entryNumber;
  Boolean matched = false;

  @Override
  public String[] toStringArray() {
    return new String[]{getSafeString(description), getSafeString(amount),
        getSafeString(entryNumber), getSafeString(matched)};
  }
}
