/**
 * 
 */
package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * SWEA 7699번
 * 수지의 수지맞는 여행
 * 1행 1열에서 시작.
 * 같은 명물을 2번 이상 가지 않게 해서 갈 수 있는 최대 경우의 수
 * DFS를 이용해서 구현.
 * A~Z까지 26가지의 경우를 한 번 방문한 경우에는 장애물로 생각한다.
 *
 */
public class SWEA_7699_Suzi_travel {
	private static char[][] map;
	private static boolean[] visited;
	private static int maxView, R, C;
	// 4방 탐색을 위한 벡터
	private static int dx[] = {0, 0, -1, 1};
	private static int dy[] = {-1, 1, 0, 0};


	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer stt = new StringTokenizer(br.readLine());

		int T = Integer.parseInt(stt.nextToken());

		for( int test_case = 1; test_case <= T; test_case++) {
			stt = new StringTokenizer(br.readLine());

			R = Integer.parseInt(stt.nextToken()); // 행
			C = Integer.parseInt(stt.nextToken()); // 열

			map = new char[R][C];
			// A ~ Z까지 26개
			visited = new boolean[26];
			maxView = 0;

			for( int i = 0; i < R; i++) {
				stt = new StringTokenizer(br.readLine());
				map[i] = stt.nextToken().toCharArray();
			}
			
			// DFS
			start_travel(0,0,1);
			System.out.println("#"+ test_case + " " + maxView);
		}
	}

	/** DFS 이용해보자*/
	private static void start_travel(int y, int x, int cnt) {
		visited[map[y][x] - 'A'] = true; // 현재 알파벳의 명물 방문
		maxView = cnt > maxView ? cnt : maxView; // 최대값 갱신
		// 이미 모든 명물을 보았다.
		if(maxView == 26) return;
		
		// 4방 탐색
		for( int k = 0; k < 4; k++) {
			int next_y = y + dy[k];
			int next_x = x + dx[k];

			if( next_y < 0 || next_y >= R || next_x < 0 || next_x >= C
					|| visited[map[next_y][next_x] - 'A']) continue;
			
			// 다음 위치 좌표를 이용해서 DFS
			start_travel(next_y, next_x, cnt+1);

		}
		// 현재 알파벳의 명물 방문을 안 한 것으로 되돌림
		visited[map[y][x] - 'A'] = false;
	}
}
