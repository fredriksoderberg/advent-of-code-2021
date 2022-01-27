import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdventOfCode {

  public static int day1part1(List<String> depths) {
    int increases = 0;
    int currentDepth = Integer.parseInt(depths.get(0));
    for (String depth : depths) {
      int nextDepth = Integer.parseInt(depth);
      if (nextDepth > currentDepth) {
        increases++;
      }
      currentDepth = nextDepth;
    }
    return increases;
  }

  public static int day1part2(List<String> depths) {
    int increases = 0;
    int currentDepthWindow =
        Integer.parseInt(depths.get(0)) + Integer.parseInt(depths.get(1)) + Integer.parseInt(
            depths.get(2));
    for (int i = 1; i < depths.size() - 2; i++) {
      int nextDepthWindow =
          Integer.parseInt(depths.get(i)) + Integer.parseInt(depths.get(i + 1)) + Integer.parseInt(
              depths.get(i + 2));
      if (nextDepthWindow > currentDepthWindow) {
        increases++;
      }
      currentDepthWindow = nextDepthWindow;
    }
    return increases;
  }

  public static int day2part1(List<String> commands) {
    int horizontal = 0;
    int depth = 0;
    for (String command : commands) {
      String[] currentCommand = command.split(" ");
      String action = currentCommand[0];
      int value = Integer.parseInt(currentCommand[1]);
      if (action.equals("forward")) {
        horizontal += value;
      } else {
        if (action.equals("up")) {
          depth -= value;
        } else {
          depth += value;
        }
      }
    }
    return horizontal * depth;
  }

  public static int day2part2(List<String> commands) {
    int horizontal = 0;
    int depth = 0;
    int aim = 0;
    for (String command : commands) {
      String[] currentCommand = command.split(" ");
      String action = currentCommand[0];
      int value = Integer.parseInt(currentCommand[1]);
      if (action.equals("forward")) {
        horizontal += value;
        depth += aim * value;
      } else {
        if (action.equals("up")) {
          aim -= value;
        } else {
          aim += value;
        }
      }
    }
    return horizontal * depth;
  }

  public static int day3part1(List<String> binaries) {
    int binaryLength = binaries.get(0).length();
    int[] mostCommonBits = new int[binaryLength];
    for (String binary : binaries) {
      for (int i = 0; i < binary.length(); i++) {
        if (binary.charAt(i) == '1') {
          mostCommonBits[i]++;
        } else {
          mostCommonBits[i]--;
        }
      }
    }
    char[] gammaBits = new char[binaryLength];
    for (int i = 0; i < mostCommonBits.length; i++) {
      gammaBits[i] = mostCommonBits[i] > 0 ? '1' : '0';
    }
    char[] epsilonBits = new char[binaryLength];
    for (int i = 0; i < gammaBits.length; i++) {
      epsilonBits[i] = gammaBits[i] == '1' ? '0' : '1';
    }
    int gamma = Integer.parseInt(String.valueOf(gammaBits), 2);
    int epsilon = Integer.parseInt(String.valueOf(epsilonBits), 2);
    return gamma * epsilon;
  }

  public static int day3part2(List<String> binaries) {
    int binaryLength = binaries.get(0).length();
    List<String> matchingBinaries = binaries;
    String oxygenBinary = null;
    for (int i = 0; i < binaryLength; i++) {
      matchingBinaries = binariesMatchingBitCriteria(matchingBinaries, i, true);
      if (matchingBinaries.size() == 1) {
        oxygenBinary = matchingBinaries.get(0);
        break;
      }
    }
    matchingBinaries = binaries;
    String co2Binary = null;
    for (int i = 0; i < binaryLength; i++) {
      matchingBinaries = binariesMatchingBitCriteria(matchingBinaries, i, false);
      if (matchingBinaries.size() == 1) {
        co2Binary = matchingBinaries.get(0);
        break;
      }
    }
    assert oxygenBinary != null;
    int oxygen = Integer.parseInt(oxygenBinary, 2);
    assert co2Binary != null;
    int co2 = Integer.parseInt(co2Binary, 2);
    return oxygen * co2;
  }

  public static List<String> binariesMatchingBitCriteria(List<String> binaries, int position,
      boolean searchMostCommonBit) {
    int bitCount = 0;
    for (String binary : binaries) {
      if (binary.charAt(position) == '1') {
        bitCount++;
      } else {
        bitCount--;
      }
    }
    char criteriaBit;
    if (searchMostCommonBit) {
      criteriaBit = bitCount >= 0 ? '1' : '0';
    } else {
      criteriaBit = bitCount >= 0 ? '0' : '1';
    }
    List<String> matchingBinaries = new ArrayList<>();
    for (String binary : binaries) {
      if (binary.charAt(position) == criteriaBit) {
        matchingBinaries.add(binary);
      }
    }
    return matchingBinaries;
  }

  public static int day4part1(List<Integer> numbers, List<int[][]> boards) {
    for (Integer number : numbers) {
      for (int[][] board : boards) {
        int sum = checkBingo(board, number);
        if (sum != -1) {
          return number * sum;
        }
      }
    }
    return 0;
  }

  public static int day4part2(List<Integer> numbers, List<int[][]> boards) {
    int highestCounter = 1;
    int highestSum = 0;
    int currentSum = 0;
    int currentCounter = 1;
    int highestNumber = 0;
    for (int[][] board : boards) {
      for (Integer number : numbers) {
        currentSum = checkBingo(board, number);
        if (currentSum != -1) {
          if (currentCounter > highestCounter) {
            highestSum = currentSum;
            highestCounter = currentCounter;
            highestNumber = number;
          }
          currentCounter = 0;
          break;
        }
        currentCounter++;
      }
    }
    return highestSum * highestNumber;
  }

  public static int BOARD_SIZE = 5;

  public static int[][] createBingoBoard(List<String> numbers) {
    int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
    int currentNumber = 0;
    for (int i = 0; i < BOARD_SIZE; i++) {
      for (int j = 0; j < BOARD_SIZE; j++) {
        board[i][j] = Integer.parseInt(numbers.get(currentNumber));
        currentNumber++;
      }
    }
    return board;
  }

  public static int checkBingo(int[][] board, int number) {
    int sum = 0;
    for (int i = 0; i < BOARD_SIZE; i++) {
      for (int j = 0; j < BOARD_SIZE; j++) {
        if (board[i][j] == number || board[i][j] == -1) {
          board[i][j] = -1;
        } else {
          sum += board[i][j];
        }
      }
    }

    boolean foundBingo = false;
    int bingoCount = 0;

    if (!foundBingo) {
      for (int i = 0; i < BOARD_SIZE; i++) {
        for (int j = 0; j < BOARD_SIZE; j++) {
          if (board[i][j] == -1) {
            bingoCount++;
          } else {
            bingoCount = 0;
            break;
          }
          if (bingoCount == 5) {
            foundBingo = true;
            break;
          }
        }
      }
    }

    if (!foundBingo) {
      for (int j = 0; j < BOARD_SIZE; j++) {
        for (int i = 0; i < BOARD_SIZE; i++) {
          if (board[i][j] == -1) {
            bingoCount++;
          } else {
            bingoCount = 0;
            break;
          }
          if (bingoCount == 5) {
            foundBingo = true;
            break;
          }
        }
      }
    }

    if (foundBingo) {
      return sum;
    } else {
      return -1;
    }

  }

  public static int day5part1(List<Integer> x1List, List<Integer> y1List, List<Integer> x2List,
      List<Integer> y2List, int maxX, int maxY) {
    int[][] grid = new int[maxX + 1][maxY + 1];
    for (int i = 0; i < x1List.size(); i++) {
      markLine(x1List.get(i), y1List.get(i), x2List.get(i), y2List.get(i), grid, false);
    }
    return numberOfCrossedLines(grid);
  }

  public static int day5part2(List<Integer> x1List, List<Integer> y1List, List<Integer> x2List,
      List<Integer> y2List, int maxX, int maxY) {
    int[][] grid = new int[maxX + 1][maxY + 1];
    for (int i = 0; i < x1List.size(); i++) {
      markLine(x1List.get(i), y1List.get(i), x2List.get(i), y2List.get(i), grid, true);
    }
    return numberOfCrossedLines(grid);
  }

  public static void markLine(int x1, int y1, int x2, int y2, int[][] grid, boolean checkDiogonal) {
    if (x1 == x2 || y1 == y2) {
      for (int x = Math.min(x1, x2); x <= (Math.max(x1, x2)); x++) {
        for (int y = Math.min(y1, y2); y <= (Math.max(y1, y2)); y++) {
          grid[x][y]++;
        }
      }
    }
    if (checkDiogonal) {
      if (x1 > x2 && y1 > y2) {
        for (int x = x1, y = y1; x >= x2; x--, y--) {
          grid[x][y]++;
        }
      } else if (x1 > x2 && y1 < y2) {
        for (int x = x1, y = y1; x >= x2; x--, y++) {
          grid[x][y]++;
        }
      } else if (x1 < x2 && y1 > y2) {
        for (int x = x1, y = y1; x <= x2; x++, y--) {
          grid[x][y]++;
        }
      } else if (x1 < x2 && y1 < y2) {
        for (int x = x1, y = y1; x <= x2; x++, y++) {
          grid[x][y]++;
        }
      }
    }
  }

  public static int numberOfCrossedLines(int[][] grid) {
    int crossedLines = 0;
    for (int x = 0; x < grid.length; x++) {
      for (int y = 0; y < grid[0].length; y++) {
        if (grid[x][y] >= 2) {
          crossedLines++;
        }
      }
    }
    return crossedLines;
  }

  public static long day6part1(List<Integer> school) {
    return calculateSchoolGrowth(school, 80);
  }

  public static long day6part2(List<Integer> school) {
    return calculateSchoolGrowth(school, 256);
  }

  public static long calculateSchoolGrowth(List<Integer> school, int days) {
    long[] numberOfFishPerDay = new long[9];
    for (Integer fish : school) {
      numberOfFishPerDay[fish]++;
    }
    int day = 1;
    while (day <= days) {
      long newFish = 0;
      for (int i = 0; i < 9; i++) {
        if (i == 0) {
          newFish = numberOfFishPerDay[0];
          numberOfFishPerDay[0] = numberOfFishPerDay[1];
        } else if (i == 6) {
          numberOfFishPerDay[6] = numberOfFishPerDay[7] + newFish;
        } else if (i == 8) {
          numberOfFishPerDay[8] = newFish;
        } else {
          numberOfFishPerDay[i] = numberOfFishPerDay[i + 1];
        }
      }
      day++;
    }
    long totalFish = 0;
    for (int i = 0; i < 9; i++) {
      totalFish += numberOfFishPerDay[i];
    }
    return totalFish;
  }

  public static int day7part1(List<Integer> positions) {
    int lowestFuel = Integer.MAX_VALUE;
    for (int i = 0; i < positions.size(); i++) {
      int totalFuel = 0;
      for (int j = 0; j < positions.size(); j++) {
        totalFuel += Math.abs(positions.get(j) - i);
      }
      lowestFuel = Math.min(totalFuel, lowestFuel);
    }
    return lowestFuel;
  }

  public static int day7part2(List<Integer> positions) {
    int lowestFuel = Integer.MAX_VALUE;
    for (int i = 0; i < positions.size(); i++) {
      int totalFuel = 0;
      for (int j = 0; j < positions.size(); j++) {
        int n = Math.abs(positions.get(j) - i);
        totalFuel += n * (n + 1) / 2;
      }
      lowestFuel = Math.min(totalFuel, lowestFuel);
    }
    return lowestFuel;
  }

  public static int day8part1(List<String> outputs) {
    int digitCount = 0;
    for (String output : outputs) {
      if (output.length() == 2 || output.length() == 3 || output.length() == 4
          || output.length() == 7) {
        digitCount++;
      }
    }
    return digitCount;
  }

  public static int day8part2(List<String[]> input1, List<String[]> input2) {
    int sum = 0, num;
    for (int i = 0; i < input1.size(); i++) {
      num = decode(input1.get(i), input2.get(i));
      sum += num;
    }
    return sum;
  }

  public static int decode(String[] pattern, String[] output) {
    var SegmentBuild = getSegmentBuild(pattern);
    StringBuilder num = new StringBuilder();
    for (String o : output) {
      num.append(getSegment(o.strip(), SegmentBuild));// cdgba
    }
    return Integer.valueOf(num.toString());
  }

  private static HashMap<Character, String> getSegmentBuild(String[] pattern) {
    var solution = new HashMap<Character, String>();
    int[] occur = getOccurences(pattern);
    pattern = sortPattern(pattern);
    solution.put('a', pattern[1].replace(String.valueOf(pattern[0].charAt(0)), "")
        .replace(String.valueOf(pattern[0].charAt(1)), ""));//a
    solution.put('b', Used(occur, 6, solution)); //b which is 6times total
    solution.put('e', Used(occur, 4, solution));//e 4times
    solution.put('f', Used(occur, 9, solution));//f 9times
    solution.put('c', pattern[0].replace(solution.get('f'),
        ""));//c cause in 2segment(piece1) patternand we know f
    // d, g 7times but d is in 04 (pattern[02]) digit pattern
    solution.put('d', Usedg(solution, pattern[2])); //d
    solution.put('g', getLast(solution)); //g
    return solution;
  }

  public static String getLast(HashMap<Character, String> s) {
    String compare = "";
    for (String str : s.values()) {
      compare += str;
    }
    String ss = "abcdefg";
    for (int i = 0; i < ss.length(); i++) {
      var check = String.valueOf(ss.charAt(i));
      if (!compare.contains(check)) {
        return check;
      }
    }
    return "";
  }

  public static int[] getOccurences(String[] pattern) {
    var hm = new HashMap<String, Integer>() {{
      put("a", 0);
      put("b", 1);
      put("c", 2);
      put("d", 3);
      put("e", 4);
      put("f", 5);
      put("g", 6);
    }};
    int[] occur = new int[7];//abcdefg
    for (String pat : pattern) {
      for (int i = 0; i < pat.length(); i++) {//b is used 6 times, e 4times, f 9 times
        String s = String.valueOf(pat.charAt(i));
        occur[hm.get(s)]++;
      }
    }
    return occur;
  }

  public static String Used(int[] getOccurence, int num, HashMap<Character, String> nope) {
    String str = "abcdefg";
    for (int i = 0; i < getOccurence.length; i++) {
      if (getOccurence[i] == num && !nope.containsValue(String.valueOf(str.charAt(i)))) {
        return String.valueOf(str.charAt(i));
      }
    }
    return "g";
  }

  public static String Usedg(HashMap<Character, String> solution, String four) {
    for (String s : solution.values()) {
      four = four.replace(s, "");
    }
    return four;
  }

  public static String[] sortPattern(String[] pattern) {
    String[] newPattern = new String[pattern.length];
    int cnt1 = 1, cnt2 = 1;
    for (String s : pattern) {
      if (s.length() == 2)//1
      {
        newPattern[0] = s;
      } else if (s.length() == 3)//1
      {
        newPattern[1] = s;
      } else if (s.length() == 4)//1
      {
        newPattern[2] = s;
      } else if (s.length() == 5) {//3
        newPattern[2 + cnt1] = s;
        cnt1++;
      } else if (s.length() == 6) {//3
        newPattern[5 + cnt2] = s;
        cnt2++;
      } else if (s.length() == 7)//1
      {
        newPattern[9] = s;
      }
    }
    return newPattern;
  }

  public static int getSegment(String pattern, HashMap<Character, String> segment) {
    switch (pattern.length()) {//getting the piece according to length
      case 2:
        return 1;
      case 4:
        return 4;
      case 3:
        return 7;
      case 7:
        return 8;
    }
    if (pattern.length() == 5) {
      if (!pattern.contains(segment.get('b')) && !pattern.contains(segment.get('f'))) {
        return 2;
      }
      if (!pattern.contains(segment.get('b')) && !pattern.contains(segment.get('e'))) {
        return 3;
      }
      return 5;
    }
    if (!pattern.contains(segment.get('e'))) {
      return 9;
    }
    if (!pattern.contains(segment.get('c'))) {
      return 6;
    }
    return 0;
  }

  public static int day9part1(int[][] heights) {
    int rows = heights.length;
    int columns = heights[0].length;
    int riskLevel = 0;
    int currentNode, up, down, left, right = 0;
    for (int i = 0; i < heights.length; i++) {
      for (int j = 0; j < heights[i].length; j++) {
        currentNode = heights[i][j];
        up = i != 0 ? heights[i - 1][j] : Integer.MAX_VALUE;
        down = i < rows - 1 ? heights[i + 1][j] : Integer.MAX_VALUE;
        left = j != 0 ? heights[i][j - 1] : Integer.MAX_VALUE;
        right = j < columns - 1 ? heights[i][j + 1] : Integer.MAX_VALUE;
        riskLevel += lowestPoint(up, down, left, right, currentNode);
      }
    }
    return riskLevel;
  }

  private static int lowestPoint(int up, int down, int left, int right, int currentNode) {
    if (currentNode < up && currentNode < down && currentNode < left && currentNode < right) {
      return currentNode + 1;
    } else {
      return 0;
    }
  }
}
