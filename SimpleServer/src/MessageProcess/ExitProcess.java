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
public class ExitProcess implements MessageProcess{
    private final String DEFAULTBRIDGE = "_";
    public String handle(String[] components) {
        String id = components[1];
        ResoucesServerManager.removeClientInfoFromList(id);
        //lam them gi do nua, chang han, thong bao.
        return "";
    }
    
}
