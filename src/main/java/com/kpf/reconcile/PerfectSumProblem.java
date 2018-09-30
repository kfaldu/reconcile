package com.kpf.reconcile;

// A Java program to count all subsets with given sum.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;

public class PerfectSumProblem {

  // dp[i][j] is going to store true if sum j is
  // possible with array elements from 0 to i.
  static boolean[][] dp;
  static boolean foundMatch = false;

  static void display(ArrayList<Integer> v) {
    System.out.println(
        v.stream().mapToDouble(PerfectSumProblem::toDouble).boxed().collect(Collectors.toList()));
    Scanner sc = new Scanner(System.in);

    System.out.print("Is it a correct match(y/n): ");
    char c = sc.next().charAt(0);
    if(c == 'y') {
      foundMatch = true;
    }
  }

  // A recursive function to print all subsets with the
  // help of dp[][]. Vector p[] stores current subset.
  static void printSubsetsRec(int arr[], int i, int sum, ArrayList<Integer> p)
  {

    if (foundMatch) {
      return;
    }

    // If we reached end and sum is non-zero. We print
    // p[] only if arr[0] is equal to sun OR dp[0][sum]
    // is true.
    if (i == 0 && sum != 0 && dp[0][sum])
    {
      p.add(arr[i]);
      display(p);
      p.clear();
      return;
    }

    // If sum becomes 0
    if (i == 0 && sum == 0)
    {
      display(p);
      p.clear();
      return;
    }

    // If given sum can be achieved after ignoring
    // current element.
    if (dp[i-1][sum])
    {
      // Create a new vector to store path
      ArrayList<Integer> b = new ArrayList<>();
      b.addAll(p);
      printSubsetsRec(arr, i-1, sum, b);
    }

    // If given sum can be achieved after considering
    // current element.
    if (sum >= arr[i] && dp[i-1][sum-arr[i]])
    {
      p.add(arr[i]);
      printSubsetsRec(arr, i-1, sum-arr[i], p);
    }
  }

  // Prints all subsets of arr[0..n-1] with sum 0.
  static void printAllSubsets(int arr[], int n, int sum)
  {
    if (n == 0 || sum < 0)
      return;

    // Sum 0 can always be achieved with 0 elements
    dp = new boolean[n][sum + 1];
    for (int i=0; i<n; ++i)
    {
      dp[i][0] = true;
    }

    // Sum arr[0] can be achieved with single element
    if (arr[0] <= sum)
      dp[0][arr[0]] = true;

    // Fill rest of the entries in dp[][]
    for (int i = 1; i < n; ++i)
      for (int j = 0; j < sum + 1; ++j)
        dp[i][j] = (arr[i] <= j) ? (dp[i-1][j] ||
            dp[i-1][j-arr[i]])
            : dp[i - 1][j];
    if (dp[n-1][sum] == false)
    {
      System.out.println("There are no subsets with" +
          " sum "+ sum);
      return;
    }

    // Now recursively traverse dp[][] to find all
    // paths from dp[n-1][sum]
    ArrayList<Integer> p = new ArrayList<>();
    printSubsetsRec(arr, n-1, sum, p);
  }

  @Data
  @AllArgsConstructor
  class BooleanDoubleClass {
    double d;
    boolean b;
  }

  double initialGlArray[] = {-1006812.36, -967120.02, -511250, -404132.55, -200000, -187000, -175905.51, -101524.56, -94456.71, -90376.22, -79401.31, -79271.67, -74994.48, -74975.62, -72765.62, -65131.74, -55985.47, -55771.37, -43650.84, -41801.97, -40613.81, -40119.81, -38830.15, -38660.3, -38528.42, -37915.55, -37800, -36625.21, -35226.97, -35099.68, -34942.82, -33903.35, -33740.94, -33249.44, -32537.76, -30787.23, -30762.92, -30604.61, -30199.48, -29922.03, -29645.97, -29380.68, -26688.87, -26591.23, -24897.77, -24643.31, -24376.29, -24300, -23944.23, -23411.38, -23362.22, -22751.89, -22015.59, -21731.11, -21574.18, -20759.19, -20440.03, -20205.27, -19206.5, -18809.8, -18520.91, -18312.17, -18216.7, -18180.05, -17626.8, -17595, -16700.99, -15815.29, -15750, -15720.82, -15016.95, -14585.63, -14426.15, -14162.01, -13771.75, -13491, -13194.94, -11756.96, -11426.73, -10977.83, -10593.75, -10204.21, -10118.71, -10054.73, -9944.57, -9638.43, -9424.4, -9321.2, -9270.98, -8749.45, -8564.43, -8335.43, -8096.35, -7791.12, -7692.93, -7600.08, -7210, -7160.92, -7126.15, -6804.04, -6745.55, -6700.13, -6438.82, -6345.71, -6271.93, -5979.33, -5835, -5834, -5438.18, -5356.9, -5307.92, -5290.12, -5243.75, -5125.12, -5079.73, -5045.16, -4944, -4868.6, -4864.21, -4833.62, -4750, -4738.23, -4729.83, -4571.44, -4456.11, -4437.37, -4324.8, -4286, -4267.51, -4100, -3953.6, -3941.78, -3917.18, -3804.35, -3700.69, -3694.21, -3570.31, -3570.31, -3558.56, -3498.54, -3459.98, -3384.1, -3065.17, -3027.15, -2950.77, -2908.38, -2903.1, -2822.88, -2760.63, -2669.75, -2591.6, -2500, -2440.09, -2440, -2384.6, -2282.97, -2277.28, -2115.2, -2109.85, -2109.52, -2105.13, -2085.37, -2044.54, -1969.77, -1967.95, -1942.07, -1927.03, -1903.4, -1856.1, -1848.02, -1778.03, -1764.21, -1744.33, -1740.55, -1722.65, -1700, -1696.44, -1622.5, -1599.99, -1579.59, -1577.62, -1570.14, -1570.14, -1553.4, -1550.77, -1479.66, -1473.6, -1440, -1425.28, -1407.49, -1390, -1377.54, -1362.98, -1300.92, -1294.42, -1219.75, -1193.73, -1187.39, -1173.95, -1154.06, -1142.22, -1134.2, -1119.1, -1096.07, -1072.69, -1066, -1019.62, -1018.55, -1011.44, -1005.73, -992.27, -978.28, -973.79, -954.62, -952.5, -947.3, -867, -855.65, -837.54, -794.82, -772, -757.68, -735.11, -663.42, -611, -599.57, -565.91, -558.4, -532.25, -530.75, -521.9, -514.86, -435, -428.69, -423.75, -400.02, -400, -400, -386.85, -383.4, -374.54, -365.46, -357.23, -351.85, -320, -309.17, -297.12, -280, -250, -250, -247.57, -246.55, -240, -217.14, -210.99, -200, -191.6, -177.34, -163.07, -154.61, -150, -150, -150, -150, -147.48, -126.84, -114.14, -109.38, -104.29, -100, -90.97, -87.97, -75.04, -74.56, -67.06, -66.4, -50, -50, -50, -45, -44.7, -44.41, -41.34, -40, -35, -31.98, -24.85, -24.49, -17.5, 3, 30, 31.69, 40, 42, 45, 66.17, 70, 73.5, 81, 82, 91, 93, 95, 98, 100, 100, 101.41, 106, 110, 110, 115, 115, 118, 120, 128, 136.11, 145, 146.38, 150, 153, 160, 160, 169.63, 183.14, 185, 192, 193, 221, 223, 225, 230, 235, 244, 248.53, 255, 255, 267, 277, 280, 288, 305, 306.41, 310, 332, 350, 375, 384, 389, 406, 410, 410, 413, 415, 440.58, 453.43, 468, 489, 494.1, 518, 524, 524.73, 552.95, 565.14, 565.14, 565.78, 566.94, 572, 572, 579, 585.03, 595, 612, 624.33, 627.61, 690, 700, 710.18, 761, 800, 807, 852.5, 862, 872.79, 878, 880, 896, 915, 936, 939, 975, 979, 983, 1001.58, 1031.58, 1070, 1087.68, 1135.06, 1146.07, 1150, 1176, 1216, 1313.39, 1316.98, 1365.7, 1387.21, 1445.8, 1451, 1493.35, 1503.58, 1534, 1574, 1634.28, 1648.14, 1687.3, 1818, 1964.03, 2024.99, 2059.5, 2065, 2109, 2157.37, 2187, 2193.7, 2265, 2275, 2289.56, 2354, 2375, 2385, 2387.78, 2396.88, 2410.65, 2425, 2543, 2589.22, 2601.48, 2619.25, 2691.5, 2705.78, 2764.03, 2966.43, 3038.33, 3171.23, 3197.62, 3214.69, 3240.72, 3278.37, 3297.6, 3316, 3404.22, 3441.95, 3531, 3562.85, 3565.86, 3609, 3745.09, 3855.89, 3859.41, 3976.64, 4006.9, 4015.1, 4071.14, 4206.53, 4280, 4349.6, 4428, 4454.71, 4477.54, 4512, 4521.49, 4540.59, 4668, 4708, 4809.99, 4887.35, 4969.84, 4972.01, 5124.45, 5187.39, 5208.75, 5215.59, 5273.7, 5279.45, 5370, 5532, 5608.49, 5655, 5719.31, 5729, 5765.75, 5984.91, 6010.75, 6127.65, 6272.39, 6706, 6719.39, 6788, 7161.09, 7643.22, 7659.51, 7679.67, 7827.11, 7833.82, 7909.11, 7918.84, 8039.42, 8096.22, 8181.73, 8243, 8505.78, 8872.02, 8881.61, 8926, 8940.46, 8946.81, 8995, 9523.63, 9599.18, 9909.38, 10011.87, 10033, 10507.01, 10660.66, 10666.85, 11036, 11094.74, 11158.2, 11191.02, 11400.25, 11504.07, 12477.8, 13035.62, 13453.5, 13571.85, 13670, 13810, 13881.93, 14120.35, 14354.61, 14453.23, 15272.6, 15337.6, 15361.01, 15399.09, 15415, 16639.7, 16769.67, 17141, 18667.6, 18898.3, 18949.2, 18949.92, 20219, 20715.51, 21162.19, 22683.05, 22757.59, 23313.65, 23754.02, 24155.08, 24463.26, 27218.23, 30064.88, 32503.4, 33604.22, 37441.14, 37566.86, 38547.36, 39185.57, 39647.3, 40648.98, 40817.25, 41850.23, 42920.06, 44282.14, 44909.24, 46630.68, 47678.15, 47755.81, 48022.42, 48284.05, 48709.05, 48853.02, 48853.02, 49036.76, 49827.95, 51998.8, 52822.1, 56330.8, 59920.45, 63408.46, 65309.84, 68632.78, 70030.96, 73432.56, 74058.36, 74411.56, 75007.47, 75319.71, 76156.44, 78314.94, 82151.92, 85684.42, 91648.3, 92314.73, 93540.72, 97883.32, 98433.52, 99196.68, 102130.36, 109341.49, 115469.73, 122717.7, 133452.45, 138373.61, 147761.57, 172595.93, 175638.45, 176703.95, 198665.59, 217886.99, 249975, 606606.51, 732341.31};

  double initialBankArray[] = {-1006812.36, -967120.02, -511250.00, -252119.54, -209154.95, -200000.00, -187000.00, -169171.43, -108556.27, -101524.56, -99891.99, -95109.90, -94456.71, -91077.79, -84396.79, -80591.77, -79929.70, -79810.11, -77039.02, -74975.62, -65164.19, -65131.74, -57646.23, -55771.37, -45082.67, -43650.84, -40613.81, -40119.81, -39965.69, -38830.15, -38660.30, -38528.42, -37915.55, -36625.21, -35226.97, -35099.68, -34942.82, -34476.05, -33903.35, -32537.76, -30787.23, -30762.92, -29922.03, -29380.68, -26899.67, -26591.23, -24974.88, -24897.77, -24643.31, -24376.29, -24300.00, -22751.89, -22015.59, -21731.11, -21574.18, -18216.70, -17626.80, -16700.99, -16624.41, -15815.29, -15750.00, -15720.82, -15016.95, -14585.63, -14162.01, -13491.00, -13194.94, -11756.96, -11426.73, -11086.34, -10717.40, -10118.71, -10054.73, -9638.43, -9424.40, -9289.09, -8996.50, -8564.43, -8335.43, -8141.69, -8096.35, -7791.12, -7735.94, -7692.93, -7600.08, -7210.00, -7126.15, -6700.13, -6585.71, -6271.93, -5979.33, -5835.00, -5834.00, -5438.18, -5390.86, -5356.90, -5307.92, -5290.12, -5133.01, -4944.00, -4868.60, -4864.21, -4750.00, -4738.23, -4729.83, -4571.44, -4396.73, -4324.80, -4267.51, -4100.00, -3953.60, -3941.78, -3804.35, -3570.31, -3570.31, -3384.10, -3065.17, -2760.63, -2591.60, -2583.53, -2440.00, -2384.60, -2282.97, -2277.28, -2115.20, -2024.00, -1856.10, -1848.02, -1700.00, -1622.50, -1599.99, -1570.14, -1473.60, -1440.00, -1425.28, -1407.49, -1390.00, -1362.98, -1300.92, -1187.39, -1155.34, -1154.10, -1090.19, -1018.55, -1011.44, -978.28, -973.79, -954.62, -855.65, -837.54, -794.82, -618.33, -530.75, -515.32, -514.86, -493.32, -435.00, -423.75, -400.02, -400.00, -400.00, -400.00, -394.07, -386.85, -379.11, -320.00, -305.52, -266.11, -246.55, -214.05, -211.61, -192.48, -191.60, -182.21, -167.77, -163.98, -147.67, -142.00, -104.49, -101.14, -100.76, -44.70, 3.00, 45.00, 66.17, 82.00, 101.41, 110.00, 136.11, 146.38, 150.00, 160.00, 160.00, 183.14, 192.00, 255.00, 277.00, 280.00, 288.00, 305.00, 350.00, 384.00, 389.00, 410.00, 413.00, 440.58, 453.43, 489.00, 494.10, 524.00, 565.14, 565.14, 565.78, 566.94, 579.00, 585.03, 624.33, 627.61, 710.18, 852.50, 862.00, 872.79, 896.00, 915.00, 979.00, 1001.58, 1031.58, 1135.06, 1146.07, 1313.39, 1316.98, 1365.70, 1445.80, 1493.35, 1534.00, 1648.14, 1818.00, 1964.03, 2024.99, 2059.50, 2065.00, 2157.37, 2187.00, 2193.70, 2387.78, 2410.65, 2619.25, 2764.03, 2966.43, 3038.33, 3197.62, 3214.69, 3240.72, 3297.60, 3316.00, 3441.95, 3562.85, 3745.09, 3855.89, 3859.41, 4006.90, 4071.14, 4206.53, 4280.00, 4325.78, 4428.00, 4512.00, 4521.49, 4819.00, 4969.84, 4972.01, 5049.60, 5124.45, 5187.39, 5208.75, 5215.59, 5273.70, 5370.00, 6010.75, 6271.64, 6706.00, 7643.22, 7659.51, 7679.67, 7909.11, 8243.00, 8505.78, 8881.61, 8926.00, 8940.46, 9523.63, 10033.00, 10507.01, 10660.66, 10666.85, 11015.00, 11036.00, 11094.74, 11317.79, 11473.50, 11504.07, 11529.38, 11842.21, 12076.00, 12311.36, 12477.80, 12923.45, 13035.62, 13453.50, 13881.93, 14120.35, 15272.60, 15337.60, 15399.09, 15436.23, 17141.00, 17538.51, 18667.60, 18898.30, 18949.20, 18949.92, 19822.25, 19994.84, 21162.19, 24463.26, 29298.56, 30064.88, 33511.51, 33604.22, 35137.07, 37441.14, 37566.86, 40714.24, 42336.28, 44417.13, 44423.64, 44909.24, 47755.81, 47909.10, 48022.42, 48709.05, 48853.02, 49036.76, 51998.80, 52097.43, 52822.10, 62404.89, 64512.53, 65309.84, 65686.20, 66377.55, 70030.96, 73432.56, 74058.36, 75007.47, 76156.44, 77609.27, 78314.94, 88480.91, 88595.88, 92314.73, 94446.02, 94628.40, 97883.32, 99196.68, 104834.97, 113236.41, 126660.75, 127190.01, 133452.45, 150414.41, 172595.93, 172845.08, 177559.18, 188578.82, 237212.95, 242723.19, 249975.00, 284979.94, 299421.65, 606606.51, 823989.61};

  private List<BooleanDoubleClass> initialGlList = new ArrayList<>();
  private List<BooleanDoubleClass> initialBankList = new ArrayList<>();

  void initializeLists() {
    for(double d: initialGlArray) {
      initialGlList.add(new BooleanDoubleClass(d, false));
    }
    for(double d: initialBankArray) {
      initialBankList.add(new BooleanDoubleClass(d, false));
    }
  }

  static double[] getUnmatchedArray(List<BooleanDoubleClass> bdl) {
    return bdl.stream().filter(booleanDoubleClass -> !booleanDoubleClass.b).mapToDouble(bd -> bd.d).toArray();
  }

  //Driver Program to test above functions
  public static void main(String args[])
  {
    PerfectSumProblem perfectSumProblem = new PerfectSumProblem();
    perfectSumProblem.initializeLists();
    double glArray[] = PerfectSumProblem.getUnmatchedArray(perfectSumProblem.initialGlList);

    int[] creditArrayGl = Arrays.stream(glArray).filter(a -> a > 0).mapToInt(PerfectSumProblem::toInt)
        .toArray();

    int[] debitArrayGl = Arrays.stream(glArray).filter(a -> a < 0).mapToInt(PerfectSumProblem::toInt)
        .toArray();

    double bankArray[] = PerfectSumProblem.getUnmatchedArray(perfectSumProblem.initialBankList);

    int[] creditArrayBank = Arrays.stream(bankArray).filter(a -> a > 0).mapToInt(PerfectSumProblem::toInt)
        .toArray();

    int[] debitArrayBank = Arrays.stream(bankArray).filter(a -> a < 0).mapToInt(PerfectSumProblem::toInt)
        .toArray();

/*    List<Integer> someCreditGlList = Arrays.stream(creditArrayGl).boxed().collect(Collectors.toList());

    for(int creditBank: creditArrayBank) {
      if(someCreditGlList.stream().filter(a -> a == creditBank).findFirst().orElse(0) != 0) {
        System.out.println("Credit Found match for "+PerfectSumProblem.toDouble(creditBank));
      }
    }

    List<Integer> someDebitGlList = Arrays.stream(debitArrayGl).boxed().collect(Collectors.toList());

    for(int debitBank: debitArrayBank) {
      if(someDebitGlList.stream().filter(a -> a == debitBank).findFirst().orElse(0) != 0) {
        System.out.println("Debit Found match for "+PerfectSumProblem.toDouble(debitBank));
      }
    }*/

    for(int creditBank : creditArrayBank) {
      System.out.print("\nBank: " + PerfectSumProblem.toDouble(creditBank) + " GL: ");
      try {
        dp = new boolean[0][0];
        foundMatch = false;
        printAllSubsets(creditArrayGl, creditArrayGl.length, creditBank);
      } catch (RuntimeException re) {

      }
    }

  }

  static int toInt(double d) {
    return (int) (d * 100);
  }

  static double toDouble(int i) {
    return ((double) i) / 100;
  }

}
