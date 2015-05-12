/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internetbankiereninterfaces;

/**
 *
 * @author Jordy
 */
public interface iLoginAccount {
    
    /**
     * @return De gebruikersnaam van dit inlogaccount.
     */
    String getNaam();

    /**
     * @return Het rekeningnummer van dit inlogaccount.
     */
    int getReknr();

    /**
     * @param wachtwoord Het wachtwoord dat gecontroleerd wordt met het wachtwoord van het inlogaccount.
     * @return True als het meegegeven wachtwoord overeenkomt met het wachtwoord van het inlogaccount, anders false.
     */
    boolean checkWachtwoord(String wachtwoord);
}
