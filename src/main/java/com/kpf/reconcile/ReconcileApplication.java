package com.kpf.reconcile;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
@Slf4j
public class ReconcileApplication {

  static List<GL> glList = loadObjectList(GL.class, "gl.csv");
  static List<Bank> bankList = updateBankList(loadObjectList(Bank.class, "bank.csv"));

  public static void main(String[] args) {
    SpringApplication.run(ReconcileApplication.class, args);

    getPerfectMatch();

/*    bankList.stream().filter(bank -> !bank.matched).forEach(bank -> {
      foundMatch = false;
      dp = new boolean[0][0];
      int[] creditArrayGl = getCreditArray(glList);
      System.out.print("\nBank: " + bank.amount);
      printAllSubsets(creditArrayGl, creditArrayGl.length,
          ReconcileApplication.toInt(bank.amount * -1));
    });*/

    writeObjectList(Bank.class, bankList, "bankMatched.csv");
    writeObjectList(GL.class, glList, "glMatched.csv");
    System.out.println();
  }

  static <T extends CsvWriterInterface> int[] getCreditArray(List<T> fullList) {
    return fullList.stream()
        .filter(entry -> !entry.getMatched() && entry.getAmount() > 0)
        .mapToInt(entry -> ReconcileApplication.toInt(entry.getAmount())).toArray();
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

  public static void getPerfectMatch() {
    bankList.forEach(bank -> {
      long numberOfMatchedGl = glList.stream().filter(gl -> gl.amount.equals(bank.amount)).count();
      if (numberOfMatchedGl == 1) {
        glList.forEach(gl -> {
          if (gl.amount.equals(bank.amount)) {
            bank.matched = true;
            bank.entryList.add(gl.entryNumber);
            gl.matched = true;
          }
        });
      }
    });
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

  public static <T extends CsvWriterInterface> void writeObjectList(Class<T> type,
      List<T> objectList, String fileName) {
    try {
      CSVWriter writer = new CSVWriter(new FileWriter(fileName));
      for (T object : objectList) {
        writer.writeNext(object.toStringArray());
      }
      writer.close();

    } catch (Exception e) {
      log.error("Error occurred while creating object list to file " + fileName, e);
    }
  }

  // dp[i][j] is going to store true if sum j is
  // possible with array elements from 0 to i.
  static boolean[][] dp;
  static boolean foundMatch = false;

  static void display(ArrayList<Integer> v) {
    System.out.println("GL: " +
        v.stream().mapToDouble(ReconcileApplication::toDouble).boxed()
            .collect(Collectors.toList()));
    Scanner sc = new Scanner(System.in);

    System.out.print("Is it a correct match(y/n): ");
    char c = sc.next().charAt(0);
    if (c == 'y') {
      foundMatch = true;
    }
  }

  // A recursive function to print all subsets with the
  // help of dp[][]. Vector p[] stores current subset.
  static void printSubsetsRec(int arr[], int i, int sum, ArrayList<Integer> p) {

    if (foundMatch) {
      return;
    }

    // If we reached end and sum is non-zero. We print
    // p[] only if arr[0] is equal to sun OR dp[0][sum]
    // is true.
    if (i == 0 && sum != 0 && dp[0][sum]) {
      p.add(arr[i]);
      display(p);
      p.clear();
      return;
    }

    // If sum becomes 0
    if (i == 0 && sum == 0) {
      display(p);
      p.clear();
      return;
    }

    // If given sum can be achieved after ignoring
    // current element.
    if (dp[i - 1][sum]) {
      // Create a new vector to store path
      ArrayList<Integer> b = new ArrayList<>();
      b.addAll(p);
      printSubsetsRec(arr, i - 1, sum, b);
    }

    // If given sum can be achieved after considering
    // current element.
    if (sum >= arr[i] && dp[i - 1][sum - arr[i]]) {
      p.add(arr[i]);
      printSubsetsRec(arr, i - 1, sum - arr[i], p);
    }
  }


  // Prints all subsets of arr[0..n-1] with sum 0.
  static void printAllSubsets(int arr[], int n, int sum) {
    if (n == 0 || sum < 0) {
      return;
    }

    // Sum 0 can always be achieved with 0 elements
    dp = new boolean[n][sum + 1];
    for (int i = 0; i < n; ++i) {
      dp[i][0] = true;
    }

    // Sum arr[0] can be achieved with single element
    if (arr[0] <= sum) {
      dp[0][arr[0]] = true;
    }

    // Fill rest of the entries in dp[][]
    for (int i = 1; i < n; ++i) {
      for (int j = 0; j < sum + 1; ++j) {
        dp[i][j] = (arr[i] <= j) ? (dp[i - 1][j] ||
            dp[i - 1][j - arr[i]])
            : dp[i - 1][j];
      }
    }
    if (dp[n - 1][sum] == false) {
      System.out.println("There are no subsets with" +
          " sum " + sum);
      return;
    }

    // Now recursively traverse dp[][] to find all
    // paths from dp[n-1][sum]
    ArrayList<Integer> p = new ArrayList<>();
    printSubsetsRec(arr, n - 1, sum, p);
  }


  static int toInt(double d) {
    return (int) (d * 100);
  }

  static double toDouble(int i) {
    return ((double) i) / 100;
  }

}
