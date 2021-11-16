package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.ContactDao;

public class ContactList extends JFrame implements ActionListener, MouseListener{

	Vector v;
	Vector cols;
	
	JTable jTable = new JTable();
	JScrollPane pane;
	JPanel topPane;
	JButton btnInsert;
	
	ContactProc contactProc;
	
	public ContactList() {
		super("연락처목록");
		
		initComponent();
		
		setLocation(600, 250);
		setSize(350, 450);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	
	private void initComponent() { // jtable 클릭 표시 안나게 하기, 새로고침키, 에러
			jTable = new JTable();
		
			jTable.setModel( 
					new DefaultTableModel(getDatalist(), getColumn()) {
						public boolean isCellEditable(int row, int column) {
							return false;
						} 
				    });
			
			// 셀 너비 조절
			jTable.getColumnModel().getColumn(0).setPreferredWidth(10);
			jTable.getColumnModel().getColumn(1).setPreferredWidth(10);
			jTable.getColumnModel().getColumn(3).setPreferredWidth(10);
			
			// 내용 가운데 정렬
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			jTable.getColumnModel().getColumn(0).setCellRenderer(dtcr);
			jTable.getColumnModel().getColumn(1).setCellRenderer(dtcr);
			jTable.getColumnModel().getColumn(2).setCellRenderer(dtcr);
			jTable.getColumnModel().getColumn(3).setCellRenderer(dtcr);

			
		pane =  new JScrollPane( jTable );
		topPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnInsert = new JButton("연락처추가");
		topPane.add(btnInsert);
		add( pane );
		
		add(topPane, BorderLayout.NORTH);
		
		// 버튼 이벤트
		btnInsert.addActionListener(this);
		jTable.addMouseListener(this);	
	}
	

	private Vector getColumn() {
		Vector cols = new Vector();
		
		cols.add("No.");
		cols.add("이름");
		cols.add("전화번호");
		cols.add("관계");
		
		return cols;
	}


	private Vector getDatalist() {
		ContactDao cDao = new ContactDao();
		Vector v = cDao.getContactList();
		
		return v;
	}

	// 마우스 이벤트 처리
	@Override
	public void mouseClicked(MouseEvent e) {
		JTable jTable = (JTable)e.getSource();
		int row = jTable.getSelectedRow();    
		int col = jTable.getSelectedColumn(); 
		int id = (int)jTable.getValueAt(row, 0);  
		if( contactProc != null  )   // 열려있는 contactProc닫기
			contactProc.dispose();
		contactProc = new ContactProc(id, this); 
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	// 버튼 이벤트 처리
	@Override
	public void actionPerformed(ActionEvent e) {
		if( contactProc != null  )   // 열려있는 contactProc닫기
			contactProc.dispose();
		contactProc = new ContactProc(this); 
	}
	
	// 새로고침
	public void jTableRefresh() {
		jTable.setModel(new DefaultTableModel(getDatalist(), getColumn()) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		// 셀 너비 조절
		jTable.getColumnModel().getColumn(0).setPreferredWidth(10);
		jTable.getColumnModel().getColumn(1).setPreferredWidth(10);
		jTable.getColumnModel().getColumn(3).setPreferredWidth(10);
		
		// 내용 가운데 정렬
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		jTable.getColumnModel().getColumn(0).setCellRenderer(dtcr);
		jTable.getColumnModel().getColumn(1).setCellRenderer(dtcr);
		jTable.getColumnModel().getColumn(2).setCellRenderer(dtcr);
		jTable.getColumnModel().getColumn(3).setCellRenderer(dtcr);
		jTable.repaint();	
	}
	
	// 실행
	public static void main(String[] args) {
		new ContactList();
	}
}
