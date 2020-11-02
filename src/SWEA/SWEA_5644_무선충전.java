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
public class SWEA_5644_무선충전 {
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
		User user1 = new User(1,1);
		User user2 = new User(10,10);
		
		while(true) {
			user1.y += dy[move1[idx]];
			user1.x += dx[move1[idx]];
			user2.y += dy[move2[idx]];
			user2.x += dx[move2[idx]];
			
			List<Integer>bclistA = new ArrayList<>(); 
			List<Integer>bclistB = new ArrayList<>(); 
			
			for(int i = 0; i < bccnt; i++) {
				BC bc = bclist.get(i);
				int dist1 = Math.abs(user1.y - bc.y) + Math.abs(user1.x - bc.x);
				int dist2 = Math.abs(user2.y - bc.y) + Math.abs(user2.x - bc.x);
			
				if(dist1 <= bc.dist) bclistA.add(i);
				if(dist2 <= bc.dist) bclistB.add(i);
			}
			
			totalPow += getCharge(bclistA, bclistB);

			if(++idx == movecnt+1) {
				break;
			}
		} // while end
	}

	private static int getCharge(List<Integer> bclistA, List<Integer> bclistB) {
		int max = 0, tmp = 0;
		
		int aSize = bclistA.size(), bSize = bclistB.size();
		
		if(aSize == 0 && bSize == 0) return 0; // 둘다 충전 불가능
		else if(aSize == 0) return getMaxPower(bclistB); // 유저 2만 충전 가능
		else if(bSize == 0) return getMaxPower(bclistA); // 유저 1만 충전 가능
		
		// 유저 1, 2 모두 충전 가능한 상황 조합고려
		for(Integer a : bclistA) {
			for(Integer b : bclistB) {
				if( a != b) tmp = bclist.get(a).pow + bclist.get(b).pow;
				else 		tmp = Math.max(bclist.get(a).pow, bclist.get(b).pow);
				
				if(max < tmp) max = tmp;
			}
		}
		return max;
	}

	private static int getMaxPower(List<Integer> bcList) {
		int max = 0;
		for(Integer a : bcList) {
			int pow = bclist.get(a).pow;
			if(max < pow) {
				max = pow;
			}
		}
		return max;
	}
}
