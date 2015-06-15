/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.centrale;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.*;

/**
 *
 * @author Jelle
 */
public class CentraleServer
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        new CentraleServer().startCentrale();
    }
       
    public boolean startCentrale()
    {
        boolean succes = false;
        try
        {
            String IP = InetAddress.getLocalHost().getHostAddress();
            int port = 1099;
            Registry reg = LocateRegistry.createRegistry(port);
            Centrale centrale = new Centrale();
            Naming.rebind("centrale", centrale);
            System.out.println("De centrale is opgestart op IP: " + IP + " port: " + port);
            succes = true;
        }
        catch (IOException ex)
        {
            Logger.getLogger(CentraleServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return succes;
    }
    
}
