/**
 * 
 */
package SWEA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * @author YSM
 *
 */
public class SWEA_1249_보급로 {
	private static class Pos implements Comparable<Pos>{
		int y, x, time;

		public Pos(int y, int x, int time) {
			this.y = y;
			this.x = x;
			this.time = time;
		}

		@Override
		public int compareTo(Pos o) {
			return Integer.compare(time, o.time);
		}
	}
	
	private static int N, map[][], resMin;
	private static int dx[] = {0,0,-1,1};
	private static int dy[] = {-1,1,0,0};
	private static boolean visited[][];
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for(int test_case = 1; test_case <= T; test_case++) {
			N = Integer.parseInt(br.readLine());
			
			map = new int[N][N];
			char tmp[] = new char[N];
			visited = new boolean[N][N];
			
			StringTokenizer stt;
			for(int i = 0; i < N; i++) {
				stt = new StringTokenizer(br.readLine(), " ");
				tmp = stt.nextToken().toCharArray();
				for(int j = 0; j < N; j++) {
					map[i][j] = (int)(tmp[j]-'0');
				}
			} // input end
			
			bfs();
			
			System.out.println("#"+test_case+" "+resMin);
		} // test_case end
	}
	
	private static void bfs() {
		PriorityQueue<Pos> pQueue = new PriorityQueue<>();
		pQueue.offer(new Pos(0,0,0)); // 좌 상단 처음 시작 위치
		visited[0][0] = true;
		
		while(!pQueue.isEmpty()) {
			Pos pos = pQueue.poll();
			
			if(pos.y == N-1 && pos.x == N-1) { // 가장 먼저 도착한 객체가 최소 값
				resMin = pos.time;
				break;
			}
			
			for(int k = 0; k < 4; k++) {
				int next_y = pos.y + dy[k];
				int next_x = pos.x + dx[k];
				
				if(rangeCheck(next_y, next_x)) continue;
				if(visited[next_y][next_x]) continue;
				
				pQueue.offer(new Pos(next_y, next_x, pos.time + map[next_y][next_x]));
				visited[next_y][next_x] = true;
			}
		}
	}
	
	private static boolean rangeCheck(int y, int x) {
		if(y < 0 || y >= N || x < 0 || x >= N) return true;
		return false;
	}
}
