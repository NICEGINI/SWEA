/**
 * 
 */
package SWEA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * SWEA 2115 벌꿀채취
 * 조합 + 부분집합
 * 
 * 0/1 배낭채우기로도 풀어진다
 *
 */
public class SWEA_2115_벌꿀채취_조합_부분집합 {
	static int N, M, C, map[][], maxMap[][], resMax;
	static PriorityQueue<Integer>pQueue;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for (int test_case = 1; test_case <= T; test_case++) {
			
			StringTokenizer stt = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(stt.nextToken());
			M = Integer.parseInt(stt.nextToken());
			C = Integer.parseInt(stt.nextToken());
			
			map = new int[N][N];
			maxMap = new int[N][N];
			
			for (int i = 0; i < N; i++) {
				stt = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(stt.nextToken()); 
				}
			} // input end
			
			System.out.println("#"+test_case+" "+getMaxBenefit());
		} // test_case end
	}
	
	private static int getMaxBenefit() {
		makeMaxMap();
		return processCombination();
	}

	private static void makeMaxMap() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j <= N-M; j++) {
				makeMaxSubset(i, j, 0, 0, 0);
			}
		}
	}
	
	// 부분집합의 합을 구하자
	private static void makeMaxSubset(int i, int j, int cnt, int sum, int powSum) {
		if(sum > C) return ;
		if(cnt == M) {
			if(maxMap[i][j-M] < powSum) {
				maxMap[i][j-M] = powSum;
			}
			return ;
		}
		
		// 선택
		makeMaxSubset(i, j+1, cnt+1, sum+map[i][j], powSum+map[i][j]*map[i][j]);
		
		// 비선택
		makeMaxSubset(i, j+1, cnt+1, sum, powSum);
	}

	/** 조합을 구하자 */
	private static int processCombination() {
		int max = 0, aBenefit = 0, bBenefit = 0;
		for(int i = 0; i < N; i++) {
			for(int j = 0; j <= N-M; j++) { // 일꾼 A 선택 후 고정
				aBenefit = maxMap[i][j];
				// 일꾼 B 처리
				// 같은 행
				bBenefit = 0;
				for(int j2 = j+M; j2 <= N-M; j2++) {
					if(bBenefit < maxMap[i][j2]) bBenefit = maxMap[i][j2];
				}
				// 다른 행
				for(int i2 = i+1; i2 < N; i2++) {
					for(int j2 = 0; j2 <= N-M; j2++) {
						if(bBenefit < maxMap[i2][j2]) bBenefit = maxMap[i2][j2];
					}
				}
				if(max < aBenefit + bBenefit) max = aBenefit + bBenefit;
			}
		}
		return max;
	}
}
