package com.kpf.reconcile;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bank {
  String description;
  Double debit;
  Double credit;
  Double amount = 0d;
  Boolean matched = false;
  List<Long> entryList = new ArrayList<>();
}
