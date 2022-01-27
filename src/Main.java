import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

  public static void main(String[] args) throws URISyntaxException, IOException {
    //Day1
    List<String> depths = Files.readAllLines(
        Paths.get(Objects.requireNonNull(Main.class.getResource("day_1_input.txt")).toURI()));
    System.out.println("Day 1, Part 1: " + AdventOfCode.day1part1(depths));
    System.out.println("Day 1, Part 2: " + AdventOfCode.day1part2(depths));

    //Day2
    List<String> commands = Files.readAllLines(
        Paths.get(Objects.requireNonNull(Main.class.getResource("day_2_input.txt")).toURI()));
    System.out.println("Day 2, Part 1: " + AdventOfCode.day2part1(commands));
    System.out.println("Day 2, Part 2: " + AdventOfCode.day2part2(commands));

    //Day3
    List<String> binaries = Files.readAllLines(
        Paths.get(Objects.requireNonNull(Main.class.getResource("day_3_input.txt")).toURI()));
    System.out.println("Day 3, Part 1: " + AdventOfCode.day3part1(binaries));
    System.out.println("Day 3, Part 2: " + AdventOfCode.day3part2(binaries));

    //Day4
    List<Integer> numbers;
    List<int[][]> boards = new ArrayList<>();
    try (Stream<String> linesStream = Files.lines(
        Paths.get(Objects.requireNonNull(Main.class.getResource("day_4_input.txt")).toURI()))) {
      List<String> lines = linesStream.collect(Collectors.toList());
      numbers = Arrays.stream(lines.get(0).split(",")).map(Integer::parseInt)
          .collect(Collectors.toList());
      List<String> boardNumbers = new ArrayList<>();
      for (int i = 2; i < lines.size(); i++) {
        if (!lines.get(i).isEmpty()) {
          boardNumbers.addAll(Arrays.asList(lines.get(i).trim().split(" +")));
        } else {
          boards.add(AdventOfCode.createBingoBoard(boardNumbers));
          boardNumbers.clear();
        }
      }
    }
    System.out.println("Day 4, Part 1: " + AdventOfCode.day4part1(numbers, boards));
    System.out.println("Day 4, Part 2: " + AdventOfCode.day4part2(numbers, boards));

    //Day5
    List<String> coordinates = Files.readAllLines(
        Paths.get(Objects.requireNonNull(Main.class.getResource("day_5_input.txt")).toURI()));
    List<Integer> x1List = new ArrayList<>();
    List<Integer> y1List = new ArrayList<>();
    List<Integer> x2List = new ArrayList<>();
    List<Integer> y2List = new ArrayList<>();
    int maxX = 0;
    int maxY = 0;
    for (String coord : coordinates) {
      String firstCoords = coord.split(" -> ")[0];
      String secondCoords = coord.split(" -> ")[1];
      int x1 = Integer.parseInt(firstCoords.split(",")[0]);
      int y1 = Integer.parseInt(firstCoords.split(",")[1]);
      int x2 = Integer.parseInt(secondCoords.split(",")[0]);
      int y2 = Integer.parseInt(secondCoords.split(",")[1]);
      x1List.add(x1);
      y1List.add(y1);
      x2List.add(x2);
      y2List.add(y2);
      maxX = Math.max(maxX, Math.max(x1, x2));
      maxY = Math.max(maxY, Math.max(y1, y2));
    }
    System.out.println(
        "Day 5, Part 1: " + AdventOfCode.day5part1(x1List, y1List, x2List, y2List, maxX, maxY));
    System.out.println(
        "Day 5, Part 1: " + AdventOfCode.day5part2(x1List, y1List, x2List, y2List, maxX, maxY));

    //Day6
    String start = Files.readAllLines(
            Paths.get(Objects.requireNonNull(Main.class.getResource("day_6_input.txt")).toURI()))
        .get(0);
    List<Integer> school = Arrays.stream(start.split(",")).map(Integer::parseInt)
        .collect(Collectors.toList());
    System.out.println("Day 6, Part 1: " + AdventOfCode.day6part1(school));
    System.out.println("Day 6, Part 2: " + AdventOfCode.day6part2(school));

    //Day7
    String input = Files.readAllLines(
            Paths.get(Objects.requireNonNull(Main.class.getResource("day_7_input.txt")).toURI()))
        .get(0);
    List<Integer> positions = Arrays.stream(input.split(",")).map(Integer::parseInt)
        .collect(Collectors.toList());
    System.out.println("Day 7, Part 1: " + AdventOfCode.day7part1(positions));
    System.out.println("Day 7, Part 2: " + AdventOfCode.day7part2(positions));

    //Day8
    List<String> digits = Files.readAllLines(
        Paths.get(Objects.requireNonNull(Main.class.getResource("day_8_input.txt")).toURI()));
    List<String> outputs = new ArrayList<>();
    for (String digit : digits) {
      outputs.addAll(Arrays.asList(digit.substring(digit.lastIndexOf("|") + 2).split(" ")));
    }
    System.out.println("Day 8, Part 1: " + AdventOfCode.day8part1(outputs));

    List<String[]> input1 = new ArrayList<>(), input2 = new ArrayList<>();
    File inputFile = new File(Main.class.getResource("day_8_input.txt").getPath());
    Scanner scan = new Scanner(inputFile);
    String[] ss;
    while (scan.hasNext()) {
      ss = scan.nextLine().split(" \\| ");
      input1.add(ss[0].split("\\s"));
      input2.add(ss[1].split("\\s"));
    }
    scan.close();
    System.out.println("Day 8, Part 2: " + AdventOfCode.day8part2(input1, input2));

    //Day9
    List<String> lines = Files.readAllLines(
        Paths.get(Objects.requireNonNull(Main.class.getResource("day_9_input.txt")).toURI()));
    int[][] heights = new int[lines.size()][lines.get(0).length()];
    int i = 0;
    for (String line : lines) {
      char[] chars = line.toCharArray();
      for (int j = 0; j < chars.length; j++) {
        heights[i][j] = Integer.parseInt(String.valueOf(chars[j]));
      }
      i++;
    }
    System.out.println("Day 9, Part 1: " + AdventOfCode.day9part1(heights));
  }
}
