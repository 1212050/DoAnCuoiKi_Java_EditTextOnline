/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientdao;


import dao.util.MySqlDataAccessHelper;
import java.sql.ResultSet;
import java.util.*;
import pojo.ClientData;

/**
 *
 * @author Admin
 */
public class ClientDataDAO {
    public static ArrayList<ClientData> getClientDataFromDB()
    {
        ArrayList<ClientData> clientListData = new ArrayList<ClientData>();
        try
        {
            MySqlDataAccessHelper helper = new MySqlDataAccessHelper();
            helper.open("serverdata");
            String sql = "SELECT * FROM CLIENTDATA";
            ResultSet rs = helper.executeQuery(sql);
            while(rs.next())
            {
                ClientData client = new ClientData();
                client.setId(rs.getString("id"));
                client.setPassword(rs.getString("password"));
                clientListData.add(client);
            }
            helper.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return clientListData;
    }
    public static void addClientDataToDB(String id, String pass)
    {
        try
        {
            MySqlDataAccessHelper helper = new MySqlDataAccessHelper();
            helper.open("serverdata");
            String sql = "INSERT INTO `serverdata`.`clientdata` (`id`, `password`) "
                    + "VALUES ('"+ id + "', '"+ pass +"');";
   
            helper.executeUpdate(sql); 
            helper.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
