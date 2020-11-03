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
public class SWEA_1953_탈주범검거 {
	private static int N, M, MY, MX, Time, map[][], totalCnt;
	private static int dx[] = {0, 0, -1, 1};
	private static int dy[] = {-1, 1, 0, 0};
	private static int pipe[][] = {
									{0, 0, 0, 0}, 	
									{1, 1, 1, 1}, // 1번부터 시작 
									{1, 1, 0, 0},
									{0, 0, 1, 1},
									{1, 0, 0, 1},
									{0, 1, 0, 1},
									{0, 1, 1, 0},
									{1, 0, 1, 0}
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
			
			for (int i = 0; i < N; i++) {
				stt = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < M; j++) {
					map[i][j] = Integer.parseInt(stt.nextToken());
				}
			} // input end
			
			totalCnt = 0;
			move();
			
			System.out.println("#"+test_case+" "+totalCnt);
		} // test_case end
	}
	
	/** 범인의 위치를 기준으로 시간별 bfs */
	private static void move() {
		int time = 0;
		boolean visited[][] = new boolean[N][M];
		Queue<int[]>queue = new LinkedList<int[]>();
		queue.offer(new int[] {MY,MX}); // 처음위치 맨홀
		visited[MY][MX] = true;
		
		while(!queue.isEmpty()) {
			if(time == Time) break;
			int qSize = queue.size();
			
			// 
			for(int s = 0; s < qSize; s++) {
				int[] pos = queue.poll();
				
				int type = map[pos[0]][pos[1]];
				
				// 현재 파이프 타입에 따라 갈 수 있는 길이 나뉘어 진다.
				for(int d = 0; d < 4; d++) {
					if(pipe[type][d] != 0) { // 연결할 수 있는 경우만 다음으로
						int next_y = pos[0] + dy[d];
						int next_x = pos[1] + dx[d];
						
						if(rangeCheck(next_y,next_x)) continue; // 범위 체크
						if(visited[next_y][next_x]) continue; // 방문 체크
						int nextType = map[next_y][next_x]; // 다음 파이프의 타입
						
						if(d == 0) { // 지금 파이프 위쪽 0
							if(pipe[nextType][1] == 0) continue;
						} else if(d == 1) { // 지금 파이프 아래쪽 1
							if(pipe[nextType][0] == 0) continue;
						} else if(d == 2) { // 지금 파이프 왼쪽 2
							if(pipe[nextType][3] == 0) continue;
						} else { // 지금 파이프 오른쪽 3
							if(pipe[nextType][2] == 0) continue;
						}
						// 다음 파이프가 연결 가능한 파이프인 경우만 넣어주자
						queue.offer(new int[] {next_y, next_x});
						visited[next_y][next_x] = true;
					}
				}
				totalCnt++;
			}
			time++;
		}
	}
	
	private static boolean rangeCheck(int y, int x) {
		if(y < 0 || y >= N || x < 0 || x >= M) return true;
		return false;
	}
}
