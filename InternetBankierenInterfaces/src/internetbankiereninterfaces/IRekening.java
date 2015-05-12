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
public interface IRekening
{
    /**
     * @return Het rekeningnummer van deze rekening.
     */
    int getRekNr();

    /**
     * @return Het saldo van deze rekening.
     */
    double getSaldo();

    /**
     * @return De eigenaar van deze rekening.
     */
    IKlant getEigenaar();

    /**
     * @return Het kredietlimiet van deze rekening.
     */
    double getKredietLimiet();
}
