/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.gui;

import bank.bankieren.IRekening;
import bank.bankieren.Money;
import bank.internettoegang.IBalie;
import bank.internettoegang.IBankiersessie;
import fontys.observer.RemotePropertyListener;
import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;
import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author frankcoenen
 */
public class BankierSessieController implements Initializable, Serializable, RemotePropertyListener
{

    @FXML
    private transient Hyperlink hlLogout;

    @FXML
    private transient TextField tfNameCity;
    
    @FXML
    private transient TextField tfAccountNr;
    
    @FXML
    private transient TextField tfBalance;
    
    @FXML
    private transient TextField tfAmount;
    
    @FXML
    private transient TextField tfToAccountNr;
    
    @FXML
    private transient Button btTransfer;
    
    @FXML
    private transient TextArea taMessage;

    private transient BankierClient application;
    private IBalie balie;
    private IBankiersessie sessie;

    public void setApp(BankierClient application, IBalie balie, IBankiersessie sessie) throws RemoteException
    {
        this.balie = balie;
        this.sessie = sessie;
        this.application = application;
        IRekening rekening = null;
        try
        {
            rekening = sessie.getRekening();
            tfAccountNr.setText(rekening.getNr() + "");
            tfBalance.setText(rekening.getSaldo() + "");
            String eigenaar = rekening.getEigenaar().getNaam() + " te "
                    + rekening.getEigenaar().getPlaats();
            tfNameCity.setText(eigenaar);
        } catch (InvalidSessionException ex)
        {
            taMessage.setText("bankiersessie is verlopen");
            Logger.getLogger(BankierSessieController.class.getName()).log(Level.SEVERE, null, ex);

        } catch (RemoteException ex)
        {
            taMessage.setText("verbinding verbroken");
            Logger.getLogger(BankierSessieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    }

    @FXML
    private void logout(ActionEvent event)
    {
        try
        {
            sessie.logUit();
            application.gotoLogin(balie, "");
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void transfer(ActionEvent event)
    {
        try
        {
            int from = 0;
            int to = 0;
            try
            {
                from = Integer.parseInt(tfAccountNr.getText());
                to = Integer.parseInt(tfToAccountNr.getText());
            } catch (NumberFormatException ex)
            {
                ex.printStackTrace();
            }
            if (from == to)
            {
                taMessage.setText("can't transfer money to your own account");
            }
            long centen = (long) (Double.parseDouble(tfAmount.getText()) * 100);
            sessie.maakOver(to, new Money(centen, Money.EURO));
        } catch (RemoteException e1)
        {
            e1.printStackTrace();
            taMessage.setText("verbinding verbroken");
        } catch (NumberDoesntExistException | InvalidSessionException e1)
        {
            e1.printStackTrace();
            taMessage.setText(e1.getMessage());
        }
        
    }

    public void updateBalance(String saldo)
    {
        System.out.println(saldo);
        tfBalance.setText(saldo);
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) throws RemoteException
    {
        String saldo = String.valueOf(pce.getNewValue());
        updateBalance(saldo);
    }
}
