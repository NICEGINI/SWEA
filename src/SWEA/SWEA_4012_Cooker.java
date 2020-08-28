/**
 * 
 */
package SWEA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author YSM
 *
 */
public class SWEA_4012_Cooker {
	static int map[][], N, resMin;
	static boolean visited[];
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer stt = new StringTokenizer(br.readLine());
		
		int T = Integer.parseInt(stt.nextToken());
		
		for( int t = 1; t <= T; t++) {
			stt = new StringTokenizer(br.readLine());
			N = Integer.parseInt(stt.nextToken());
			
			map = new int[N][N];
			visited = new boolean[N];
			
			for( int i = 0; i < N; i++) {
				stt = new StringTokenizer(br.readLine());
				for( int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(stt.nextToken());
				}
			}
			
			resMin = Integer.MAX_VALUE;
			start_cook(0,0);
			
			System.out.println("#" + t + " " + resMin);
		} // test_case end
	}
	
	/** 순열을 이용해서 중복되지않게 재료를 선택한 다음에 차이를 계산*/
	private static void start_cook(int cnt, int start) {
		if(cnt == N) {
			return;
		}
		if(cnt == N/2) {
			// 고를수있는 모든 경우를 가지고 계산 시작.
			cook();
			return ;
		}
		
		for( int i = start; i < N; i++) {
			visited[i] = true;
			start_cook(cnt+1, i+1);
			visited[i] = false;
		}
	}

	/** */
	private static void cook() {
		int first_sum = 0;
		int sec_sum = 0;
		
		for( int i = 0; i < N-1; i++) {
			for( int j = i+1; j < N; j++) {
				if(visited[i] != visited[j]) continue;
				
				if(visited[i]) {
					first_sum += map[i][j] + map[j][i];
				} else {
					sec_sum += map[i][j] + map[j][i];
				}
			}
		}
		
		int res = Math.abs(first_sum - sec_sum);
		resMin = Math.min(res, resMin);
	}
}
