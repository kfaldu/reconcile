package com.kpf.reconcile;

public abstract class CsvWriterInterface {

  public abstract String[] toStringArray();

  public String getSafeString(Object o) {
    return o == null ? "" : o.toString();
  }
}
