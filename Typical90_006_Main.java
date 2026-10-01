import java.io.*;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    String[] firstLine = input.readLine().trim().split("\\s+");
    int n = Integer.parseInt(firstLine[0]);
    int k = Integer.parseInt(firstLine[1]);
    String s = input.readLine().trim();

    char[] stack = new char[n];
    int size = 0;
    int removalsLeft = n - k;

    for (int i = 0; i < n; i++) {
      char current = s.charAt(i);
      while (removalsLeft > 0 && size > 0 && stack[size - 1] > current) {
        size--;
        removalsLeft--;
      }
      stack[size++] = current;
    }

    size -= removalsLeft;
    System.out.println(new String(stack, 0, size));
  }
}