/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageProcess;

import ResourceManager.ResoucesServerManager;
import clientdao.GroupInfoDAO;

/**
 *
 * @author Admin
 */
public class RemoveProcess implements MessageProcess {
    
    private final String REMOVE = "REMOVE";
    private final String DEFAULTBRIDGE = "_";
    
    public String handle(String[] components) {
        String fromId = components[1];
        String title = components[2];
        String offSet = components[3];
        String length = components[4];
        String message = REMOVE + DEFAULTBRIDGE
                + fromId + DEFAULTBRIDGE
                + title + DEFAULTBRIDGE
                + offSet + DEFAULTBRIDGE
                + length;
        GroupInfoDAO.getGroup().get(title).getContent().delete(Integer.parseInt(offSet), Integer.parseInt(offSet) + Integer.parseInt(length));
        System.out.println(GroupInfoDAO.getGroup().get(title).getContent().toString());
        ResoucesServerManager.sendMessageToGroupClient(message, fromId, title);
        return "";
        
    }

}
