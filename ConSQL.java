package SchoolRate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConSQL {
	static int resultCnt = 0;
	
	public static String[][] getDate(String date) throws SQLException{
		ArrayList<String[]> list = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String connectionUrl = "jdbc:sqlserver://192.168.10.192;databaseName=Anycare;user=kabsung3;password=hyung269";
			conn = DriverManager.getConnection(connectionUrl);
			stmt = conn.createStatement();
			list = new ArrayList<>(); //데이터 조회 결과를 넣을 리스트
			
			String tableDate; //테이블명에 들어가는 YYYYMM 을 저장할 변수
						
			tableDate = date.substring(0,4) + date.substring(5,7);
			String sql = 
						"SELECT ROW_NUMBER() OVER(ORDER BY a.orgid DESC) AS No, title.지사명 '지사명', title.기관명 '기관명', om.[전체 학생의수] '전체 학생의 수', atnd.attendStu '총 등교 학생 수', +\r\n" + 
						"	CAST(CAST(atnd.attendStu AS FLOAT)/CAST(om.[전체 학생의수] AS FLOAT)*100 AS DECIMAL) '등교율', goBack.goBackStu '총 하교 학생 수', +\r\n" + 
						"	CAST(CAST(goBack.goBackStu AS FLOAT)/CAST(om.[전체 학생의수] AS FLOAT)*100 AS DECIMAL) '하교율', aftGoBack.aftGoBackStu '총 하교후 학생 수', + iBtn.iBtnStu '총 I버튼 학생 수'\r\n" + 
						"FROM TB_ORGAN AS a\r\n" + 
						"join (\r\n" + 
						"	SELECT o.orgid '기관id', COUNT(*) '전체 학생의수'\r\n" + 
						"	FROM tb_member as m\r\n" + 
						"	join tb_organ as o\r\n" + 
						"	ON o.orgmem = m.orgid + '000'\r\n" + 
						"	GROUP BY o.orgid) as om\r\n" + 
						"on a.orgid = om.기관id\r\n" + 
						"join( -- 지사명과 기관명 추출\r\n" + 
						"	select om.기관id '기관id' ,b.name '지사명', om.기관명 '기관명'\r\n" + 
						"	from tb_branch as b\r\n" + 
						"	left join (\r\n" + 
						"		select m.기관id '기관id', m.기관명 '기관명', o.mngbrid '지사id'\r\n" + 
						"		from tb_organ as o \r\n" + 
						"		right join (\r\n" + 
						"			select distinct orgid '기관id', orgname '기관명'\r\n" + 
						"			from tb_attend_" + tableDate + "\r\n" + 
						"		) as m\r\n" + 
						"		on m.기관id + '000' = o.orgmem\r\n" + 
						"		) as om\r\n" + 
						"	on b.brmem = om.지사id + '000'\r\n" + 
						"	where right(brmem,3) = '000'\r\n" + 
						") as title\r\n" + 
						"on title.기관id = a.orgid\r\n" + 
						"join( -- 등교\r\n" + 
						"	select orgid, count(*) 'attendStu'\r\n" + 
						"	from tb_attend_" + tableDate + "\r\n" + 
						"	where atndidx = 'A' and left(tagtime, 10) = '" + date + "'\r\n" + 
						"	group by orgid\r\n" + 
						") as atnd\r\n" + 
						"on atnd.orgid = a.orgid\r\n" + 
						"join( -- 하교\r\n" + 
						"	select orgid, count(*) 'goBackStu'\r\n" + 
						"	from tb_attend_" + tableDate + "\r\n" + 
						"	where atndidx = 'O' and left(tagtime, 10) = '" + date + "'\r\n" + 
						"	group by orgid\r\n" + 
						") as goBack\r\n" + 
						"on goBack.orgid = a.orgid\r\n" + 
						"join( -- 하교후 \r\n" + 
						"	select orgid, count(*) 'aftGoBackStu'\r\n" + 
						"	from tb_attend_" + tableDate + "\r\n" + 
						"	where atndidx = 'F' and left(tagtime, 10) = '" + date + "'\r\n" + 
						"	group by orgid\r\n" + 
						") as aftGoBack\r\n" + 
						"on aftGoBack.orgid = a.orgid\r\n" + 
						"join( -- I버튼\r\n" + 
						"	select orgid, count(*) 'iBtnStu'\r\n" + 
						"	from tb_attend_" + tableDate + "\r\n" + 
						"	where atndidx = 'I' and left(tagtime, 10) = '" + date + "'\r\n" + 
						"	group by orgid\r\n" + 
						") as iBtn\r\n" + 
						"on iBtn.orgid = a.orgid\r\n" + 
						"where right(a.orgmem, 3) = '000'" + 
						"order by No\r\n";
						
				
				
				rs = stmt.executeQuery(sql);
				
				System.out.println(date + " 날짜의 등하교 정보를 출력합니다.");
				System.out.println("No" + "\t" + "지사명" + "\t" +  "기관명" + "\t\t" +  "전체 학생의 수" + "\t" +  "총 등교 학생 수" + "\t" +  "등교율"
						+ "\t" +  "총 하교 학생 수" + "\t" +  "하교율" + "\t" +  "총 하교후 학생 수 " + "\t" +  "총 I버튼 학생 수");
				while (rs.next()) {
					String field = rs.getString("No");
					String field1 = rs.getString("지사명");
					String field2 = rs.getString("기관명");
					String filed3 = rs.getString("전체 학생의 수");
					String filed4 = rs.getString("총 등교 학생 수");
					String filed5 = rs.getString("등교율");
					String filed6 = rs.getString("총 하교 학생 수");
					String filed7 = rs.getString("하교율");
					String filed8 = rs.getString("총 하교후 학생 수");
					String filed9 = rs.getString("총 I버튼 학생 수");
					
					//list.add(new String[] { field1 + field2 + filed3 + filed4 + filed5 + filed6 + filed7 
					//							+ filed8 + filed9 });
					list.add(new String[] {field ,field1 ,field2, filed3, filed4, filed5, filed6, filed7, filed8, filed9 });
					resultCnt++;
				}								
				System.out.println("행의 개수: " + resultCnt);
				
				
		}
		catch (SQLException sqle) {
			System.out.println("서버 접속에 실패하였습니다.");
			System.out.println("SQLException: " + sqle);
		}
		finally {
			rs.close();
			stmt.close();
			conn.close();
		}
		
		String[][] arr = new String[list.size()][10];
		return list.toArray(arr);
		
	}
	
	public static int getResultCnt() {
		int result = resultCnt;
		resultCnt = 0;
		return result;
	}
	
	
	
}
