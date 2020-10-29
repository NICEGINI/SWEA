/**
 * 
 */
package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * @author YSM
 *
 */
public class SWEA_1249_보급로_Dijstra {
	static int N, INF=Integer.MAX_VALUE;
	static int map[][];
	static int dr[] = {-1,1,0,0};
	static int dc[] = {0,0,-1,1};
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int TC = Integer.parseInt(br.readLine());
		for(int test_case = 1; test_case <= TC; test_case++) {
			N = Integer.parseInt(br.readLine());
			map = new int[N][N];
			
			for(int i = 0; i < N; i++) {
				char ch[] = br.readLine().toCharArray();
				for(int j = 0; j < N; j++) {
					map[i][j] = ch[j] - '0';
				}
			} // input end
			
			System.out.println("#"+test_case+" "+dijkstra(0,0,N-1,N-1));
		}// test_case end
	}
	
	private static int dijkstra(int startX, int startY, int endX, int endY) {
		boolean[][] visited = new boolean[N][N];
		int[][] minTime = new int[N][N];
		
		// 모든 최소 비용 최대값으로 초기화
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				minTime[i][j] = INF;
			}
		}
		
		// int[] : {r, c, cost}
		PriorityQueue<int[]> pQueue = new PriorityQueue<int[]>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return Integer.compare(o1[2], o2[2]); // cost비교 후 오름차순의 결과
			}
		});
		
		minTime[startX][startY] = 0;
		pQueue.offer(new int[] {startX, startY, minTime[startX][startY]});
		
		int r = 0, c = 0, cost = 0, nr, nc, current[];
		while(true) {
			//1. 미방문 정점 중에 최소 비용의 정점 찾기
			current = pQueue.poll();
			r = current[0];
			c = current[1];
			cost = current[2];
			
			if(visited[r][c]) continue;
			
			visited[r][c] = true;
			
			// 선택한 정점이 도착정점이라면 끝내기
			if(r == endX && c == endY) return cost;
			
			//2. 선택된 정점을 경유지로 하여 미방문 정점들의 최소 비용 갱신
			// 선택된 정점의 인접 정점은 4방에 있는 정점. 4방 탐색 수행
			for(int d = 0; d < 4; d++) {
				nr = r + dr[d];
				nc = c + dc[d];
				if(nr >= 0 && nr < N && nc >=0 && nc < N && !visited[nr][nc] 
						   && minTime[nr][nc] > cost+map[nr][nc]) {
					minTime[nr][nc] = cost+map[nr][nc];
					// 갱신된 최소 비용을 PQ에 넣는다
					pQueue.offer(new int[] {nr, nc, minTime[nr][nc]});
				}
			}
		}
	}
}
