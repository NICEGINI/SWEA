/**
 * 
 */
package SWEA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * SWEA 2112 - 보호 필름. 약품을 투여한다 / 안한다 -> 투여한다- A, B로 나뉘어진다. 부분집합을 이용.
 * 부분집합
 */
public class SWEA_2112_Protect_Film {
	// D: 두꼐, W: 가로길이, K: 합격기준
	private static int D, W, K, film[][], resMin, list[];
	private static final int NOT = -1, A = 0, B = 1;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringTokenizer stt = null;

		for (int test_case = 1; test_case <= T; test_case++) {
			stt = new StringTokenizer(br.readLine(), " ");
			D = Integer.parseInt(stt.nextToken()); // 행
			W = Integer.parseInt(stt.nextToken()); // 열
			K = Integer.parseInt(stt.nextToken()); // 합격기준

			film = new int[D][W];
			list = new int[D]; // 약픔을 행으로 집어넣음. 투여된 약품의 상태정보를 관리.

			for (int i = 0; i < D; i++) {
				stt = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < W; j++) {
					film[i][j] = Integer.parseInt(stt.nextToken());
				}
			} // input end
			
			Arrays.fill(list, NOT); // 약품 비투여 상태
			if(checkRows()) { // 약품 하나도 사용하지 않은 상태로 체크.
				resMin = 0;
			} else {
				resMin = Integer.MAX_VALUE;
				process(0, 0);
			}
			System.out.println("#" + test_case + " " + resMin);
		} // test_case end
	} // main end

	/** 부분집합을 이용해서 투여(A,B)/비투여의 모든 상황을 처리 */
	private static void process(int rowidx, int cnt) {
		if (cnt >= resMin) return; // cnt가 최소값보다 크면 볼 필요 없다.
		if (rowidx == D) { // 끝까지 다 시도했다면
			// 해당 보호필름의 상태가 통과기준에 부합하는지 체크.
			// 통과한다면 이때 쓰인 약품의 개수를 이용하여 최소값 갱신 로직.
			if (checkRows()) {
				if (resMin > cnt)
					resMin = cnt;
			}
			// 통과 못한다면 답이 될 수 없음.
			return;
		}
		
		// 약품 투여 (A)
		list[rowidx] = A;
		process(rowidx + 1, cnt + 1);
		// 약품 투여 (B)
		list[rowidx] = B;
		process(rowidx + 1, cnt + 1);
		// 약품 비투여
		list[rowidx] = NOT;
		process(rowidx + 1, cnt);
	}

	/** 모든 열이 만족하는지 체크 */
	private static boolean checkRows() {
		for (int j = 0; j < W; j++) {
			// 투여가 안됐으면 기존입력값을, 투여가 됐다면 투여된 약품값을.
			int before = list[0] == NOT ? film[0][j] : list[0];
			int count = 1;
			for (int i = 1; i < D; i++) { // 1행부터 마지막까지 연속성 체크.
				int current = list[i] == NOT ? film[i][j] : list[i];
				if (before != current) { // 인접한 2개의 셀이 다르다.
					before = current;
					count = 1;
				} else { // 인접한 2개의 셀이 같다.
					if (++count >= K)
						break; // 연속 셀의 개수가 K이상이면 더이상 체크 x
				}
			}
			if(count < K) return false;
		}
		return true;
	}
}
