import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static int max = 0, maxIdx = 0;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String[] line;
		int n = toi(br.readLine());
		Node[] nodes = new Node[n + 1];
		for(int i = 1; i <= n; i++) nodes[i] = new Node[i];

		line = getLine();
		Node node = nodes[toi(line[0])];
		for(int i = 1; i < n; i++) {
			Node tempNode = nodes[toi(line[i])];
			tempNode.left = node;
			node.right = tempNodeNode;
		}


		for(int i = 0; i < n; i++) {
			int[] pair = al.get(i);
			sum += get(seg, 0, n - 1, 0, pair[1], 1);
			update(seg, 0, n - 1, pair[1], 1);
		}
		print(sum);
	}

	class Node {
		int idx;
		int left;
		int right;
		boolean bleft;
		boolean bright;

		public Node(int idx) { this.idx = idx; }
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }	
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}