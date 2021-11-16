package model;

public class ContactVo {
	private int id;	
	private String name;
	private String tel;
	private String relation;
	private String email;
	private String address;
	
	public ContactVo() {
		
	}
	
	public ContactVo(String name, String tel, String relation, String email, String address) {
		this.name = name;
		this.tel = tel;
		this.relation = relation;
		this.email = email;
		this.address = address;
	}
	
	public ContactVo(int id, String name, String tel, String relation, String email, String address) {
		this.id = id;
		this.name = name;
		this.tel = tel;
		this.relation = relation;
		this.email = email;
		this.address = address;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "ContactVo [id=" + id + ", name=" + name + ", tel=" + tel + ", relation=" + relation + ", email=" + email
				+ ", address=" + address + "]";
	}
	
	
}
