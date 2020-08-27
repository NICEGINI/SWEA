/**
 * 
 */
package SWEA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;


/**
 * SWEA 7793 - 오 나의 여신님
 * 지구에 종말이 다가와 악마가 강림한다!
 * 악마는 악마의 손아귀라는 스킬을 사용한다.
 * 악마의손아귀 : 매 초마다 상하좌우 인접해있는 영역을 부식시키며 확장
 * 
 * 단 지은이라는 여신이 있는 공간은 피해를 입지않는다.
 * 수연이는 여신이 있는곳까지 가야한다!
 * NxM크기, 돌이 있는 곳은 갈 수없고, 부식되지않음.
 * 수연이 이동 동서남북 여신에게 가는 최소시간 구하기
 *
 * 2 <= N,M <= 50
 * S : 수연, D : 여신, X : 돌, * : 악마
 */

class Actor implements Comparable<Actor>{
	int y, x, cnt, type; // 수연 :0, 악마 : 1
	public Actor(int y, int x, int cnt, int type) {
		this.y = y;
		this.x = x;
		this.type = type;
		this.cnt = cnt;
	}
	@Override
	public int compareTo(Actor o) {
		return this.type - o.type;
	}
}

public class SWEA_7793_Oh_My_Godness {
	private static int N,M, minDistance;
	private static char[][] map;
	private static List<Actor> alist;
	
	private static int dy[] = {-1,1,0,0};
	private static int dx[] = {0,0,-1,1};
	
	private static boolean visited[][][];
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer stt = new StringTokenizer(br.readLine());
		
		int T = Integer.parseInt(stt.nextToken());
		
		for( int test_case = 1; test_case <= T; test_case++) {
			stt = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(stt.nextToken());
			M = Integer.parseInt(stt.nextToken());
			
			map = new char[N][M];
			visited = new boolean[N][M][2];
			minDistance = 0;
			alist = new ArrayList<>();
			
			for( int i = 0; i < N; i++) {
				stt = new StringTokenizer(br.readLine());
				char[] tmp = stt.nextToken().toCharArray();
				for( int j = 0; j < M; j++ ) {
					if(tmp[j] == 'S') {
						alist.add(new Actor(i, j, 0, 0));
					} else if(tmp[j] == '*') {
						alist.add(new Actor(i, j, 0, 1));
					}
					map[i][j] = tmp[j];
				}
			}
			
			// 수연이가 항상 먼저 나올 수 있게 정렬
			Collections.sort(alist);
			
			int res = start();
			if(res == 1)
				System.out.println("#" + test_case + " " + minDistance);
			else
				System.out.println("#" + test_case + " " + "GAME OVER");
		} // test_case end
				
	}
	
	/** Breadth First Search */
	private static int start() {
		Queue<Actor> queue = new LinkedList<>();
		for( int i = 0; i < alist.size(); i++) {
			queue.offer(alist.get(i));
			visited[alist.get(i).y][alist.get(i).x][alist.get(i).type] = true;
		}
		
		while(!queue.isEmpty()) {
			Actor tmp = queue.poll();
			int type = tmp.type;
			int cnt = tmp.cnt;
			
			// 수연이 여신에게 도착
			if(map[tmp.y][tmp.x] == 'D' && type == 0) {
				minDistance = cnt;
				return 1;
			}
			
			// 이동하고 다시 이동해야 하는 시점에 이미 부식이 된 자리라면??
			// 악마가 부식을 시켰는데 그 자리에 수연이가 있는경우 그런곳은 수연이가 가면 안되는데...
			// ....죽은걸로 판단
			if(visited[tmp.y][tmp.x][1] && type == 0) {
				continue;
			}
			
			for( int k = 0; k < 4; k++) {
				int next_y = tmp.y + dy[k];
				int next_x = tmp.x + dx[k];
				
				// 범위 밖이면 못가
				if(next_y < 0 || next_y >= N || next_x < 0 || next_x >= M) continue;
				if(visited[next_y][next_x][type]) continue; // 이미 방문 한 곳이면 갈 필요가 없어.
				if(map[next_y][next_x] == 'X') continue; // 돌이면 못가
				if( type == 0 ) { // 수연이의 경우 	
					if(visited[next_y][next_x][1]) continue; // 이미 부식된 곳은 갈 수 없어.
					queue.offer(new Actor(next_y, next_x, cnt+1, type));
					visited[next_y][next_x][type] = true;
				} else if( type == 1 ) { // 악마의 경우
					if(map[next_y][next_x] == 'D') continue; // 여신자리는 부식이 안돼
					queue.offer(new Actor(next_y, next_x, 0, type));
					visited[next_y][next_x][type] = true;
				}
			}
		} // while end // 중간에 return 되기 때문에 여기 왔다는 것은 갈 길이 없다는 뜻
		return 2;
	}
}
