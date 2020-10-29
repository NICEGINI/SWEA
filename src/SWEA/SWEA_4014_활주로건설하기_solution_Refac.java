/**
 * 
 */
package SWEA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 4014 활주로 건설하기
 *
 */
public class SWEA_4014_활주로건설하기_solution_Refac {
	private static int N, X, map[][], rmap[][], totalCnt;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for(int test_case = 1; test_case <= T; test_case++) {
			StringTokenizer stt = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(stt.nextToken());
			X = Integer.parseInt(stt.nextToken());
			
			map = new int[N][N];
			rmap = new int[N][N];
			
			totalCnt = 0;
			
			for(int i = 0; i < N; i++) {
				stt = new StringTokenizer(br.readLine());
				for(int j = 0; j < N; j++) {
					rmap[j][i] = map[i][j] = Integer.parseInt(stt.nextToken());
				}
			} // input end
			
			process();
			
			System.out.println("#"+test_case+" "+ totalCnt);
		} // test_case end
	}
	
	private static void process() {
		for(int i = 0; i < N; i++) {
			if(makeRoad(map[i])) ++totalCnt;
			if(makeRoad(rmap[i])) ++totalCnt;
		}
	}
	
	// 행 단위로 활주로 건설
	private static boolean makeRoad(int[] road) {
		int beforeHeight, size; // 이전칸의 높이, 평탕한 지형의 길이
		beforeHeight = road[0];
		size = 1;
		
		for(int j = 1; j < N; j++) {
			//1. 이전칸과 현재칸의 높이가 같은지
			if(beforeHeight == road[j]) {
				++size;
			}
			//2. 현재칸이 이전칸보다 높이가 1 높은 경우(오르막 경사로 설치 가능)
			else if(beforeHeight + 1 == road[j]) {
				if(size < X) return false; // 활주로 건설 불가
				beforeHeight++;
				size = 1; // 새로운 높이의 평탄한 지형 길이 1부터 시작
			}
			//3. 현재 칸이 이전칸보다 높이가 1 낮은 경우 ( 내리막 경사로 설치 가능 )
			else if(beforeHeight-1 == road[j]) {
				int count = 0;
				for(int k = j; k < N; k++) {
					if(road[k] != beforeHeight - 1) break;
					count++; // 이전칸의 높이와 1차이 낮은 연속된 평탄화 지형의 길이 카운트
				}
				if(count < X) return false; // 활주로 건설 불가
				j += X-1; // 경사로 다음 칸의 위치로 제어
				beforeHeight--;
				size = 0;
			}
			//4. 높이가 2이상 차이나는 경우
			else {
				return false;
			}
		}
		return true;
	}
}
