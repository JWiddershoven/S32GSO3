/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aex.shared;

import java.io.Serializable;
import java.rmi.Remote;

/**
 *
 * @author Jelle
 */
public interface IFonds extends Serializable {
    
    /**
     * @return De naam van het fonds.
     */
    public String getNaam();
    
    /**
     * @return De koers van het fonds.
     */
    public double getKoers();
}
