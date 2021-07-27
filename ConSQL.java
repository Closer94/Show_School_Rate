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
			list = new ArrayList<>(); //������ ��ȸ ����� ���� ����Ʈ
			
			String tableDate; //���̺�� ���� YYYYMM �� ������ ����
						
			tableDate = date.substring(0,4) + date.substring(5,7);
			String sql = 
						"SELECT ROW_NUMBER() OVER(ORDER BY a.orgid DESC) AS No, title.����� '�����', title.����� '�����', om.[��ü �л��Ǽ�] '��ü �л��� ��', atnd.attendStu '�� � �л� ��', +\r\n" + 
						"	CAST(CAST(atnd.attendStu AS FLOAT)/CAST(om.[��ü �л��Ǽ�] AS FLOAT)*100 AS DECIMAL) '���', goBack.goBackStu '�� �ϱ� �л� ��', +\r\n" + 
						"	CAST(CAST(goBack.goBackStu AS FLOAT)/CAST(om.[��ü �л��Ǽ�] AS FLOAT)*100 AS DECIMAL) '�ϱ���', aftGoBack.aftGoBackStu '�� �ϱ��� �л� ��', + iBtn.iBtnStu '�� I��ư �л� ��'\r\n" + 
						"FROM TB_ORGAN AS a\r\n" + 
						"join (\r\n" + 
						"	SELECT o.orgid '���id', COUNT(*) '��ü �л��Ǽ�'\r\n" + 
						"	FROM tb_member as m\r\n" + 
						"	join tb_organ as o\r\n" + 
						"	ON o.orgmem = m.orgid + '000'\r\n" + 
						"	GROUP BY o.orgid) as om\r\n" + 
						"on a.orgid = om.���id\r\n" + 
						"join( -- ������ ����� ����\r\n" + 
						"	select om.���id '���id' ,b.name '�����', om.����� '�����'\r\n" + 
						"	from tb_branch as b\r\n" + 
						"	left join (\r\n" + 
						"		select m.���id '���id', m.����� '�����', o.mngbrid '����id'\r\n" + 
						"		from tb_organ as o \r\n" + 
						"		right join (\r\n" + 
						"			select distinct orgid '���id', orgname '�����'\r\n" + 
						"			from tb_attend_" + tableDate + "\r\n" + 
						"		) as m\r\n" + 
						"		on m.���id + '000' = o.orgmem\r\n" + 
						"		) as om\r\n" + 
						"	on b.brmem = om.����id + '000'\r\n" + 
						"	where right(brmem,3) = '000'\r\n" + 
						") as title\r\n" + 
						"on title.���id = a.orgid\r\n" + 
						"join( -- �\r\n" + 
						"	select orgid, count(*) 'attendStu'\r\n" + 
						"	from tb_attend_" + tableDate + "\r\n" + 
						"	where atndidx = 'A' and left(tagtime, 10) = '" + date + "'\r\n" + 
						"	group by orgid\r\n" + 
						") as atnd\r\n" + 
						"on atnd.orgid = a.orgid\r\n" + 
						"join( -- �ϱ�\r\n" + 
						"	select orgid, count(*) 'goBackStu'\r\n" + 
						"	from tb_attend_" + tableDate + "\r\n" + 
						"	where atndidx = 'O' and left(tagtime, 10) = '" + date + "'\r\n" + 
						"	group by orgid\r\n" + 
						") as goBack\r\n" + 
						"on goBack.orgid = a.orgid\r\n" + 
						"join( -- �ϱ��� \r\n" + 
						"	select orgid, count(*) 'aftGoBackStu'\r\n" + 
						"	from tb_attend_" + tableDate + "\r\n" + 
						"	where atndidx = 'F' and left(tagtime, 10) = '" + date + "'\r\n" + 
						"	group by orgid\r\n" + 
						") as aftGoBack\r\n" + 
						"on aftGoBack.orgid = a.orgid\r\n" + 
						"join( -- I��ư\r\n" + 
						"	select orgid, count(*) 'iBtnStu'\r\n" + 
						"	from tb_attend_" + tableDate + "\r\n" + 
						"	where atndidx = 'I' and left(tagtime, 10) = '" + date + "'\r\n" + 
						"	group by orgid\r\n" + 
						") as iBtn\r\n" + 
						"on iBtn.orgid = a.orgid\r\n" + 
						"where right(a.orgmem, 3) = '000'" + 
						"order by No\r\n";
						
				
				
				rs = stmt.executeQuery(sql);
				
				System.out.println(date + " ��¥�� ���ϱ� ������ ����մϴ�.");
				System.out.println("No" + "\t" + "�����" + "\t" +  "�����" + "\t\t" +  "��ü �л��� ��" + "\t" +  "�� � �л� ��" + "\t" +  "���"
						+ "\t" +  "�� �ϱ� �л� ��" + "\t" +  "�ϱ���" + "\t" +  "�� �ϱ��� �л� �� " + "\t" +  "�� I��ư �л� ��");
				while (rs.next()) {
					String field = rs.getString("No");
					String field1 = rs.getString("�����");
					String field2 = rs.getString("�����");
					String filed3 = rs.getString("��ü �л��� ��");
					String filed4 = rs.getString("�� � �л� ��");
					String filed5 = rs.getString("���");
					String filed6 = rs.getString("�� �ϱ� �л� ��");
					String filed7 = rs.getString("�ϱ���");
					String filed8 = rs.getString("�� �ϱ��� �л� ��");
					String filed9 = rs.getString("�� I��ư �л� ��");
					
					//list.add(new String[] { field1 + field2 + filed3 + filed4 + filed5 + filed6 + filed7 
					//							+ filed8 + filed9 });
					list.add(new String[] {field ,field1 ,field2, filed3, filed4, filed5, filed6, filed7, filed8, filed9 });
					resultCnt++;
				}								
				System.out.println("���� ����: " + resultCnt);
				
				
		}
		catch (SQLException sqle) {
			System.out.println("���� ���ӿ� �����Ͽ����ϴ�.");
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
