/**
 * 
 */
package SWEA;

import java.util.Scanner;

/**
 * SWEA 3234 - 준환이의 양팔저울
 * N개의 무게추를 저울에 올리는 방법은 N!
 * 왼쪽에 올릴 것인지 오른쪽에 올리 것인지를 선택하면 2^N * N!
 * 이때 저울의 오른쪽이 왼쪽보다 무거우면 안된다.
 * 저울에 무게추를 올릴 수 있는 모든 경우의 수
 * static 변수 생성하면 메모리 초과 발생.
 */
public class SWEA_3234_Two_Arm_Scale {
	static int total;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int T = sc.nextInt();

		for( int t = 1; t <= T; t++) {
			int N = sc.nextInt();
			boolean sel[] = new boolean[N];

			int weight[] = new int[N];
			for( int i = 0; i < N; i++) {
				weight[i] = sc.nextInt();
			}

			total = 0;
			calc(weight, sel, 0, 0, 0);

			System.out.println("#" + t + " " + total);
		}
	}

	/** 방문표시를 활용한 조합 */
	static void calc(int[] weight, boolean[] sel, int lw, int rw, int cnt) {
		if(cnt == weight.length) {
			total++;
			return;
		}
		for( int i = 0; i < weight.length; i++) {
			if(sel[i]) continue; // 이미 선택한 경우는 제외
			sel[i] = true;
			calc(weight, sel, lw + weight[i], rw, cnt+1); // 일단 왼쪽에 무게를 더해서 다음 조합으로
			if(rw + weight[i] <= lw) // 해당 무게추가 왼쪽에서 끝난경우, 오른쪽에 올렸을때 왼쪽보다 가볍다면?
				calc(weight, sel, lw, rw + weight[i], cnt+1); // 오른쪽에 무게를 싣고 다음 조합으로
			sel[i] = false;

		}
	}
}
