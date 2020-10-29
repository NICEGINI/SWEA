/**
 * 
 */
package SWEA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 4014 활주로 건설하기
 *
 */
public class SWEA_4014_활주로건설하기 {
	private static int N, X, map[][], totalCnt;
	private static boolean row[][], col[][];
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for(int test_case = 1; test_case <= T; test_case++) {
			StringTokenizer stt = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(stt.nextToken());
			X = Integer.parseInt(stt.nextToken());
			
			map = new int[N][N];
			row = new boolean[N][N];
			col = new boolean[N][N];
			
			totalCnt = 0;
			
			for(int i = 0; i < N; i++) {
				stt = new StringTokenizer(br.readLine());
				for(int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(stt.nextToken());
				}
			} // input end
			
			// 가로 체크
			next_line:
			for(int i = 0; i < N; i++) {
				int cur = map[i][0];
				int cnt = 1;
				int dir = 0; // 0평지 1오르막 2내리막
				for(int j = 1; j < N; j++) {
					if(map[i][j] == cur) { // 이전 값이랑 같은 값
						cnt++;
						dir = 0;
						continue;
					}
					// 차이가 1이 아니라면 다음 라인으로
					if(Math.abs(map[i][j]-cur) != 1) {
						continue next_line;
					}
					
					// 오르막인 경우
					if(map[i][j]-cur == 1 && dir != 1) {
						if(cnt - X >= 0) {
							dir = 1;
							cnt = 1;
							cur = map[i][j];
							continue;
						}
					}
					// 내리막인 경우
					if(cur-map[i][j] == 1 && dir != 2) {
						dir = 2;
						cnt = 1;
						cur = map[i][j];
						continue;
					}
					continue next_line;
				}
				
				if(cnt >= X)
					totalCnt++;
			}
			
			// 세로 체크
			next_line:
				for(int i = 0; i < N; i++) {
					int cur = map[0][i];
					int cnt = 1;
					int dir = 0; // 0평지 1오르막 2내리막
					for(int j = 1; j < N; j++) {
						if(map[j][i] == cur) { // 이전 값이랑 같은 값
							dir = 0;
							cnt++;
							continue;
						}
						// 차이가 1이 아니라면 다음 라인으로
						if(Math.abs(map[j][i]-cur) != 1) {
							continue next_line;
						}
						
						// 오르막인 경우
						if(map[j][i]-cur == 1 && dir != 1) {
							if(cnt - X >= 0) {
								dir = 1;
								cnt = 1;
								cur = map[j][i];
								continue;
							}
						}
						// 내리막인 경우
						if(cur-map[j][i] == 1 && dir != 2) {
							dir = 2;
							cnt = 1;
							cur = map[j][i];
							continue;
						}
						continue next_line;
					}
					
					if(cnt >= X)
						totalCnt++;
				}
			
			System.out.println("#"+test_case+" "+ totalCnt);
		} // test_case end
	}
}
