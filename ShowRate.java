package SchoolRate;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.toedter.calendar.JDateChooser;

public class ShowRate {

	private JFrame frame_1;
	String[][] data;
	String[] headers;
	JTable table;
	JScrollPane scrollPane;
	JDateChooser dateChooser;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowRate window = new ShowRate();
					window.frame_1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ShowRate() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("deprecation")
	private void initialize() {		
		//프레임 생성 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
		frame_1 = new JFrame();
		frame_1.setTitle("\uB0A0\uC9DC\uBCC4 \uB4F1\uD558\uAD50\uC728 \uAC80\uC0C9 \uD504\uB85C\uADF8\uB7A8");
		frame_1.setBounds(100, 100, 1157, 329);
		frame_1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_1.getContentPane().setLayout(null);
		
		//테이블 생성 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//처음 프로그램을 실행할때 디폴트 테이블을 보여줌/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		headers = new String[]{"No", "지사명", "기관명", "전체 학생의 수", "총 등교 학생 수" ,"등교율", "총 하교 학생의 수", "하교율", "총 하교후 학생 수", "총 I버튼 학생 수"};
		DefaultTableModel model = new DefaultTableModel(data, headers); //header 과 data를 기반으로 model 객체 생성 (이때 data는 비어있음)
		table = new JTable(model); //테이블 생성
		tableCellSize(table); //테이블 셀 크기 조절해주는 메소드
		
		//스크롤바 추가  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		scrollPane = new JScrollPane();
		scrollPane.setBounds(46, 76, 1034, 175);
		frame_1.getContentPane().add(scrollPane); //스크롤바를 frame_1에 추가
		scrollPane.setViewportView(table); //table에 있는 데이터를 스크롤바로 나타내기
		
		//검색 개수를 나타내는 라벨////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JLabel lblResultCnt = new JLabel("0");
		lblResultCnt.setBounds(703, 37, 39, 15);
		frame_1.getContentPane().add(lblResultCnt);
		
		//검색 버튼 생성 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JButton btnNewButton = new JButton("\uAC80\uC0C9");
		btnNewButton.setBounds(472, 30, 97, 23);
		btnNewButton.addActionListener(new ActionListener() { //버튼 클릭시 이벤트 메소드
			public void actionPerformed(ActionEvent e) {
				//캘린더에서 선택한 날짜를 YYYY/MM/DD로 변환해주어 inputDate 변수에 넣어준다.
				Date result = dateChooser.getDate();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				String inputDate = sdf.format(result);
				
				//inputDate =  textField.getText(); //텍스트 박스에 입력한 날짜를 inputDate에 저장
				//검색한 날짜로 mssql서버에 접속하여 조회한 결과를 String[][] 타입으로 반환///////////////////////////////////////////////////////////////////////////////////////////
				
				try {
					data = ConSQL.getDate(inputDate); 	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
				headers = new String[]{"No", "지사명", "기관명", "전체 학생의 수", "총 등교 학생 수" ,"등교율", "총 하교 학생의 수", "하교율", "총 하교후 학생 수", "총 I버튼 학생 수"};
				DefaultTableModel model = new DefaultTableModel(data, headers); //조회한 결과에 대한 data를 기반으로 table 생성
				table.setModel(model);
				tableCellRight(table); //컬럼 오른쪽 정렬하는 메소드		
				tableCellSize(table); //테이블 셀 크기 조절해주는 메소드
				scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum()); //검색시 항상 스크롤바 맨위로 위치 
				//검색 개수를 나타내는 라벨에 데이터 개수를 추가
				//getResultCnt()메소드는 해당 날짜에 조회한 결과에 대한 행 개수를 int형으로 반환하는 메소드이다.  
				lblResultCnt.setText(Integer.toString(ConSQL.getResultCnt()));
			}
		});
		frame_1.getContentPane().add(btnNewButton);
		 	
		JLabel lblNewLabel = new JLabel("\uB0A0\uC9DC :");
		lblNewLabel.setBounds(261, 34, 57, 15);
		frame_1.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\uAC80\uC0C9 \uACB0\uACFC \uC218 :");
		lblNewLabel_1.setBounds(619, 37, 82, 15);
		frame_1.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("\uAC74");
		lblNewLabel_3.setBounds(732, 37, 57, 15);
		frame_1.getContentPane().add(lblNewLabel_3);
		
		//달력 컴포넌트 설정///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
		dateChooser = new JDateChooser();
		dateChooser.setBounds(307, 31, 153, 21);
		//캘린더를 2015/07/01 로 디폴트 설정
		Date defaultDate = new Date(2015-1900,06,01); //기존의 1900/01/00 에서 더한값
		dateChooser.setDate(defaultDate);
		frame_1.getContentPane().add(dateChooser);

	}
	
	//테이블 내용 우측 정렬하는 메소드////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void tableCellRight(JTable t){

      DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // 디폴트테이블셀렌더러를 생성
      dtcr.setHorizontalAlignment(SwingConstants.RIGHT); // 오른쪽 정렬로 설정
     
      TableColumnModel tcm = t.getColumnModel(); // 정렬할 테이블의 컬럼모델을 가져옴
     
      //전체 열에 지정
      //for(int i = 0 ; i < tcm.getColumnCount() ; i++){
      //tcm.getColumn(i).setCellRenderer(dtcr);  
      // 컬럼모델에서 컬럼의 갯수만큼 컬럼을 가져와 for문을 이용하여
      // 각각의 셀렌더러를 아까 생성한 dtcr에 set해줌
      //}
       
      //특정 열에 지정하여 정렬
      tcm.getColumn(3).setCellRenderer(dtcr);
      tcm.getColumn(4).setCellRenderer(dtcr);
      tcm.getColumn(5).setCellRenderer(dtcr);
      tcm.getColumn(6).setCellRenderer(dtcr);
      tcm.getColumn(7).setCellRenderer(dtcr);
      tcm.getColumn(8).setCellRenderer(dtcr);
      tcm.getColumn(9).setCellRenderer(dtcr);
      
      //No, 지사명, 기관명 셀은 가운데 정렬로
      DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer(); // 디폴트테이블셀렌더러를 생성
      dtcr1.setHorizontalAlignment(SwingConstants.CENTER); // 오른쪽 정렬로 설정
      TableColumnModel tcm1 = t.getColumnModel();
      tcm1.getColumn(0).setCellRenderer(dtcr1);
    }
    
    //테이블 셀 크기 조절 메소드////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void tableCellSize(JTable t){
    	 TableColumnModel tcm = t.getColumnModel(); 
    	 tcm.getColumn(0).setPreferredWidth(5);   
    	 tcm.getColumn(1).setPreferredWidth(30);   
    	 tcm.getColumn(2).setPreferredWidth(90);   
    	 tcm.getColumn(5).setPreferredWidth(40);   
    	 tcm.getColumn(7).setPreferredWidth(40);   
    	 
    }
}
