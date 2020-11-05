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
public class SWEA_5643_키순서_배열_BFS {
	private static int N, M, adj[][], GtCnt, LtCnt, answer;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for(int test_case = 1; test_case <= T; test_case++) {
			N = Integer.parseInt(br.readLine());
			M = Integer.parseInt(br.readLine());
			
			adj = new int[N+1][N+1];
			StringTokenizer stt;
			
			int i, j;
			for (int m = 0; m < M; m++) {
				stt = new StringTokenizer(br.readLine());
				i = Integer.parseInt(stt.nextToken());
				j = Integer.parseInt(stt.nextToken());
				adj[i][j] = 1;
			}
			
			for(int k = 1; k <= N; k++) {
				GtCnt = LtCnt = 0;
				gtBFS(k);
				ltBFS(k);
				if(GtCnt + LtCnt == N-1) answer++;
			}
			System.out.println("#"+test_case+" "+answer);
		}
	}
	
	private static void gtBFS(int start) {
		Queue<Integer> queue = new LinkedList<Integer>();
		boolean[] visited = new boolean[N+1];
		queue.offer(start);
		visited[start] = true;
		
		while(!queue.isEmpty()) {
			int k = queue.poll();
			for (int i = 1; i <= N; i++) {
				if(adj[k][i] == 1 && !visited[i]) {
					queue.offer(i);
					visited[i] = true;
					GtCnt++;
				}
			}
		}
	}
	
	private static void ltBFS(int start) {
		Queue<Integer> queue = new LinkedList<Integer>();
		boolean[] visited = new boolean[N+1];
		queue.offer(start);
		visited[start] = true;
		
		while(!queue.isEmpty()) {
			int k = queue.poll();
			for (int i = 1; i <= N; i++) {
				if(adj[i][k] == 1 && !visited[i]) {
					queue.offer(i);
					visited[i] = true;
					LtCnt++;
				}
			}
		}
	}
}
