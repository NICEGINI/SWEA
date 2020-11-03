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
 * SWEA 5656 - 벽돌깨기
 * 
 */
public class SWEA_5656_벽돌깨기 {
	private static int N, W, H, resMin;
	private static int dx[] = {0, 0, -1, 1};
	private static int dy[] = {-1, 1, 0, 0};
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int test_case = 1; test_case <= T; test_case++) {
			StringTokenizer stt = new StringTokenizer(br.readLine());
			N = Integer.parseInt(stt.nextToken());
			W = Integer.parseInt(stt.nextToken());
			H = Integer.parseInt(stt.nextToken());
			
			int[][]map = new int[H][W];
			
			for(int i = 0; i < H; i++) {
				stt = new StringTokenizer(br.readLine());
				for( int j = 0; j < W; j++) {
					int val = Integer.parseInt(stt.nextToken());
					map[i][j] = val;
				}
			} // input end
			
			resMin = Integer.MAX_VALUE;
			start(0, map);
			
			System.out.println("#"+test_case+" "+resMin);
		} // test_case end
	}
	
	/** dfs로 해야할 것 같아. */
	private static void start(int idx, int[][] map) {
		if(idx == N) { // 모두 이동
			int sum = 0;
			for(int i = H-1; i >= 0; i--) {
				int cnt = 0; // 더 셀 필요가 있는지 확인
				for(int j = 0; j < W; j++) {
					if(map[i][j] != 0 ) { // 남은 블럭만 센다
						sum++;
						continue;
					}
					cnt++; // 여기로 왔다는 소리는 0이라는 소리
				}
				if(cnt == W) break; // 가로 한 줄이 전부 0이면 위는 볼 필요 없음
			}
			if(resMin > sum) { // 최소값 갱신
				resMin = sum;
			}
			return ;
		}
		// 원본 복사
		int tmpmap[][] = new int[H][W];
		for(int i = 0; i < H; i++) {
			for(int j = 0; j < W; j++) {
				tmpmap[i][j] = map[i][j];
			}
		}
		int idxY = 0;
		for(int j = 0; j < W; j++) {
			for(int i = 0; i < H; i++) {
				if(tmpmap[i][j] != 0) {
					idxY = i; // 가장 위의 블럭을 선택
					break;
				}
			}
			breakwall(idxY, j, tmpmap); // 0이 아닌 경우 구슬 발사
			movewall(tmpmap); // 부서지고 남은 떠있는 공간을 바닥으로 내려주자
			start(idx+1, tmpmap); // 현재까지 부서진 값을 가지고 다음으로
			
			// 이전 맵으로 되돌리기
			for(int h = 0; h < H; h++) {
				for(int w = 0; w < W; w++) {
					tmpmap[h][w] = map[h][w];
				}
			}
		}
	}
	
	/** 바닥부터 시작해서 가장 위 까지*/
	private static void movewall(int[][] map) {
		for (int i = H-2; i >= 0; i--) { // 가장 바닥은 신경쓰지 말자
			for (int j = 0; j < W; j++) {
				if(map[i][j] != 0) { // 현재 위치가 0이 아닌 상황에서
					int idx = 1;
					while(true) { // 가장 아래로 닿을 때까지
						if(i+idx >= H) break; // 범위 벗어나면 멈춤
						if(map[i+idx][j] == 0) { // 아래가 빈칸이면
							map[i+idx][j] = map[i+idx-1][j]; // 값 체인지
							map[i+idx-1][j] = 0;
							idx++;
							continue;
						}
						break; // 빈칸이 아니면 멈춤
					}
				}
			}
		}
	}

	/** BFS를 해야할 것 같은데..*/
	private static void breakwall(int y, int x, int[][] map) {
		boolean visited[][] = new boolean[H][W];
		Queue<int[]> queue = new LinkedList<>();
		queue.offer(new int[] {y, x}); // 현재 블럭 위치가 기준
		visited[y][x] = true;
		
		while(!queue.isEmpty()) {
			int[] pos = queue.poll();
			
			if(map[pos[0]][pos[1]] == 1) { // 해당 위치 값이 1이면 다음으로
				map[pos[0]][pos[1]] = 0;
				continue;
			}
			// 1이상이면
			// 현재 부서진 벽돌의 값
			int val = map[pos[0]][pos[1]];
			for(int d = 0; d < 4; d++) {
				int next_y = pos[0];
				int next_x = pos[1];
				for(int i = 1; i < val; i++) { // 현재 값보다 1작게 해서 퍼져야한다.
					next_y += dy[d];
					next_x += dx[d];
					if(rangeCheck(next_y, next_x)) break; // 범위 체크 벗어나면 멈춤
					if(visited[next_y][next_x]) continue; // 방문했으면 다음으로
					if(map[next_y][next_x] == 0) continue; // 0이면 다음으로
					queue.offer(new int[] {next_y, next_x});
					visited[next_y][next_x] = true;
				}
			}
			// 현재 위치 벽돌 파괴
			map[pos[0]][pos[1]] = 0;
		}
	}
	
	// 범위 체크
	private static boolean rangeCheck(int y, int x) {
		if(y < 0 || y >= H || x < 0 || x >= W) return true;
		return false;
	}
}
