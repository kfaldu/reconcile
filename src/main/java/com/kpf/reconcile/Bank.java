package com.kpf.reconcile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bank extends CsvWriterInterface {
  String description;
  Double debit;
  Double credit;
  Double amount = 0d;
  Boolean matched = false;
  List<Long> entryList = new ArrayList<>();

  @Override
  public String[] toStringArray() {
    return new String[]{getSafeString(description), getSafeString(debit), getSafeString(credit),
        getSafeString(amount),
        getSafeString(entryList.stream().map(Object::toString).collect(Collectors.joining(", ")))};
  }
}
