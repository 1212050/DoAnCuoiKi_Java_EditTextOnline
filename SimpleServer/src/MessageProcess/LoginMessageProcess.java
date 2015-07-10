package MessageProcess;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ResourceManager.ClientInfo;
import ResourceManager.ResoucesServerManager;
import pojo.ClientData;


public class LoginMessageProcess implements MessageProcess{

	private final String WRONGDATA = "ID or Password wrong";
	private final String OVERPASSLENGTH = "Password must be in 6-15 character";
	private final String OVERIDLENGTH = "ID must be in 4-15 character";
	private final String SPECIALCHAR = "ID cannot contain special characters";
        private final String SPECIALPASS = "Password cannot contain '&' '_'";
	private final String IDISUSING = "ID is using by another account";
	private final String SUCCESS = "LOGINSUCCESS";
	private Pattern regex = Pattern.compile("[$&+,:;=?@#|_]");
        private Pattern passRegex = Pattern.compile("&_");
	
	private PrintWriter writer;
	
	public LoginMessageProcess(PrintWriter writer)
	{
		this.writer = writer;
	}
	
	public String handle(String [] components) {
		// TODO Auto-generated method stub
		String id = components[1];
		String pass = components[2];
		Matcher matcher = regex.matcher(id);
		Matcher matcherPass = passRegex.matcher(pass);
                
                
		if(id.length() < 4 || id.length() > 15)
			return OVERIDLENGTH;
		if(pass.length() < 6 || pass.length() > 15)
			return OVERPASSLENGTH;
		if(matcher.find())
			return SPECIALCHAR;
                if(matcherPass.find())
			return SPECIALPASS;
		if(!ResoucesServerManager.isAvailableClient(new ClientData(id, pass)))
			return WRONGDATA;
		
		ClientData clientData = ResoucesServerManager.getClientData(id);
		if(clientData.isUsing)
			return IDISUSING;
		else
		{
			ResoucesServerManager.addToListClientInfo(new ClientInfo(id, writer));
			
			
			clientData.isUsing = true;
			return SUCCESS;
		}
	}

}
