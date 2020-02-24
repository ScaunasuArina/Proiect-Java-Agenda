
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
public class NrFix extends NrTel implements Serializable{

    public NrFix(String nrT) {
        super(nrT);
    }

      
    
    @Override
    public boolean validareNumar(String nr) {
        if(nr.length()==10)
            return true;
           
             else
                    throw new RuntimeException("Numarul de fix nu are 10 cifre!");
            
            
    }

    
}
