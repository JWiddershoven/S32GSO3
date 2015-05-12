/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internetbankiereninterfaces;

/**
 *
 * @author Jelle
 */
public interface IBank
{
     /**
     * Creatie van een nieuwe bankrekening met een nieuw rekeningnummer; 
     * alleen als de klant, geidentificeerd door naam en plaats, nog niet bestaat 
     * wordt er ook een nieuwe klant aangemaakt.
     * 
     * @param naam van de eigenaar van de nieuwe bankrekening
     * @param plaats de woonplaats van de eigenaar van de nieuwe bankrekening
     * @return -1 zodra de naam of plaats een lege string is en anders het nummer van de
     *         nieuwe bankrekening
     */
    int openRekening(String naam, String plaats);

    /**
     * Er wordt een bedrag overgemaakt van de bankrekening (bron) naar de andere
     * bankrekening (bestemming), mits het afschrijven van het bedrag
     * van de bron rekening niet lager wordt dan de kredietlimiet van deze
     * rekening. 
     * 
     * @param bron
     * @param bestemming
     *            ongelijk aan bron
     * @param bedrag
     *            is groter dan 0
     * @return true als de overmaking is gelukt, anders false
     * 
     * @throws IllegalArgumentException indien de bron gelijk is aan de bestemming of een van de tweede rekeningen niet bestaat
     */
    boolean maakOver(int bron, int bestemming, double bedrag)
            throws IllegalArgumentException;

    /**
     * @param nr
     * @return de bankrekening met nummer nr mits bij deze bank bekend, anders null
     */
    IRekening getRekening(int nr);

    /**
     * @return de naam van deze bank
     */
    String getName();
}
