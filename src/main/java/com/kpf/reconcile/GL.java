package com.kpf.reconcile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GL {
  String description;
  Double amount;
  Long entryNumber;
  Boolean matched = false;
}
