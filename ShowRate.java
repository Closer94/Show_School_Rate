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
		//������ ���� //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
		frame_1 = new JFrame();
		frame_1.setTitle("\uB0A0\uC9DC\uBCC4 \uB4F1\uD558\uAD50\uC728 \uAC80\uC0C9 \uD504\uB85C\uADF8\uB7A8");
		frame_1.setBounds(100, 100, 1157, 329);
		frame_1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_1.getContentPane().setLayout(null);
		
		//���̺� ���� ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//ó�� ���α׷��� �����Ҷ� ����Ʈ ���̺��� ������/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		headers = new String[]{"No", "�����", "�����", "��ü �л��� ��", "�� � �л� ��" ,"���", "�� �ϱ� �л��� ��", "�ϱ���", "�� �ϱ��� �л� ��", "�� I��ư �л� ��"};
		DefaultTableModel model = new DefaultTableModel(data, headers); //header �� data�� ������� model ��ü ���� (�̶� data�� �������)
		table = new JTable(model); //���̺� ����
		tableCellSize(table); //���̺� �� ũ�� �������ִ� �޼ҵ�
		
		//��ũ�ѹ� �߰�  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		scrollPane = new JScrollPane();
		scrollPane.setBounds(46, 76, 1034, 175);
		frame_1.getContentPane().add(scrollPane); //��ũ�ѹٸ� frame_1�� �߰�
		scrollPane.setViewportView(table); //table�� �ִ� �����͸� ��ũ�ѹٷ� ��Ÿ����
		
		//�˻� ������ ��Ÿ���� ��////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JLabel lblResultCnt = new JLabel("0");
		lblResultCnt.setBounds(703, 37, 39, 15);
		frame_1.getContentPane().add(lblResultCnt);
		
		//�˻� ��ư ���� //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JButton btnNewButton = new JButton("\uAC80\uC0C9");
		btnNewButton.setBounds(472, 30, 97, 23);
		btnNewButton.addActionListener(new ActionListener() { //��ư Ŭ���� �̺�Ʈ �޼ҵ�
			public void actionPerformed(ActionEvent e) {
				//Ķ�������� ������ ��¥�� YYYY/MM/DD�� ��ȯ���־� inputDate ������ �־��ش�.
				Date result = dateChooser.getDate();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				String inputDate = sdf.format(result);
				
				//inputDate =  textField.getText(); //�ؽ�Ʈ �ڽ��� �Է��� ��¥�� inputDate�� ����
				//�˻��� ��¥�� mssql������ �����Ͽ� ��ȸ�� ����� String[][] Ÿ������ ��ȯ///////////////////////////////////////////////////////////////////////////////////////////
				
				try {
					data = ConSQL.getDate(inputDate); 	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
				headers = new String[]{"No", "�����", "�����", "��ü �л��� ��", "�� � �л� ��" ,"���", "�� �ϱ� �л��� ��", "�ϱ���", "�� �ϱ��� �л� ��", "�� I��ư �л� ��"};
				DefaultTableModel model = new DefaultTableModel(data, headers); //��ȸ�� ����� ���� data�� ������� table ����
				table.setModel(model);
				tableCellRight(table); //�÷� ������ �����ϴ� �޼ҵ�		
				tableCellSize(table); //���̺� �� ũ�� �������ִ� �޼ҵ�
				scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum()); //�˻��� �׻� ��ũ�ѹ� ������ ��ġ 
				//�˻� ������ ��Ÿ���� �󺧿� ������ ������ �߰�
				//getResultCnt()�޼ҵ�� �ش� ��¥�� ��ȸ�� ����� ���� �� ������ int������ ��ȯ�ϴ� �޼ҵ��̴�.  
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
		
		//�޷� ������Ʈ ����///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
		dateChooser = new JDateChooser();
		dateChooser.setBounds(307, 31, 153, 21);
		//Ķ������ 2015/07/01 �� ����Ʈ ����
		Date defaultDate = new Date(2015-1900,06,01); //������ 1900/01/00 ���� ���Ѱ�
		dateChooser.setDate(defaultDate);
		frame_1.getContentPane().add(dateChooser);

	}
	
	//���̺� ���� ���� �����ϴ� �޼ҵ�////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void tableCellRight(JTable t){

      DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // ����Ʈ���̺��������� ����
      dtcr.setHorizontalAlignment(SwingConstants.RIGHT); // ������ ���ķ� ����
     
      TableColumnModel tcm = t.getColumnModel(); // ������ ���̺��� �÷����� ������
     
      //��ü ���� ����
      //for(int i = 0 ; i < tcm.getColumnCount() ; i++){
      //tcm.getColumn(i).setCellRenderer(dtcr);  
      // �÷��𵨿��� �÷��� ������ŭ �÷��� ������ for���� �̿��Ͽ�
      // ������ ���������� �Ʊ� ������ dtcr�� set����
      //}
       
      //Ư�� ���� �����Ͽ� ����
      tcm.getColumn(3).setCellRenderer(dtcr);
      tcm.getColumn(4).setCellRenderer(dtcr);
      tcm.getColumn(5).setCellRenderer(dtcr);
      tcm.getColumn(6).setCellRenderer(dtcr);
      tcm.getColumn(7).setCellRenderer(dtcr);
      tcm.getColumn(8).setCellRenderer(dtcr);
      tcm.getColumn(9).setCellRenderer(dtcr);
      
      //No, �����, ����� ���� ��� ���ķ�
      DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer(); // ����Ʈ���̺��������� ����
      dtcr1.setHorizontalAlignment(SwingConstants.CENTER); // ������ ���ķ� ����
      TableColumnModel tcm1 = t.getColumnModel();
      tcm1.getColumn(0).setCellRenderer(dtcr1);
    }
    
    //���̺� �� ũ�� ���� �޼ҵ�////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void tableCellSize(JTable t){
    	 TableColumnModel tcm = t.getColumnModel(); 
    	 tcm.getColumn(0).setPreferredWidth(5);   
    	 tcm.getColumn(1).setPreferredWidth(30);   
    	 tcm.getColumn(2).setPreferredWidth(90);   
    	 tcm.getColumn(5).setPreferredWidth(40);   
    	 tcm.getColumn(7).setPreferredWidth(40);   
    	 
    }
}
