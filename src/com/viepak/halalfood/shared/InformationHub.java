package com.viepak.halalfood.shared;

public class InformationHub {
	
	private static InformationHub informationHub = null;
	
	private User user;
	
	private InformationHub(){}
	
	public static InformationHub GetInstance(){
		if(informationHub == null)
			informationHub = new InformationHub();
		
		return informationHub;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
