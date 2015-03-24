/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbannerapplicatie;

/**
 *
 * @author Jelle
 */
public class MockEffectenBeurs implements IEffectenbeurs{

    private IFonds[] fondsen;
    
    public MockEffectenBeurs()
    {
        fondsen = new IFonds[5];
        fondsen[0] = new Fonds("Microsoft", 67.9);
        fondsen[1] = new Fonds("Apple", 66);
        fondsen[2] = new Fonds("Google", 68.3);
        fondsen[3] = new Fonds("Nokia", 55.3);
        fondsen[4] = new Fonds("Yahoo", 44.9);
    }
    @Override
    public IFonds[] getKoersen() {
        return this.fondsen;
    }
  
}
