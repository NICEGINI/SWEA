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
public class SWEA_5643_키순서_배열_DFS2 {
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
			
			int i, j;
			for (int m = 0; m < M; m++) {
				stt = new StringTokenizer(br.readLine());
				i = Integer.parseInt(stt.nextToken());
				j = Integer.parseInt(stt.nextToken());
				adj[i][j] = 1;
			}
			
			int answer = 0;
			
			for(int k = 1; k <= N; k++) {
				dfs(k, k, new boolean[N+1]);
			}
			for(int k = 1; k <= N; k++) {
				if(gtCnt[k] + ltCnt[k] == N-1) answer++;
			}
			System.out.println("#"+test_case+" "+answer);
		}
	}
	
	// src : 출발 학생번호. src보다 큰 학생을 따라 이동. src 자신이 누구보다 작은지 체크
	private static void dfs(int src, int cur, boolean[] visited) {
		visited[cur] = true; // src < cur
		for (int i = 1; i <= N; i++) {
			if(adj[cur][i] == 1 && !visited[i]) { // src < cur < i
				gtCnt[src]++;
				ltCnt[i]++;
				dfs(src, i, visited);
			}
		}
	}
}
