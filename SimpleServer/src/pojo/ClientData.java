package pojo;

import ResourceManager.*;

public class ClientData{

	
	private String password;
	private String id;
	public boolean isUsing = false;

    public ClientData() {
 
    }
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public ClientData(String id, String password)
	{
		this.password = password;
		this.id = id;
	}
	
	
	public boolean compareData(ClientData data)
	{
		if(data.getId().equals(id) && data.getPassword().equals(password))
			return true;
		return false;
	}
	
}
