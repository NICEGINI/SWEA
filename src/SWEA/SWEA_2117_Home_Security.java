/**
 * 
 */
package SWEA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * SWEA 2117 - 홈 방범 서비스
 * NxN크기 ( 5<= N <=20 )
 * 하나의 집이 지불할 수 있는 비용 M ( 1<= M <= 10 )
 * 운영비용은 면적비용과 동일
 */
public class SWEA_2117_Home_Security {
	private static int N, M, map[][], resMax, maxhouse, cost[];
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringTokenizer stt = null;
		
		cost = new int[21];
		for( int k = 0; k < 21; k++) {
			cost[k] = (k+1)*(k+1) + k*k;

		}
		
		for( int test_case = 1; test_case <= T; test_case++) {
			stt = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(stt.nextToken());
			M = Integer.parseInt(stt.nextToken());
			
			map = new int[N][N];
			
			for( int i = 0; i < N; i++) {
				stt = new StringTokenizer(br.readLine(), " ");
				for( int j = 0; j < N; j++) {
					int info = Integer.parseInt(stt.nextToken());
					if(info == 1) maxhouse++;
					map[i][j] = info;
				}
			} // input end
			resMax = 0;
			start_Security();
			System.out.println("#"+test_case+" " +resMax);
		} // test_case end
	}
	
	/** */
	private static void start_Security() {
		for( int k = 0; k <= N; k++) {
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					int home_cnt = 0;
					// 기준점 i,j에서 거리가 k-1안에 집이 있는지 확인하자
					int sj = j-k, ej = j+k;
					// 기준 행 처리
					for (int s = sj; s <= ej; s++) {
						if(s >= 0 && s < N && map[i][s] == 1) home_cnt++;
					}
					
					// 기준행의 위 아래 처리
					for(int s = 1; s <= k; s++) {
						sj = j-(k-s);
						ej = j+(k-s);
						for (int b = sj; b <= ej; b++) {
							if(b >= 0 && b < N && i-s >= 0 && map[i-s][b] == 1) home_cnt++;
							if(b >= 0 && b < N && i+s < N && map[i+s][b] == 1) home_cnt++;
						}
					}
					int home_cost = home_cnt*M;
					if(cost[k] <= home_cost) {
						if(resMax < home_cnt) resMax = home_cnt;
					}
					
					if(cost[k] > home_cost && home_cnt == maxhouse) return;
				}
			}
		}
	}
}
