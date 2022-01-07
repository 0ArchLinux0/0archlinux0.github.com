import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static int max = 0, maxIdx = 0;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String[] line;
		Node root = new Node(toi(br.readLine()));
		Stack<Node> stack = new Stack<>();
		stack.push(new Node(Integer.MAX_VALUE));
		stack.push(root);

		String s;
		// while((s = br.readLine()) != null && s.length() != 0) { //for local test
			while((s = br.readLine()) != nul) { //for local test
			int idx = Integer.parseInt(s);
			if(stack.peek().idx > idx) {
				stack.peek().left = new Node(idx);
				stack.push(stack.peek().left);
			} else {
				Node node;
				while(true) {
					node = stack.pop();
					if(stack.peek().idx > idx) break; 
				}
				node.right = new Node(idx);
				stack.push(node.right);
			}
			if(s.length() == 0) return;
		}
		post(root, sb);
		print(sb);
	}

	static void post(Node node, StringBuilder sb) {
		if(node.left != null) post(node.left, sb);
		if(node.right != null) post(node.right, sb);
		sb.append(node.idx).append("\n");
	}
	
	public static class Node {
		Node right;
		Node left;
		int idx;
		Node(int idx) { this.idx = idx; }
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }	
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}