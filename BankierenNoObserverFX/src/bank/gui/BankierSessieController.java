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
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
public class BankierSessieController extends UnicastRemoteObject implements Initializable, RemotePropertyListener
{

    @FXML
    private Hyperlink hlLogout;

    @FXML
    private TextField tfNameCity;

    @FXML
    private TextField tfAccountNr;

    @FXML
    private TextField tfBalance;

    @FXML
    private TextField tfAmount;

    @FXML
    private TextField tfToAccountNr;

    @FXML
    private Button btTransfer;

    @FXML
    private TextArea taMessage;

    private BankierClient application;
    private IBalie balie;
    private IBankiersessie sessie;

    public BankierSessieController() throws RemoteException
    {

    }

    public void setApp(BankierClient application, IBalie balie, IBankiersessie sessie) throws RemoteException
    {
        this.balie = balie;
        this.sessie = sessie;
        this.application = application;
        sessie.addListener(this, "Saldo");
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
            String from = "";
            String to = "";
            try
            {
                from = tfAccountNr.getText();
                to = tfToAccountNr.getText();
            } catch (NumberFormatException ex)
            {
                ex.printStackTrace();
            }
            if (from.equals(to))
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
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                tfBalance.setText(saldo);
            }
        });

    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) throws RemoteException
    {
        String saldo = String.valueOf(pce.getNewValue());
        updateBalance(saldo);
    }
}
