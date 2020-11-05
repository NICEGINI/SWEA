/**
 * 
 */
package SWEA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * SWEA 1953 - 탈주범 검거
 * 
 */
public class SWEA_1953_탈주범검거_DFS_Solution {
	private static int N, M, MY, MX, Time, map[][], visited[][];
	// 상 좌 우 하
	private static int dx[] = {0, -1, 1, 0};
	private static int dy[] = {-1, 0, 0, 1};
	private static String[] type = { // 더했을때 3이 나오게 
									null,
									"0312",// 1
									"03",  // 2
									"12",  // 3
									"02",  // 4
									"32",  // 5
									"31",  // 6
									"01"   // 7
							};
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for (int test_case = 1; test_case <= T; test_case++) {
			StringTokenizer stt = new StringTokenizer(br.readLine(), " ");
			
			N = Integer.parseInt(stt.nextToken());
			M = Integer.parseInt(stt.nextToken());
			MY = Integer.parseInt(stt.nextToken());
			MX = Integer.parseInt(stt.nextToken());
			Time = Integer.parseInt(stt.nextToken());
			
			map = new int[N][M];
			visited = new int[N][M];
			
			for (int i = 0; i < N; i++) {
				stt = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < M; j++) {
					map[i][j] = Integer.parseInt(stt.nextToken());
					visited[i][j] = Integer.MAX_VALUE;
				}
			} // input end
			
			dfs(MY, MX, 1);
			
			System.out.println("#"+test_case+" "+getCount());
		} // test_case end
	}

	/** 시간안에 갈 수있는 모든 곳을 체크하며 DFS 수행 */
	private static void dfs(int y, int x, int time) {
		visited[y][x] = time;
		if(time == Time) return ;
		
		String info = type[map[y][x]];
		int next_y, next_x, dir;
		for(int d = 0; d < info.length(); d++) {
			dir = info.charAt(d) -'0';
			next_y = y + dy[dir];
			next_x = x + dx[dir];
			
			if(rangeCheck(next_y, next_x)) continue; // 범위체크
			if(map[next_y][next_x] == 0) continue; // 빈칸체크
			if(!type[map[next_y][next_x]].contains(Integer.toString(3-dir))) continue; // 방향체크
			if(visited[next_y][next_x] < time) continue; // 시간체크
			dfs(next_y, next_x, time+1);
		}
	}
	
	/** 시간동안 지나간 모든 위치의 수를 세어서 리턴 */
	private static int getCount() {
		int count = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if(visited[i][j] != Integer.MAX_VALUE) ++count;
			}
		}
		return count;
	}

	private static boolean rangeCheck(int y, int x) {
		if(y < 0 || y >= N || x < 0 || x >= M) return true;
		return false;
	}
}
