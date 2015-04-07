/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbannerapplicatie;

/**
 *
 * @author Jordy
 */
public interface Publisher {
    
    
  /**
   * listener abonneert zich op PropertyChangeEvent's zodra property is gewijzigd
   * @param listener PropertyListener
   * @param property mag null zijn, dan abonneert listener zich op alle
   * properties
   */
  void addListener(RemotePropertyListener listener, String property);

  /**
   * het abonnement van listener voor PropertyChangeEvent's mbt property wordt
   * opgezegd
   * @param listener PropertyListener
   * @param property mag null zijn, dan worden alle abonnementen van listener
   * opgezegd
   */
  void removeListener(RemotePropertyListener listener, String property);


  void removeAllListeners();

}
