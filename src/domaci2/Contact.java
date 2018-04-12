package domaci2;

public class Contact {
	private String name;
	private String surname;
	private String contact;
	
	public Contact(String n, String s, String c) {
		this.setName(n);
		this.setSurname(s);
		this.setContact(c);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@Override
	public String toString() {
		return this.name + " " + this.surname + " " + this.contact;
	}
	
	
}
