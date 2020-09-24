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
public class SWEA_3307_최장증가부분수열 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		for(int test_case = 1; test_case <= T; test_case++) {
			int N = Integer.parseInt(br.readLine());
			
			int[] arr = new int[N];
			int[] LIS = new int[N];
			
			StringTokenizer stt = new StringTokenizer(br.readLine(), " ");
			for(int i = 0; i < N; i++) {
				arr[i] = Integer.parseInt(stt.nextToken());
			}
			
			int max = 0;
			
			for( int i = 0; i < N; i++) {
				LIS[i] = 1; // 자신만으로 LIS를 구성했을때 1
				// 자신(i)의 앞에 있는 원소들과 비교
				for (int j = 0; j <= i-1; j++) {
					// 앞쪽 원소보다 자신이 큰 경우에만 넣을 수 있다.
					if(arr[j] < arr[i] && LIS[i] < LIS[j]+1) {
						LIS[i] = LIS[j] + 1;
					}
				}
				// 현 원소의 LIS값과 전체 최대값과 비교, 갱신
				if(max < LIS[i]) max = LIS[i];
			}
			System.out.println("#"+test_case+" "+max);
		}
	}
}
