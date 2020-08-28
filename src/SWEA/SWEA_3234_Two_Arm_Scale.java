/**
 * 
 */
package SWEA;

import java.util.Scanner;

/**
 * SWEA 3234 - 준환이의 양팔저울
 * 저울의 오른쪽이 왼쪽보다 무거우면 안된다.
 * 
 *
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

	/** 일단 왼쪽 저울에 올릴 수 있는 경우의 수*/
	static void calc(int[] weight, boolean[] sel, int lw, int rw, int cnt) {
		if(cnt == weight.length) {
			total++;
			return;
		}
		for( int i = 0; i < weight.length; i++) {
			if(sel[i]) continue;
			sel[i] = true;
			calc(weight, sel, lw + weight[i], rw, cnt+1);
			if(rw + weight[i] <= lw)
				calc(weight, sel, lw, rw + weight[i], cnt+1);
			sel[i] = false;

		}
	}
}
