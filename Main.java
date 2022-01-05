class Solution {
    public List<List<String>> partition(String s) {
        int len = s.length();
        char[] ch = s.toCharArray();
        boolean[][] pal = new boolean[len][len];
        for(int i = 0; i < len - 1; i++) {
          pal[i][i] = true;
          if(ch[i] == ch[i+1]) pal[i][i+1] = true;
        }
        
        pal[len-1][len-1] = true;
        for(int di = 2; di < len; di++) 
          for(int i = 0; i < len - di; i++) 
            if(ch[i] == ch[i+di] && pal[i+1][i+di-1]) pal[i][i+di] = true; 

        LinkedList<Integer> ll = new LinkedList<>();
        List<List<String>> list = new LinkedList<>();
        dfs(pal, ch, -1, ll, list, len);
        return list;
    }

    void dfs(boolean[][] pal, char[] ch, int cur, LinkedList<Integer> ll, List<List<String>> list, int len) {
      if(cur == len - 1) {
        LinkedList<String> temp = new LinkedList<>();
        int startIdx = 0;
        for(int idx: ll) {
          temp.add(String.copyValueOf(ch, startIdx, idx - startIdx + 1));
          startIdx = idx + 1;
        }
        list.add(temp);
        return;
      }
      for(int i = cur + 1; i < len; i++) {
        if(pal[cur+1][i]) {
          ll.add(i);
          dfs(pal, ch, i, ll, list, len);
          ll.removeLast();
        }
      }
    }
    
    <T> void print(T s) { System.out.print(s); }
}