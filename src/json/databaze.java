/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import java.util.ArrayList;

/**
 *
 * @author Tom
 */
public class databaze {
    private ArrayList<Zaznam> zaznamy;
    private ArrayList<Region> regiony;
    
    public databaze () {
        zaznamy = new ArrayList<>();
        regiony = new ArrayList<>();
    }
    public void pridejZaznam (String code, String name, String unit) {
        zaznamy.add (new Zaznam ( code, name, unit));
    }
    public void pridejRegion (String code, String name) {
        regiony.add (new Region ( code, name));
    }
            
    
}
