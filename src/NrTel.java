
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
public abstract class NrTel implements Comparable,Serializable{
    
    String nrTel;
    
    public NrTel(String t){
        if(validareNumar(t)){
            this.nrTel=t;
        }
        
    }

   //metdoa abstracta de validare a numarului, care va fi rescrisa in subclase
    public abstract boolean validareNumar(String nr);
    
    @Override
    public boolean equals(Object nrT){
        return (((NrTel) nrT).nrTel.compareTo(nrTel))==0;
    }
    
    
    @Override
    public int compareTo(Object nrT) {
    int dif=((NrTel)nrT).nrTel.compareToIgnoreCase(nrTel);
    return dif;
    }
    
    
    @Override
    public String toString(){
        return nrTel;
    }


}
