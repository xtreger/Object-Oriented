package main;

import java.io.Serializable;

public class Customer implements Serializable {

	private String name;
	private int age;
	private String email;
	
	public Customer(String name, int age, String email) {
		super();
		this.name = name;
		this.age = age;
		this.email = email;
	}
	
	public Customer() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String toString() {
		return "Customer [name=" + name + ", age=" + age + ", email=" + email + "]";
	}

}
