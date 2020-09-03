/**
 * 
 */
package SWEA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * SWEA 1251 - 하나로
 * 주어진 모든 섬을 하나로 연결하자.
 * 단, 세율E, 길이L의 비용이 발생
 * 비용 : E*L^2
 *
 */
public class SWEA_1251_Hanaro {
	static class Island { // 섬 위치 정보
		int y, x;

		public Island(int y, int x) {
			this.y = y;
			this.x = x;
		}
	}

	// 섬간에 이어진 간선 정보
	static class Edge implements Comparable<Edge> {
		int to; // 다음 섬
		long weight; // 가중치

		public Edge(int to, long weight) {
			this.to = to;
			this.weight = weight;
		}

		// long타입 크기 비교
		public int compareTo(Edge o) {
			return Long.compare(this.weight, o.weight);
		}
	}

	private static int N; // 섬 개수
	// minEdge : 최소 가중치 담을 배열, resMin : 최소값
	private static long minEdge[], resMin;
	private static double E; // 세율
	private static boolean visited[]; // 방문표시
	private static List<Island>[] list; // 섬 담을 리스트
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());

		StringTokenizer stt;
		for( int test_case = 1; test_case <= T; test_case++) {
			N = Integer.parseInt(br.readLine());
			list = new ArrayList[N];
			for (int i = 0; i < N; i++) {
				list[i] = new ArrayList<Island>();
			}

			visited = new boolean[N];
			minEdge = new long[N];
			int[] input = new int[N*2];

			int idx = 0;
			for( int i = 0; i < 2; i++) {
				stt = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					input[idx] = Integer.parseInt(stt.nextToken());
					idx++;
				}
			} // input end

			for(int i = 0; i < N; i++) {
				list[i].add(new Island(input[i+N],input[i]));
			} // 리스트 저장.

			E = Double.parseDouble(br.readLine());
			resMin = 0;
			// 1번 정점에서 시작해서 모든 간선을 골랐을 때
			// 모든 간선을 선택하면서 최소가 되는 경우를 출력해야한다.
			connectIsland();

			System.out.println("#" + test_case + " " + Math.round(E*resMin));
		} // test_case end
	} // main end

	/** 모든 간선 선택, Prim으로 MST 찾기 */
	private static void connectIsland() {
		PriorityQueue<Edge> queue = new PriorityQueue<Edge>();
		// 섬간에 이어진 간선이 주어지지않았기 때문에...
		// 어떤 정점을 선택해서 넣는 순간 주변에 방문하지 않은 모든 정점과의 거리를 계산하고 
		// 그 중 가장 짧은 가중치를 가진 정점을 큐에 넣으면 되지않을까?!
		int nodeCnt = 0;
		// 일단 엄청 큰 값으로 초기화
		Arrays.fill(minEdge, Long.MAX_VALUE);
		minEdge[0] = 0; // 초기위치 0
		queue.offer(new Edge(0,0)); // 자기 자신 가중치 0

		while(!queue.isEmpty()) {
			Edge minVertex = queue.poll();
			if(visited[minVertex.to]) continue;

			resMin += minVertex.weight;
			visited[minVertex.to] = true;
			if(++nodeCnt == N) break;

			for( int i = 0; i < N; i++) {
				if(!visited[i]) { // 방문 하지 않은 정점만 보자.
					// 선택된 정점에서 이어진 모든 정점의 최소 값을 구해보자.
					long tmp = (long)(Math.pow((list[i].get(0).x - list[minVertex.to].get(0).x),2)
							+ Math.pow((list[i].get(0).y - list[minVertex.to].get(0).y),2));
					if( minEdge[i] > tmp ) { // 현재 저장되어진 가중치보다 더 작다면
						minEdge[i] = tmp; // 업데이트
						// 최소 거리를 가지는 정점 큐에 추가.
						queue.offer(new Edge(i, minEdge[i])); 
					}
				}
			}
		} // while end
	} // prim end
}
