package MessageProcess;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ResourceManager.ResoucesServerManager;

public class SignUpMessageProcess implements MessageProcess{

	private final String VALIDID = "ID already taken";
	private final String DOESNOTMATCHPASS = "Confirm password doesn't match";
	private final String OVERPASSLENGTH = "Password must be in 6-15 character";
	private final String OVERIDLENGTH = "ID must be in 4-15 character";
        private final String SPECIALPASS = "Password cannot contain '&' '_'";
	private final String SPECIALCHAR = "ID cannot contain special characters";
	private final String SUCCESS = "SIGNUPSUCCESS";
	private Pattern regex = Pattern.compile("[$&+,:;=?@#|_]");
        private Pattern passRegex = Pattern.compile("&_");
	
	public String handle(String [] components) {
		// TODO Auto-generated method stub
		String id = components[1];
		String pass = components[2];
		String confirmPass = components[3];
		
		
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
		if(!confirmPass.equals(pass))
			return DOESNOTMATCHPASS;
		if(!ResoucesServerManager.isAvailableId(id))
                {
                    ResoucesServerManager.addClientDataToServer(id, pass);
                    return SUCCESS;
                }
		else
			return VALIDID;
	}


}
