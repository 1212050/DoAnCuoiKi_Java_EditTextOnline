package MessageProcess;

import ResourceManager.ClientInfo;
import ResourceManager.ResoucesServerManager;
import clientdao.GroupInfoDAO;

public class TypedMessageProcess implements MessageProcess {

    private final String INSERT = "INSERT";
    private final String DEFAULTBRIDGE = "_";
    private final String SPECIALCHAR = "ENTER";

    public String handle(String[] components) {
        // TODO Auto-generated method stub
        String content;
        
        
        String fromId = components[1];
        String title = components[2];
        String text = components[3];
        String offSet = components[4];
        String message = INSERT + DEFAULTBRIDGE
                + fromId + DEFAULTBRIDGE
                + title + DEFAULTBRIDGE
                + text + DEFAULTBRIDGE
                + offSet;
        
        if (!text.equals(SPECIALCHAR)) {
            String style = components[5];
            String size = components[6];
            String font = components[7];
            String color = components[8];
            message += DEFAULTBRIDGE
                    + style + DEFAULTBRIDGE
                    + size + DEFAULTBRIDGE
                    + font + DEFAULTBRIDGE
                    + color;
            content = text;
        }
        else
        {
            content = "\n";
        }
         GroupInfoDAO.getGroup().get(title).getContent().insert(Integer.parseInt(offSet), content);
        ResoucesServerManager.sendMessageToGroupClient(message, fromId, title);
        return "";
    }

}
