package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.ContactDao;
import model.ContactVo;

public class ContactProc extends JFrame implements ActionListener{
	JPanel p, p2, p3;
	JTextField txtId, txtName, txtTel, txtEmail, txtAddr;
	JComboBox cbRel;
	JButton btnInsert, btnUpdate, btnDelete, btnReset;
	ContactList contactList;
	
	GridBagLayout gb;
	GridBagConstraints gbc;
	
	public ContactProc() {
		setTitle("상세정보");
		initComponent();
		
		setLocation(1000, 200);
		setSize(300, 400);
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setVisible(true);
	}
	
	public ContactProc(ContactList contactList) {
		this();
		this.contactList = contactList;
		
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
	}

	public ContactProc(int id, ContactList contactList) {
		this();
		this.contactList = contactList;
		
//		txtId.setText(String.valueOf(id));
		
		viewData(id);
		btnInsert.setEnabled(false);
	}

	private void viewData(int id) {
		ContactDao cDao = new ContactDao();
		ContactVo vo = cDao.getContact(id); 
		setViewData(vo); 
	} 

	// 화면에 뿌리기
	private void setViewData(ContactVo vo) {
		txtId.setText(String.valueOf(vo.getId()));
		txtName.setText(vo.getName());
		txtTel.setText(vo.getTel());
		cbRel.setSelectedItem(vo.getRelation());
		txtEmail.setText(vo.getEmail());
		txtAddr.setText(vo.getAddress());
	}

	private void initComponent() {
		gb = new GridBagLayout();
		
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		
		p3 = new JPanel();
		p3.setLayout(gb);
		
		setLayout(new BorderLayout());
		
		// 식별번호
		p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblId = new JLabel("No.");
		txtId = new JTextField(3);
		p2.add(lblId);
		p2.add(txtId);
		txtId.setEditable(false);
		add(p2, BorderLayout.PAGE_START);
	
		// 이름
		JLabel lblName = new JLabel("이름");
		txtName = new JTextField(30);
		gbAdd(lblName, 0, 0, 1, 1);
		gbAdd(txtName, 1, 0, 3, 1);
		
		// 전화번호
		JLabel lblTel = new JLabel("전화번호");
		txtTel = new JTextField(30);
		gbAdd(lblTel, 0, 1, 1, 1);
		gbAdd(txtTel, 1, 1, 3, 1);
		// 관계
		JLabel lblRel = new JLabel("관계");
		String[] arrRel = {"가족", "친구", "친척", "동료", "기타"};
		cbRel = new JComboBox(arrRel);
		gbAdd(lblRel, 0, 2, 1, 1);
		gbAdd(cbRel, 1, 2, 3, 1);
		// 이메일
		JLabel lblEmail = new JLabel("이메일");
		txtEmail = new JTextField(30);
		gbAdd(lblEmail, 0, 3, 1, 1);
		gbAdd(txtEmail, 1, 3, 3, 1);
		// 주소
		JLabel lblAddr = new JLabel("주소");
		txtAddr = new JTextField(30);
		gbAdd(lblAddr, 0, 4, 1, 1);
		gbAdd(txtAddr, 1, 4, 3, 1);
		// 버튼
		p = new JPanel();
		btnInsert = new JButton("추가");
		btnUpdate = new JButton("수정");
		btnDelete = new JButton("삭제");
		btnReset = new JButton("취소");
		btnReset.setForeground(Color.RED);
		
		p.add(btnInsert);
		p.add(btnUpdate);
		p.add(btnDelete);
		p.add(btnReset);
		
		add(p, BorderLayout.PAGE_END);
		add(p3);
		
		// 버튼 이벤트
		btnInsert.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);
		btnReset.addActionListener(this);
	}

	private void gbAdd(JComponent c, int x, int y, int w, int h) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		gb.setConstraints(c, gbc);
		gbc.insets = new Insets(2, 2, 2, 2);
		p3.add(c, gbc);
	}
	
	// 화면 값들을 vo에 저장
	private ContactVo getViewData(int i) {
		ContactVo vo = null;
		int id = 0;
		if(i != 1)
			id = Integer.parseInt(txtId.getText());
		String name = txtName.getText();
		String tel = txtTel.getText();
		String relation = (String)cbRel.getSelectedItem();
		String email = txtEmail.getText();
		String address = txtAddr.getText();
		
		if(i != 1)
			vo = new ContactVo(id, name, tel, relation, email, address);
		else
			vo = new ContactVo(name, tel, relation, email, address);
		return vo;
	}
	
	// 이벤트 처리
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "추가":
			insertContact();
			break;
		case "수정":
			updateContact();
			break;
		case "삭제":
			deleteContact();
			break;
		case "취소":
			resetContact();
			break;
		}
	}

	// 추가버튼 클릭
	private void insertContact() {
		ContactDao cDao = new ContactDao();
		ContactVo vo = getViewData(1);
		
		if(vo.getName().equals("")||vo.getTel().equals("")) {
			String  msg = "이름과 전화번호를 입력하세요";
			JOptionPane.showMessageDialog(this, msg);
			return;
		}
		
		String msg = "";
		int aftcnt = cDao.insertContact(vo);;
		if (aftcnt == 0) {
			msg = "추가되지 않았습니다";
		} else {
			contactList.jTableRefresh();
			msg = "추가되었습니다";
		}
		JOptionPane.showMessageDialog(this, msg);
		
		cDao.close();
		
		this.dispose();
	}

	// 수정버튼 클릭
	private void updateContact() {
		ContactDao cDao = new ContactDao();
		ContactVo vo = getViewData(2);
		
		if(vo.getName().equals("")||vo.getTel().equals("")) {
			String  msg = "이름과 전화번호를 입력하세요";
			JOptionPane.showMessageDialog(this, msg);
			return;
		}
			
		String msg = "";
		int aftcnt = cDao.updateContact(vo);
		if (aftcnt == 0) {
			msg = "수정되지 않았습니다";
		} else {
			contactList.jTableRefresh();
			msg = "수정되었습니다";
		}
		JOptionPane.showMessageDialog(this, msg);
		
		cDao.close();
		
		this.dispose();
	}

	// 삭제버튼 클릭
	private void deleteContact() {
		ContactDao cDao = new ContactDao();
		ContactVo vo = getViewData(2);

		int x = JOptionPane.showConfirmDialog(this,  "정말 삭제하시겠습니까?",
				"삭제", JOptionPane.YES_NO_OPTION);
		if(x == JOptionPane.NO_OPTION)
			return;
		
		String msg = "";
		int aftcnt = cDao.deleteContact(vo);
		if (aftcnt == 0) {
			msg = "삭제되지 않았습니다";
		} else {
			contactList.jTableRefresh();
			msg = "삭제되었습니다";
		}
		JOptionPane.showMessageDialog(this, msg);
		
		cDao.close();
		
		this.dispose();
	}

	// 취소버튼 클릭
	private void resetContact() {
		txtName.setText("");
		txtTel.setText("");
		cbRel.setSelectedIndex(0);
		txtEmail.setText("");
		txtAddr.setText("");
	}

//	// test
//	public static void main(String[] args) {
//		new ContactProc();
//	}

	
}
