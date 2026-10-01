// package track;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;

public class App {
  static int n, m;
	static int[] di, dj;
	static int min = Integer.MAX_VALUE;
  static boolean[][] map;
  public static void main(String[] args) {
    // ---
    // This is a sample code to use stdin and stdout.
    // Edit and remove this code as you like.
    String[] lines = getStdin();
		String[] line = lines[0].split(" ");
		n = toi(line[0]);
		m = toi(line[1]);
		di = new int[] { 1, 0, -1, 0 };
		dj = new int[] { 0, 1, 0, -1 };
		int time = 0;
		//direction 0 : north, 1: east 2: south 3: west
		map = new boolean[n][m];
		for(boolean[] ar: map) Arrays.fill(ar, true);
		Point hunter = new Point(0, 0, 0);
		int[][] dis = new int[n][m];
		for(int[] arr: dis) Arrays.fill(arr, -1);
		ArrayList<Point> onis = new ArrayList<>();

		for(int i = 1; i <= n; i++) {
			for(int j = 0; j < m; j++) {
				char ch = lines[i].charAt(j);
				if(ch == '#') map[i - 1][j] = false;
				else if(ch == 'H') {
					hunter.i = i - 1;
					hunter.j = j;
				}
				else if(ch == 'D') onis.add(new Point(i - 1, j, 1));
			}
		}

		boolean[] visit = new boolean[onis.size()];
		storeBfs(map, hunter, dis);
    // println("map");
    // for(int i = 0; i < n; i++) {
    //   for(int j = 0; j < m; j++) print(dis[i][j]+ " ");
    //   println("");
    // }

		for(Point oni: onis) {
			if(dis[oni.i][oni.j] == -1) {
				print(-1);
				return;
			}
      if(oni.i == hunter.i && oni.j == hunter.j) {
        print(0);
        return;
      }
		}


		while(true) {
			for(Point oni: onis) {
				int ii = oni.i, jj = oni.j, dir = oni.dir;
				int toI = ii +di[dir], toJ = jj + dj[dir];
				if(!properCoord(toI, toJ) || !map[toI][toJ]) {
					oni.dir++;
					if(oni.dir > 3) oni.dir -= 4;
				} else {
					oni.i = toI;
					oni.j = toJ;
				}
			}
			int minTime = Integer.MAX_VALUE;

      boolean noZero = true;
      for(int i = 0; i < onis.size(); i++) {
        Point oni = onis.get(i);
        if(dis[oni.i][oni.j] != 0) allZero = false;
        else {
          onis.remove(i);
          if(onis.size() == 0) {
            print(time + 1);
            return;
          }
          time++;
          continue;
        }
      }

			for(int i = 0; i < onis.size(); i++) {
				Point oni = onis.get(i);
				ArrayList<Point> copy = new ArrayList<>();
				for(int j = 0; j < onis.size(); j++) {
					if(j == i) continue;
					copy.add(onis.get(j));
				}
				min = Integer.MAX_VALUE;
				recursive(copy, onis.get(i), 0);
        // println(" min : " + min + " , " + "(" + oni.i + " , " + oni.j + " ) " + dis[oni.i][oni.j]);
        if(min == Integer.MAX_VALUE) minTime = Math.min(minTime, dis[oni.i][oni.j]);
				else minTime = Math.min(minTime, dis[oni.i][oni.j] + min);
			}
			time++;
      println(time + " ,minTime: " + minTime);
      for(Point oni: onis) {
        println(oni.i + " : " + oni.j);
      }
			if(time >= minTime) {
				print(time);
				return;
			}
		}
  }

	static void recursive(ArrayList<Point> points, Point from, int dis) {
		if(points.size() == 1) {
			int distance = dis + bfs(from, points.get(0));
			min = Math.min(distance, min);
			return;
		} 
		for(int i = 0; i < points.size(); i++) {
			Point p = points.get(i);
			points.remove(i);
			recursive(points, p, dis + bfs(from, p));
			points.add(i, p);
		}
	}

	static int bfs(Point p1, Point p2) {
    if(p1.i == p2.i && p1.j == p2.j) return 0;
		int dis = 0;
		boolean[][] visit = new boolean[n][m];
		Queue<BFS> q = new LinkedList<>();
		q.add(new BFS(p1.i, p1.j, 0));
		while(!q.isEmpty()) {
			BFS cur = q.poll();
			if(visit[cur.i][cur.j]) continue;
			visit[cur.i][cur.j] = true;

			int ii = cur.i, jj = cur.j;
			for(int i = 0; i < 4; i++ ) {
				int I = ii + di[i], J = jj + dj[i];
				if(!properCoord(I, J) || !map[I][J]) continue;
				if(I == p2.i && J == p2.j) {
					return cur.dis + 1;
				}
				q.add(new BFS(I, J, dis + 1));
			}
		}
		return -1;
	}

	static void storeBfs(boolean[][] map, Point hunter, int[][] dis) {
		boolean[][] visit = new boolean[n][m];
		Queue<BFS> q = new LinkedList<>();
		q.add(new BFS(hunter.i, hunter.j, 0));

		while(!q.isEmpty()) {
			BFS cur = q.poll();
			if(visit[cur.i][cur.j]) continue;
			visit[cur.i][cur.j] = true;
			dis[cur.i][cur.j] = cur.dis;

			int ii = cur.i, jj = cur.j;
			for(int i = 0; i < 4; i++ ) {
				int I = ii + di[i], J = jj + dj[i];
				if(!properCoord(I, J) || !map[I][J]) continue;
				q.add(new BFS(I, J, cur.dis + 1));
			}
		}
	}

	static boolean properCoord(int i, int j) {
		if(i < 0 || j < 0 || i >= n || j >= m) return false;
		return true;
	}

	static class Point {
		int i, j, dir;
		public Point(int i, int j, int dir) { this.i = i; this.j = j; this.dir = dir; }
	}

	static class BFS {
		int i, j, dis;
		public BFS(int i, int j, int dis) { this.i = i; this.j = j; this.dis = dis; }
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }

  private static String[] getStdin() {
  //   Scanner scanner = new Scanner(System.in);
  //   ArrayList<String> lines = new ArrayList<>();
  //   while (scanner.hasNext()) {
  //     lines.add(scanner.nextLine());
  //   }
  //   return lines.toArray(new String[lines.size()]);
  // }
    ArrayList<String> lines = new ArrayList<>();
    lines.add("2 2");
    lines.add("D.");
    lines.add("DH");
    return lines.toArray(new String[lines.size()]);
  }
}
