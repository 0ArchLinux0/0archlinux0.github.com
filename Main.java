import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String[] line;
		int T = toi(br.readLine());

		for(int i = 0; i < T; i++) {
			line =  getLine();
			int n = toi(line[0]), m = toi(line[1]), t = toi(line[2]);
			ArrayList<ArrayList<Integer>> al = new ArrayList<>();
			for(int i = 0; i < n; i++) al.add(new ArrayList<Integer>());
			line = getLine();
			int s = toi(line[0]), g = toi(line[1]), h = toi(line[2]);
			
		}
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine(BufferedReader br) { br.readLine().split(" "); }	
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}