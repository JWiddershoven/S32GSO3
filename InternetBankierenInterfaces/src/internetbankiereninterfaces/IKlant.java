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
public interface IKlant
{

    /**
     * @return De naam van de klant, mits deze bestaat.
     */
    String getNaam();

    /**
     * @return De plaats van de klant, mits deze bestaat.
     */
    String getPlaats();
}
