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
public class EndEditProcess implements MessageProcess{

    public String handle(String[] components) {
        
        String fromId = components[1];
        String title = components[2];
        
        ResoucesServerManager.addContentToGroupFile(title);
        
        return "";
    }
    
}
