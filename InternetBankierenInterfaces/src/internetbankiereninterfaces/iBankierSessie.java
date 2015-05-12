/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internetbankiereninterfaces;

import java.rmi.RemoteException;

/**
 *
 * @author Jordy
 */
public interface iBankierSessie {
    
    long GELDIGHEIDSDUUR = 600000; 
	/**
	 * @returns true als de laatste aanroep van getRekening of maakOver voor deze
	 *          sessie minder is dan GELDIGHEIDSDUUR en als de verbinding tussentijds
         *          niet is verbroken, 
	 *          anders false
	 */
	boolean isGeldig() throws RemoteException; 

	/**
         * Er wordt een bedrag overgemaakt naar de bankrekening met rekeningnr bestemming.
	 * 
	 * @param bron
	 * @param bestemming
	 *            is ongelijk aan rekeningnummer van deze bankiersessie
	 * @param bedrag
	 *            is groter dan 0
	 * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b> 
	 */
	boolean maakOver(int bestemming, double bedrag) throws RemoteException;

	/**
	 * sessie wordt beeindigd
	 */
	void logUit() throws RemoteException;

	/**
	 * @return de rekeninggegevens die horen bij deze sessie
	 * @throws RemoteException
	 */
	IRekening getRekening() throws RemoteException;
}
