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
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Jelle
 */
public class CentraleServer extends Application
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        try
        {
            String IP = InetAddress.getLocalHost().getHostAddress();
            int port = 1098;
            Registry reg = LocateRegistry.createRegistry(port);
            ICentrale centrale = new Centrale();
            reg.rebind("centrale", centrale);
            System.out.println("De centrale is opgestart op IP: " + IP + " port: " + port);
        } catch (IOException ex)
        {
            Logger.getLogger(CentraleServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        StackPane root = new StackPane();
        Label lbl = new Label();
        lbl.setText("De centrale is online!");
        root.getChildren().add(lbl);
        Scene scene = new Scene(root, 300, 300);
        primaryStage.setTitle("Centrale Server");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
