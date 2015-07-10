/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientdao;

import ResourceManager.GroupInfo;
import java.util.HashMap;

/**
 *
 * @author Admin
 */
public class GroupInfoDAO {
    
    private HashMap<String, GroupInfo> group;
    private static GroupInfoDAO uniqueInstance = new GroupInfoDAO();
    
    private GroupInfoDAO()
    {
        group = new HashMap<String, GroupInfo>();
    }
    
    public static GroupInfoDAO getInstance()
    {
        return uniqueInstance;
    }
    
    public static void addToGroup(String title, String id)
    {
        if(getInstance().group.get(title) != null)
        {
            GroupInfo info = getInstance().group.get(title);
            if(!info.isExistedInListId(id))
                info.addToListId(id);
        }
        else
        {
            getInstance().group.put(title, new GroupInfo(id));
        }
    }
    public static HashMap<String, GroupInfo> getGroup()
    {
        return getInstance().group;
    }
}
