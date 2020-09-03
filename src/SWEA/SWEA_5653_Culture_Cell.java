/**
 * 
 */
package SWEA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * SWEA 5653 - 줄기세포 배양
 * 줄기세포 생명력이라는 수치를 가지고있음.
 * 초기상태 비활성. 수치가 x인 세포는 x시간동안 비활성
 * x시간이 지나면 활성. 세포가 죽어도 소멸이 아닌 남아있음
 * 활성화된 줄기세포는 첫 1시간동안 상하좌우로 번식.
 * 번식된 세포는 비활성.
 * 번식하려는 곳에 2개이상의 세포가 있다면 생명력수치가 높은 세포가 혼자 차지.
 * 
 * 배양용기 크기는 무한함.
 * K시간 후 살아있는 줄기세포( 비활성 + 활성)의 총 개수를 구하라.
 *
 */
public class SWEA_5653_Culture_Cell {
	static class Cell implements Comparable<Cell>{
		// y,x : 좌표 , lifetime : 생존한 시간, lifepower : 본래 세포의 생명력
		int y, x, lifetime, lifepower;

		public Cell(int y, int x, int lifetime, int lifepower) {
			this.y = y;
			this.x = x;
			this.lifetime = lifetime;
			this.lifepower = lifepower;
		}

		@Override
		public int compareTo(Cell o) {
			// 내림차순 정렬
			return -1*(this.lifepower - o.lifepower);
		}

		// 세포의 생명시간 변경
		public void setLifetime(int lifetime) {
			this.lifetime = lifetime;
		}
	}

	private static int plate[][], MAX, CENTER;
	private static int dx[] = {0, 0, -1, 1};
	private static int dy[] = {-1, 1, 0, 0};
	private static List<Cell> clist;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());

		StringTokenizer stt;
		for( int test_case = 1; test_case <= T; test_case++ ) {
			stt = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(stt.nextToken());
			int M = Integer.parseInt(stt.nextToken());
			int K = Integer.parseInt(stt.nextToken());

			// 무한히 증식한다고 했다. 특정 큰 값을 줘도 괜찮지만
			// 입력받은 N값을 이용해서 특정 큰 값을 만들었다.
			MAX = N*14;
			// 정 중앙을 찾기위해 /2
			CENTER = MAX/2;

			plate = new int[MAX][MAX];
			clist = new ArrayList<Cell>();

			for(int i = 0; i < N; i++) {
				stt = new StringTokenizer(br.readLine());
				for (int j = 0; j < M; j++) {
					int lifetime = Integer.parseInt(stt.nextToken());
					if(lifetime != 0) {
						plate[CENTER+i][CENTER+j] = lifetime;
						clist.add(new Cell(CENTER+i, CENTER+j,  lifetime, lifetime));
					}
				}
			} // input end

			// 분열 시작
			start_split(K);

			int cellCnt = clist.size();
			System.out.println("#" + test_case + " " + cellCnt);
		} // test_case end
	}

	/** 분열 시작 */
	private static void start_split(int K) {
		for( int i = 0; i < K; i++) {
			// 생명력 수치로 내림차순 정렬, 생명력이 가장 강한 친구부터 처리 할 거임.
			Collections.sort(clist);
			// 새로 분열한 세포를 담을 리스트.
			List<Cell> tmplist = new ArrayList<>();
			// 도중에 삭제 연산이 생기기떄문에 Iterator를 이용해서 리스트 순환.
			// clist를 반복하며 다음 객체가 있다면 cur에 준다.
			for(Iterator<Cell> cur = clist.iterator(); cur.hasNext();) {
				Cell cur_cell = cur.next();
				int lifetime = cur_cell.lifetime; // 시간이 지나기 전의 생명시간.

				// 시간 흐름. 시간이 지난다고 바로 분열하는것이 아니라 
				// 활성화 상태로 가기 때문에 따로관리 해주어야한다.
				cur_cell.setLifetime(lifetime-1);

				int lifepower = cur_cell.lifepower;// 세포의 생명력

				// 현재 세포의 좌표
				int x = cur_cell.x;
				int y = cur_cell.y;

				if(lifetime == 0) { // 활성화 할 시간
					// 4방향 탐색
					for( int k = 0; k < 4; k++) {
						int nx = x + dx[k];
						int ny = y + dy[k];

						// 뭐든지 세포가 있다면? 원래있든, 죽었든, 방굼 누가 퍼트렸든.
						// 그럼 넘김
						if(plate[ny][nx] != 0) continue;
						// 빈공간. 가장 먼저 활성화하는 친구가 현재로서는 가장 강한 세포.
						plate[ny][nx] = lifepower;
						// 새롭게 분열된 세포의 정보 저장.
						tmplist.add(new Cell(ny,nx, lifepower, lifepower));
					}
				}
				// 원래 생명력과 살아있는 시간과의 차이가 음수이면 죽음.
				// 위에 선언한 lifetime은 시간이 지나기 전 값이라 현재 리스트에 들어가 있는
				// 생명 시간과 비교해줘야 한다.
				if((lifepower + cur_cell.lifetime) == 0) {
					cur.remove();
					continue;
				}
			}
			// 새롭게 생긴 리스트를 이전 리스트와 연결.
			clist.addAll(tmplist);
			tmplist = new ArrayList<>(); // 초기화
		}
	}
}
