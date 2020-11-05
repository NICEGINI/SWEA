/**
 * 
 */
package SWEA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author YSM
 *
 */
public class SWEA1868_파핑파핑 {
	static class Pos{
		int y, x;

		public Pos(int y, int x) {
			this.y = y;
			this.x = x;
		}
	}
	static int N, clickCnt;
	static char[][] map;
	static boolean[][] visited;
	static int dx[]= {0,0,-1,1,-1,-1,1,1};
	static int dy[]= {-1,1,0,0,-1,1,-1,1};
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int test_case = 1; test_case <= T; test_case++) {
			N = Integer.parseInt(br.readLine());
			map = new char[N][];
			visited = new boolean[N][N];
			
			for(int i = 0; i < N; i++) {
				map[i] = br.readLine().toCharArray();
			}
			
			clickCnt = 0;
			process();
			
			System.out.println("#"+test_case+" "+clickCnt);
		} // test_case end
	}
	
	private static void process() {
		for(int i = 0; i < N; i++) { // 모든 경우 체크 
			for(int j = 0; j < N; j++) {
				if(map[i][j] == '*') continue;
				if(visited[i][j]) continue;
				int mineCnt = 0;
				for(int d = 0; d < 8; d++) {
					int next_y = i + dy[d];
					int next_x = j + dx[d];
					if(rangeCheck(next_y, next_x)) continue;
					if(visited[next_y][next_x]) continue;
					if(map[next_y][next_x] == '*') mineCnt++;
				}
				if(mineCnt == 0) { // 사방에 지뢰가 하나도 없는 경우에
					bfs(i,j); // 해당 좌표를 기준으로 bfs 시행
					clickCnt++;
				}
			}
		}
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				// 아직 .인 경우는 클릭 안한 경우밖에 없다.
				if(map[i][j] == '.') clickCnt++;
			}
		}
	}
	
	private static void bfs(int y, int x) {
		Queue<Pos> queue = new LinkedList<>();
		queue.offer(new Pos(y,x));
		visited[y][x] = true;
		
		while(!queue.isEmpty()) {
			Pos pos = queue.poll();
			map[pos.y][pos.x] = 'c';
			
			int mineCnt = 0;
			for(int d = 0; d < 8; d++) {
				int next_y = pos.y + dy[d];
				int next_x = pos.x + dx[d];
				if(rangeCheck(next_y, next_x)) continue;
				if(visited[next_y][next_x]) continue;
				if(map[next_y][next_x] == '*') mineCnt++;
			}
			if(mineCnt == 0) { // 사방에 지뢰가 하나도 없는 경우에
				for(int d = 0; d < 8; d++) {
					int next_y = pos.y + dy[d];
					int next_x = pos.x + dx[d];
					if(rangeCheck(next_y, next_x)) continue;
					if(visited[next_y][next_x]) continue;
					queue.offer(new Pos(next_y, next_x));
					visited[next_y][next_x] = true;
				}
			}
		}
	}

	private static boolean rangeCheck(int y, int x) {
		if(y < 0 || y >= N || x < 0 || x >= N) return true;
		return false;
	}
}
