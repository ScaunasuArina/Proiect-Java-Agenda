
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Arina
 */
public class NrMobil extends NrTel implements Serializable {

    public NrMobil(String nrT) {
        super(nrT);
    }

    
    
    @Override
    public boolean validareNumar(String nr) {
        if(nr.length()==10){
           
            //validare existenta retea
            if(    nr.startsWith("076")
               ||  nr.startsWith("078")//TELEKOM
               ||  nr.startsWith("077")//Digi
               ||  nr.startsWith("074")
               ||  nr.startsWith("075")//Orange
               ||  nr.startsWith("072")
               ||  nr.startsWith("073")//Vodafone     
              )
                    return true;
            
            else 
                    throw new RuntimeException("Numarul nu este intr-o retea existenta in Romania!");
        } 
        
        
        else  throw new RuntimeException("Numarul de mobil nu are 10 cifre!");
            
       
       
    }

  
   

    
    
}
