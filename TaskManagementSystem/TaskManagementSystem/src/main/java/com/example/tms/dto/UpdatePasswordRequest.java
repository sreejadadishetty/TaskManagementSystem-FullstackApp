package com.example.tms.dto;

public class UpdatePasswordRequest {
	private Long id;
	
	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UpdatePasswordRequest [id=" + id + ", password=" + password + "]";
	}

	public UpdatePasswordRequest(Long id, String password) {
		super();
		this.id = id;
		this.password = password;
	}

	public UpdatePasswordRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	

}
