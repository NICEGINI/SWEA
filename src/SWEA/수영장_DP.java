/**
 * 
 */
package SWEA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author YSM
 *
 */
public class 수영장_DP {
	private static int days[], moneys[];
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int t = 1; t <= T; t++) {
			moneys = new int[4];
			days = new int[13];
			StringTokenizer stt = new StringTokenizer(br.readLine());
			for(int i = 0; i < 4; i++) {
				moneys[i] = Integer.parseInt(stt.nextToken());
			}
			
			stt = new StringTokenizer(br.readLine());
			for(int i = 1; i <= 12; i++) {
				days[i] = Integer.parseInt(stt.nextToken());
			}
			System.out.println("#"+t+" "+plan());
		}
	}
	
	private static int plan() {
		int D[] = new int[13];
		
		for(int i = 1; i <= 12; i++) {
			// 1일권
			D[i] = D[i-1] + days[i]*moneys[0];
			// 1달권
			if(days[i] > 0) D[i] = Math.min(D[i], D[i-1]+moneys[1]);
			// 3달권
			if(i >= 3)D[i] = Math.min(D[i], D[i-3]+moneys[2]);
		}
		return Math.min(D[12], moneys[3]);
	}
}
