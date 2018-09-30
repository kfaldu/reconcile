package com.kpf.reconcile;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
@Slf4j
public class ReconcileApplication {

  public static void main(String[] args) {
    SpringApplication.run(ReconcileApplication.class, args);

    List<GL> glList = loadObjectList(GL.class, "gl.csv");
    List<Bank> bankList = updateBankList(loadObjectList(Bank.class, "bank.csv"));
  }

  public static List<Bank> updateBankList(List<Bank> bankList) {
    return bankList.stream().map(bank -> {
      if (bank.debit != null) {
        bank.amount = bank.debit * -1;
      } else if (bank.credit != null) {
        bank.amount = bank.credit;
      }
      return bank;
    }).collect(Collectors.toList());
  }

  public static void getPerfectMatch(List<Bank> bankList, List<GL> glList) {
  }

  public static <T> List<T> loadObjectList(Class<T> type, String fileName) {
    try {
      CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
      CsvMapper mapper = new CsvMapper();
      File file = new ClassPathResource(fileName).getFile();
      MappingIterator<T> readValues =
          mapper.reader(type).with(bootstrapSchema).readValues(file);
      return readValues.readAll();
    } catch (Exception e) {
      log.error("Error occurred while loading object list from file " + fileName, e);
      return Collections.emptyList();
    }
  }
}
