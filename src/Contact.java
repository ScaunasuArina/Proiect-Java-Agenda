
import java.io.Serializable;
import java.time.LocalDate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Arina
 */
public class Contact implements Serializable {
    
    private String nume;  //cel putin 2 litere
    private String prenume;  //cel putin 2 litere
    private LocalDate data_nastere;   //Format standard: AAAA.LL.ZZ
    private NrTel nrTel;     
    
    
    
    public String getNume(){           
            return nume;    
    }
    public void setNume(String n){
        nume=n;
    }
    
    
    
    public String getPrenume(){
        return prenume;
    }
    public void setPreume(String p){
        prenume=p;
    }
    
    
    
    public LocalDate getDataNastere(){
        return data_nastere;
    }
    public void setDataNastere(LocalDate d){
        data_nastere=d;
    }
    
    
    
    public NrTel getNrTel(){
        return nrTel;
    }
    public void setNrTel(String nr){
        nrTel.nrTel=nr;
    }
    
    
    
    public Contact(String n, String p, LocalDate d, String nr){
     
           //validare NUME
           if(n.length()<2) {               
              throw new RuntimeException("Nume prea scurt!");                      
           }
           else{
                this.nume=n;
           }
           
           //validare PRENUME
           if(p.length()<2) {               
               throw new RuntimeException("Prenume prea scurt!");                     
            
           }
           else{
               this.prenume=p;
           }
               
           //validare NrTel
           if (nr.startsWith("07") || nr.startsWith("03") || nr.startsWith("02")) {

                     if (nr.startsWith("07")) {
                         nrTel = new NrMobil(nr);
                    }
                     if (nr.startsWith("02") || nr.startsWith("03")) {
                         nrTel = new NrFix(nr);
                     }
                   
                }else {
                    throw new RuntimeException("Numarul NU incepe cu 07,02 sau 03!");
                }
           
            //validare AN NASTERE
            //se pot adauga verificari si pentru zi si luna
            if(d.getYear()> LocalDate.now().getYear() || d.getYear()<1920){
                         throw new RuntimeException("An invalid!");
            }else{
                this.data_nastere=d;
            }
                
                
                
                
    }
     
    
    @Override
    public String toString(){
    
        return(nume+" "+prenume+","+data_nastere+","+nrTel);
    }
    
    
     @Override
    public boolean equals(Object c){
        if(((Contact)c).nume.equalsIgnoreCase(nume) 
                && ((Contact)c).prenume.equalsIgnoreCase(prenume) 
                && ((Contact)c).data_nastere.equals(data_nastere) 
                && ((Contact)c).nrTel.equals(nrTel)
          )
            return true;
    
        else
            return false;
        
    
    }

    
}
