import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		// br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		ArrayList<Project> projects = new ArrayList<>();
		String fileName = "e_exceptional_skills.in";
    File inFile = new File("D:\\dev-blog-git-page\\input_data (1)\\" + fileName + ".txt");
    FileReader filereader = new FileReader(inFile);
		br = new BufferedReader(filereader);
		File outFile = new File("D:\\dev-blog-git-page\\input_data (1)\\" + fileName + "____output.txt");
    BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));	
		
    int[] arr = getArr();
    int c = arr[0], p = arr[1];
		String[] line;
		HashMap<String, Person> person = new HashMap<>();
		HashMap<String, String> candidate = new HashMap<>();
		HashMap<String, Integer> endDay = new HashMap<>();

		for(int i = 0; i < c; i++) {
			line = getLine();
			String pname = line[0];
			int skillNum = toi(line[1]);
			Person ptmp = new Person(pname);
			for(int j = 0; j < skillNum; j++) {
				line = getLine();
				String skillName = line[0].trim();
				int skillLevel = toi(line[1]);
				endDay.put(skillName, 0);
				if(j == 0) candidate.put(skillName, pname);
				ptmp.skills.put(skillName, skillLevel);
			}
			person.put(pname, ptmp);
		}

		for(int i = 0; i < p; i++) {
			line = getLine();
			String pname = line[0];
			int day = toi(line[1]), score = toi(line[2]), bb = toi(line[3]), role = toi(line[4]);
			ArrayList<Skill> tmp = new ArrayList<>();
			HashSet<String> duplicate = new HashSet<>();
			boolean noAdding = false;
			for(int j = 0; j < role; j++) {
				line = getLine();
				String skillName = line[0].trim();
				int level = toi(line[1]);
				if(duplicate.contains(skillName)) noAdding = true;
				duplicate.add(skillName);
				if(level > 10) noAdding = true;
				tmp.add(new Skill(skillName, level));
			}
			if(noAdding) continue;
			Project ptmp = new Project(pname, day, score, bb, role);
			ptmp.skills = tmp;
			projects.add(ptmp);
		}

		// int handleCnt = 0;
		projects.sort((l, r) -> l.day - r.day);
		bw.flush();
		bw.write((int)projects.size());
		println(projects.size());
		bw.newLine();
		for(Project pro: projects) {
			bw.write(pro.name);
			bw.newLine();
			int max = 0;
			for(Skill skill: pro.skills) {
				max = Math.max(max, endDay.get(skill.name));
				bw.write(candidate.get(skill.name) + " ");
			}
			bw.newLine();
			for(Skill skill: pro.skills) {
				endDay.put(skill.name, max + pro.day);
			}
			// handleCnt++;
		}
		
		// for(Project pro : projects) {
		// 	for(Skill sk : pro.skills) {
		// 		println(sk.name + " , " + sk.level);
		// 		bw.write(sk.name + " , " + sk.level);
    // 	  bw.newLine();
		// 	}
		// }
		// while((line = br.readLine()) != null){
    //     System.out.println(line);
    // }
		br.close();
		bw.close();
	}

	static class Project {
		String name;
		int day, score, bb, roles;
		ArrayList<Skill> skills;
		Project(String name, int day, int score, int bb, int roles) {
			this.name = name;
			this.day = day;
			this.score = score;
			this.bb = bb;
			this.roles = roles;
		}
	}

	static class Person {
		String name;
		HashMap<String, Integer> skills = new HashMap();
		boolean can = true;
		Person(String name) { this.name = name; }
	}

	static class Skill {
		String name;
		int level;
		Skill(String name, int level) { this.name = name; this.level = level; }
	}


	static int toi(String s) { return Integer.parseInt(s); }
	static long tol(String s) { return Long.parseLong(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }	
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}
