/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tom
 */
public class databaze {
    private ArrayList<Zaznam> zaznamy;
    private ArrayList<Region> regiony;
    private ArrayList<Station> stanice;
    private ArrayList<Component> component;
    
    public databaze () {
        zaznamy = new ArrayList<>();
        regiony = new ArrayList<>();
        stanice = new ArrayList<>();
        component = new ArrayList<>();
        
    }
    public void pridejZaznam (String code, String name, String unit) {
        zaznamy.add (new Zaznam ( code, name, unit));
    }
    public void pridejRegion (String code, String name) {
        regiony.add (new Region ( code, name));
    }
    public void pridejStation (String code, String name, String classif,String ix,String region) {
        stanice.add (new Station ( code, name,classif,ix,region));
    }    
    public Component pridejComponent (String code, String stanice) {
        return new Component ( code,stanice);
    }      
    public void ulozComponent (Component o) {
        component.add(o);
    }   
    public ArrayList<Region> vypisRegiony() {
        ArrayList<Region> nalezene = new ArrayList<>();
        for (Region z : regiony) {
                 System.out.println(z.toString());
                   nalezene.add(z);
        }
        return nalezene;
    }  
    public ArrayList<Station> vypisStanice() {
        ArrayList<Station> nalezene = new ArrayList<>();
        for (Station z : stanice) {
                 System.out.println(z.toString());
                   nalezene.add(z);
        }
        return nalezene;
    } 
    public ArrayList<Station> vypisStanice(String code) {
        ArrayList<Station> nalezene = new ArrayList<>();
        for (Station z : stanice) {
                if (z.getRegion().equals(code)){
                 System.out.println(z.toString());
                   nalezene.add(z);
                }
        }
        return nalezene;
    }     
    // vypise komponenty stanice
    public HashMap<String,Map> vypisStanici( String code) {
        //ArrayList<Station> nalezene = new ArrayList<>();
       HashMap<String,Map> nalezene = new HashMap<>();
        for (Station z : stanice) {
            if (z.getCode().equals(code)) {                
                System.out.println(z.toString());
                
                for (Component c : component ){
                Map<String, String> words = new HashMap<>();    
                   if (c.getStanice().equals(z.getCode())) {                
                        System.out.println(c.toString()); 
                        words.put("code", c.getCode());
                        words.put("val",c.getVal ());
                        words.put("interval",c.getInt ());
                        words.put("index",c.getIx ());
                        nalezene.put(c.getCode(),words);
                    }
                }
                    
                   //nalezene.add(z); 
            }
                
        }
        System.out.println(nalezene.get("O3").get("val") + " " + nalezene.get("SO2").get("val")); 
        return nalezene;
    }  
    public ArrayList<Region>  vypisRegion( String code) {
        ArrayList<Region> nalezene = new ArrayList<>();
        for (Region z : regiony) {
            if (z.getCode().equals(code)) {                
                System.out.println(z.toString());
                   nalezene.add(z); 
            }
                
        }
        return nalezene;
    } 
}
