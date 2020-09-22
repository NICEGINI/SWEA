/**
 * 
 */
package SWEA;

import java.util.Scanner;

/**
 * 0~9까지 모든 수가 나오고 길이가 N인 모든 삐끗수 구하기
 * 삐끗수 : 1232345같이 인접숫자가 1씩 차이나는 숫자
 * 모든 경우의 수를 구하자.
 * 
 * 단, 0으로 시작할 수 없다.
 */
public class SWEA_7393_Daegue_Fighting {
	private static int N, visit=1<<10;
	private static final int BIGNUM = 1000000000; 
	private static long dp[][][];
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		
		for( int test_case = 1; test_case <= T; test_case++) {
			N = sc.nextInt();
			dp = new long[101][10][visit];
			long sum = 0;
			for( int i = 1; i < 10; i++) {
				dp[1][i][1<<i] = 1;
			}
			
			for(int i = 2; i <= N; i++) {
				for(int j = 0; j <=9; j++) {
					for(int k = 0; k < visit; k++) {
						int bit = k | (1<<j);
						dp[i][j][bit] = (dp[i][j][bit] + 
										((0 < j ? dp[i-1][j-1][k] : 0) + 
										 (j < 9 ? dp[i-1][j+1][k] : 0))%BIGNUM
										)%BIGNUM;
					}
				}
			}
			for(int i = 0; i < 10; i++) {
				sum = (sum+dp[N][i][visit-1])%BIGNUM;
			}
			System.out.println("#"+test_case+" "+sum);
		}// test_case end
	}
}
