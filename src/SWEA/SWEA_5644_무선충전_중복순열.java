/**
 * 
 */
package SWEA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * SWEA 5644 무선충전
 * 
 * 
 */
public class SWEA_5644_무선충전_중복순열 {
	private static class User {
		int y, x;

		public User(int y, int x) {
			this.y = y;
			this.x = x;
		}
	}
	private static class BC{
		int y, x, dist, pow;

		public BC(int y, int x, int dist, int pow) {
			this.y = y;
			this.x = x;
			this.dist = dist;
			this.pow = pow;
		}
	}
	private static int totalPow;
	private static int move1[], move2[];
	// 제자리 상 우 하 좌
	private static int dx[] = {0, 0, 1, 0, -1};
	private static int dy[] = {0, -1, 0, 1, 0};
	private static List<BC> bclist;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for(int test_case = 1; test_case <= T; test_case++) {
			StringTokenizer stt = new StringTokenizer(br.readLine());
			int moveCnt = Integer.parseInt(stt.nextToken());
			int bcCnt = Integer.parseInt(stt.nextToken());
			move1 = new int[moveCnt+1];
			move2 = new int[moveCnt+1];
			bclist = new ArrayList<>();
			
			stt = new StringTokenizer(br.readLine());
			for(int i = 1; i <= moveCnt; i++) {
				move1[i] = Integer.parseInt(stt.nextToken());
			}
			
			stt = new StringTokenizer(br.readLine());
			for(int i = 1; i <= moveCnt; i++) {
				move2[i] = Integer.parseInt(stt.nextToken());
			}
			
			for(int i = 0; i < bcCnt; i++) {
				stt = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(stt.nextToken());
				int y = Integer.parseInt(stt.nextToken());
				int dist = Integer.parseInt(stt.nextToken());
				int pow = Integer.parseInt(stt.nextToken());
				bclist.add(new BC(y, x, dist, pow));
			} // input end
			
			totalPow = 0;
			start(moveCnt, bcCnt);
			
			System.out.println("#"+test_case+" " +totalPow);
		}// test_casse end
	}
	
	/** */
	private static void start(int movecnt, int bccnt) {
		int idx = 0;
		User userA = new User(1,1);
		User userB = new User(10,10);
		
		while(true) {
			userA.y += dy[move1[idx]];
			userA.x += dx[move1[idx]];
			userB.y += dy[move2[idx]];
			userB.x += dx[move2[idx]];
			
			totalPow += getCharge(userA, userB, bccnt);

			if(++idx == movecnt+1) {
				break;
			}
		} // while end
	}

	private static int getCharge(User userA, User userB, int bccnt) {
		int max = 0;
		
		for(int a = 0; a < bccnt; a++) {
			for (int b = 0; b < bccnt; b++) {
				int sum = 0;
				int amountA = check(a, userA.x, userA.y);
				int amountB = check(b, userB.x, userB.y);
				if(a != b) sum = amountA + amountB;
				else 	   sum = Math.max(amountA, amountB);
				
				if(max < sum) max = sum;
			}
		}
		
		return max;
	}

	private static int check(int a, int x, int y) {
		BC bc = bclist.get(a);
		return Math.abs(bc.x - x ) + Math.abs(bc.y - y) 
		<= bc.dist ? bc.pow : 0;
	}
}
