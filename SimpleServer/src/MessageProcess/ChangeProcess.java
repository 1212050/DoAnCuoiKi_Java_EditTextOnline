/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageProcess;

import ResourceManager.ResoucesServerManager;

/**
 *
 * @author Admin
 */
public class ChangeProcess implements MessageProcess {

    private final String DEFAULTBRIDGE = "_";
    private final String CHANGE = "CHANGE";
    
    public String handle(String[] components) {
        String fromId = components[1];
        String title  = components[2];
        String offSet = components[3];
        String length = components[4];
        String style = components[5];
        String size = components[6];
        String font = components[7];
        String color = components[8];

        String message = CHANGE + DEFAULTBRIDGE
                + fromId + DEFAULTBRIDGE
                + title + DEFAULTBRIDGE
                + offSet+ DEFAULTBRIDGE
                + length + DEFAULTBRIDGE
                + style + DEFAULTBRIDGE
                + size + DEFAULTBRIDGE
                + font + DEFAULTBRIDGE
                + color;
        ResoucesServerManager.sendMessageToGroupClient(message, fromId, title);
        return "";
    }

}
