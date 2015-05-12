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
public interface IRekeningTransactie
{
    /**
     * Het saldo van deze bankrekening wordt met het meegegeven bedrag aangepast, tenzij het
     * saldotekort groter wordt dan het maximale krediet, dan verandert er niets.
     *
     * @param bedrag Het bedrag dat overgemaakt moet worden, is ongelijk aan 0
     * @return (saldo + bedrag) >= -(maximaal krediet)
     */
    boolean saldoBijwerken(double bedrag);
}
