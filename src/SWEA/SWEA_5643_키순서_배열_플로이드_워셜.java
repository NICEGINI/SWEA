
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
 * @author YSM
 *
 */
public class SWEA_5643_키순서_배열_플로이드_워셜 {
	private static int N, M, adj[][];
	private static int[] gtCnt, ltCnt;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for(int test_case = 1; test_case <= T; test_case++) {
			N = Integer.parseInt(br.readLine());
			M = Integer.parseInt(br.readLine());
			
			adj = new int[N+1][N+1]; // 자신보다 큰 관계 저장
			gtCnt = new int[N+1];
			ltCnt = new int[N+1];
			StringTokenizer stt;
			
			for (int m = 0; m < M; m++) {
				stt = new StringTokenizer(br.readLine());
				int i = Integer.parseInt(stt.nextToken());
				int j = Integer.parseInt(stt.nextToken());
				adj[i][j] = 1;
			}
			
			for (int k = 1; k <= N; k++) { // 경유지
				for (int i = 1; i <= N; i++) { // 출발지
					if(i == k) continue; // 경유지가 출발지 pass
					for(int j = 1; j <= N; j++) { // 도착지
						// 경유지가 도착지, 출발지가 도착지, 이미 관계를 아는 경우에는 pass
						if(k == j || i == j || adj[i][j] == 1) continue;
						if(adj[i][k] == 1 && adj[k][j] == 1) adj[i][j] = 1;
					}
				}
			}
			
			int answer = 0;
			
			for(int i = 1; i <= N; i++) {
				for(int j = 1; j <= N; j++) {
					adj[0][j] += adj[i][j];
					adj[i][0] += adj[i][j];
				}
			}
			
			for(int k = 1; k <= N; k++) {
				if(adj[k][0] + adj[0][k] == N-1) answer++;
			}
			
			System.out.println("#"+test_case+" "+answer);
		}
	}
}
