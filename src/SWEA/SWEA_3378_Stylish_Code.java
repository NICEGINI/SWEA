/**
 * 
 */
package SWEA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author YSM
 *
 */
public class SWEA_3378_Stylish_Code {
	private static int p,q, res[];
	private static char master[][], me[][];
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer stt = new StringTokenizer(br.readLine());
		
		int T = Integer.parseInt(stt.nextToken());
		
		for( int t = 1; t <= T; t++) {
			stt = new StringTokenizer(br.readLine());
			
			p = Integer.parseInt(stt.nextToken());
			q = Integer.parseInt(stt.nextToken());
			
			master = new char[p][];
			me = new char[q][];
			res = new int[q]; // 자신의 코드에 들여쓰기 할 수. 마스터가 이상하면 -1.
			
			Arrays.fill(res, -2);
			
			for( int i = 0; i < p; i++) {
				master[i] = br.readLine().toCharArray();
			} // 마스터 코드 입력
			
			for( int i = 0; i < q; i++) {
				me[i] = br.readLine().toCharArray();
			} // 내 코드 입력
			
			for( int r = 1; r <= 20; r++) {
				for( int c = 1; c <= 20; c++) {
					for( int s = 1; s <= 20; s++) {
						if(isAvailable(r,c,s)) { // 마스터 코드에 r,c,s 적용 가능한지 판단
							processIndent(r,c,s); // 본인 코드에 r,c,s 적용해봄
						}
					}
				}
			}
			StringBuilder sb = new StringBuilder("#" + t + " ");
			for( int r : res) {
				sb.append(r).append(" ");
			}
			System.out.println(sb.toString());
		}
	}
	
	/** @param s*/
	private static void processIndent(int r, int c, int s) {
		int rCount = 0, cCount = 0, sCount = 0;
		for( int i = 0; i < q; i++) {
			if(res[i] == -2) { // 처음 들여쓰기 계산을 하는 경우
				res[i] = r*rCount + c*cCount + s*sCount;
			} else { // 들여쓰기 계산이 처음이 아님(다른 r,c,s의 해로도 들여쓰기가 가능
				if(res[i] != r*rCount + c*cCount + s*sCount) { // 결과값 다르면 결정 불가
					res[i] = -1;
				}
			}
			
			for( char ch : me[i]) {
				switch(ch) {
				case '(' : rCount++; break;
				case ')' : rCount--; break;
				case '{' : cCount++; break;
				case '}' : cCount--; break;
				case '[' : sCount++; break;
				case ']' : sCount--; break;
				}
			}
		}
	}
	
	/** 사용 가능한 지 판단.*/
	private static boolean isAvailable(int r, int c, int s) {
		int rCount = 0, cCount = 0, sCount = 0;
		for( int i = 0; i < p; i++) {
			int cnt = 0;
			
			// 가장 앞에서 연속된 온점의 수만 센다.
			for( char ch : master[i]) {
				if(ch == '.') ++cnt;
				else break;
			}
			
			int indent = r*rCount + c*cCount + s*sCount;
			
			// 계산된 들여쓰기 개수와 마스터의 개수가 다르면 결정 불가
			if(indent != cnt) return false;
			
			for( char ch : master[i]) {
				switch(ch) {
				case '(' : rCount++; break;
				case ')' : rCount--; break;
				case '{' : cCount++; break;
				case '}' : cCount--; break;
				case '[' : sCount++; break;
				case ']' : sCount--; break;
				}
			}
		}
		return true;
	}
}
