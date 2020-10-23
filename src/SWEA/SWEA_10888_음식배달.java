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
 * SWEA 10888 - 음식배달
 * NxN 크기
 * 배달거리가 존재. (r1,c1) 과 (r2,c2)사이의 배달거리는
 * |(r1-r2| + |(c1-c2)| 
 * 0: 빈칸, 1: 일반집, 2이상(운용비):배달음식점
 * 배달거리 + 운용비의 최소값을 구하자.
 */
public class SWEA_10888_음식배달 {
	private static class Rest{
		int y, x , value;

		public Rest(int y, int x, int value) {
			super();
			this.y = y;
			this.x = x;
			this.value = value;
		}
	}
	private static int N, ResN, map[][], Min;
	private static boolean selected[];
	private static List<Rest>reslist;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringTokenizer stt = null;
		for(int test_case = 1; test_case <= T; test_case++) {
			// 초기화
			N = Integer.parseInt(br.readLine());
			ResN = 0;
			Min = Integer.MAX_VALUE;
			map = new int[N][N];
			reslist = new ArrayList<Rest>();
			
			for(int i = 0; i < N; i++) {
				stt = new StringTokenizer(br.readLine(), " ");
				for(int j = 0; j < N; j++) {
					int input = Integer.parseInt(stt.nextToken());
					map[i][j] = input;
					if(input > 1) { // 1보다 크면 음식점, 위치저장
						reslist.add(new Rest(i,j,input));
						ResN++;
					}
				}
			}// 입력 끝
			
			selected = new boolean[ResN];
			
			calc(0,0);
			
			System.out.println("#"+test_case+" "+Min);
		}// test_case end
	} // main end
	
	/** 선택 할 수 있는 모든 경우를 부분집합으로 */
	private static void calc(int idx, int truecnt) {
		if(idx == ResN) {
			if(truecnt == 0) return ; // 하나도 선택하지 않은 경우는 Pass
			calcDist();
			return ;
		}
		selected[idx] = true;
		calc(idx+1,truecnt+1);
		selected[idx] = false;
		calc(idx+1,truecnt);
	}
	
	/** 거리계산*/
	private static void calcDist() {
		int sum = 0;
		int idx = 0;
		for(int i = 0; i < N; i++) {
			for( int j = 0; j < N; j++) {
				if(map[i][j] == 1) { // 일반 집을 기준으로 
					int mindist = Integer.MAX_VALUE;
					int dist = 0;
					for(int r = 0; r < ResN; r++) {
						if(selected[r]) { // 선택한 음식점만 거리 계산
							dist = Math.abs(i - reslist.get(r).y) + Math.abs(j - reslist.get(r).x);
							if(mindist > dist) { // 현재 선택된 일반 집에서 가장 가까운 음식점 체크
								mindist = dist;
								idx = r;
							}
							else if(mindist == dist) { // 거리가 같은 음식점이 있다면 운용비를 체크
								int pre_min = reslist.get(idx).value;
								if(pre_min > reslist.get(r).value) { // 이전 비용보다 지금 비용이 더 쌀 경우
									idx = r;
								}
							}
						}
					}
					sum += mindist;
				}
			}
		}
		for(int r = 0; r < ResN; r++) {
			if(selected[r]) // 현재 선택된 음식점의 운용비를 모두 더한다.
				sum += reslist.get(r).value;
		}
		if( Min > sum) { // 최소값 갱신
			Min = sum;
		}
	}
}
