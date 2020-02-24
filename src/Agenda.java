
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.time.LocalDate;
import static java.time.LocalDate.now;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Arina
 */
public class Agenda {
    
    //  enum-ul folosit pentru odonare 
     enum CriteriuOrdonare {
        DUPA_NUME,
        DUPA_PRENUME, 
        DUPA_VARSTA, 
        DUPA_TELEFON
    }
  
    
    public List Contacte = new ArrayList();  //colectia de contacte 
    public List <Contact> l= new ArrayList();
    //se poate folosi si List(mentine ordinea elementelor), chiar daca permite elemente duplicat pt ca verificam inainte de adaugare sa nu avem contacte identice!
    //colectia map va fi populata in cadrul CONSTRUCTORULUI!(din cerinta proiectului)
    public TreeMap<Integer, Contact> map = new TreeMap<>(); //TreeMap-ul mentine ordinea elementelor, dar HashMap-ul nu!
    public Predicate<Contact> camp = (Contact c) -> true;
    public CriteriuOrdonare campCurent;  //Este implicit DUPA_NUME, acesta fiind primul criteriu din enum.
    
    
    public Agenda(){
        //......
    }
    
    
    
   //metode pentru ADAUGARE, STERGERE, MODIFICARE CONTACT
    
    public void Adauga(String n, String p, String d, String nr) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
        LocalDate data_nastere = LocalDate.parse(d, formatter);
        Contact nou = new Contact(n, p, data_nastere, nr);
        if (Contacte.contains(nou)) {
            throw new RuntimeException("Contactul exista in lista deja!");
        } else {
            Contacte.add(nou);
        }
    }
    
    
    
    public void Stergere(Contact c) {
        if (Contacte.contains(c)) {
            Contacte.remove(c);
        } else {
            throw new RuntimeException("Contactul nu exista in lista!");
        }
    }
    
    
    //metoda poate fi suprascrisa astfel incat sa se poata modifica un numar variabil de atribute ale unui contact
    public void ModificaContact(Contact c, String n, String p, String d, String nr){
        
        //verificare nume inainte de modificare
        if(n.length()<2)
            throw new RuntimeException("Nume prea scurt!");
             
        //verificare prenume inainte de modificare
        if(p.length()<2)
            throw new RuntimeException("Prenume prea scurt!");
            
        
        //verificare an nastere inainte de modificare
        //se pot adauga verificari si pentru zi si luna
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
        LocalDate data_nastere = LocalDate.parse(d, formatter);
        if(data_nastere.getYear()>LocalDate.now().getYear()  ||  data_nastere.getYear()< 1920)
            throw new RuntimeException("An invalid!");
        
           
        //trebuie verificat daca noul numar este valid
       if(nr.length()!=10) 
             throw new RuntimeException("Numarul nu are 10 cifre!");
       
      
       if(!(nr.startsWith("07")  ||  nr.startsWith("03")  ||  nr.startsWith("02")))           
               throw new RuntimeException("Numarul nu incepe cu 07, 03 sau 02!");
                
      
     
       //Verificam sa nu existe deja un contact cu datele pe care dorim sa le modificam
       Contact proba = new Contact(n, p, data_nastere, nr);
        if(Contacte.contains(proba))
            throw new RuntimeException("Deja exista un contact cu aceste date!");
        
        
       c.setNume(n);
       c.setPreume(p);
       c.setDataNastere(data_nastere);
       c.setNrTel(nr);
         
    } 
        
 
    
    //metode pentru filtrare
    public void filtreazaNrFix(){
         camp = (Contact x) -> (x.getNrTel().nrTel.startsWith("02")  ||  x.getNrTel().nrTel.startsWith("03"));
    
    }
   
    
    
    public void filtreazaNrMobil() {
        camp = (Contact x) -> x.getNrTel().nrTel.startsWith("07");
    }
    
    
    
    public void filtreazaNascutiAstazi(){
        camp = (Contact x) -> x.getDataNastere().getDayOfYear() == LocalDate.now().getDayOfYear();
    }
    
    
    
    public void filtreazaNascutiLunaCurenta(){
        camp = (Contact x) -> x.getDataNastere().getMonthValue() == LocalDate.now().getMonthValue();
    }
    
    
    
    public void filtrarePersonalizata(String s){
        camp = (Contact x)  ->  (   x.getNume().contains(s) 
                                 || x.getPrenume().contains(s) 
                                 || x.getNrTel().nrTel.contains(s)
                                 || x.getDataNastere().toString().contains(s));      
    }
    
    
    
    //metoda pentru setarea criteriului de ordonare
    public void ordoneaza(CriteriuOrdonare camp){
        campCurent=camp;
    }
    
    
    //metoda pentru ordonarea dupa un anumit criteriu
    public TreeMap contacte(int i){
          
        //pentru i=0, consideram ca nu se aplica criteriu de ordonare
        if (i == 0) {

            l = (List) Contacte.stream().filter(camp).collect(Collectors.toList());
            for(int j = 0; j<l.size(); j++){
                map.put(j, (Contact)l.get(j));
            }
                    
            return map;
            
            
        } else {
            switch (campCurent) {
                case DUPA_NUME:{
                    l = (List) Contacte.stream().sorted(Comparator.comparing(Contact::getNume)).collect(Collectors.toList());
                    for(int j = 0; j<l.size(); j++){
                            map.put(j, (Contact)l.get(j));
                    }
                } break;
                
                case DUPA_PRENUME:{
                    l = (List) Contacte.stream().sorted(Comparator.comparing(Contact::getPrenume)).collect(Collectors.toList());
                    for(int j = 0; j<l.size(); j++){
                            map.put(j, (Contact)l.get(j));
                    }
                } break;
                
                case DUPA_VARSTA:{
                    l = (List) Contacte.stream().sorted(Comparator.comparing(Contact::getDataNastere)).collect(Collectors.toList());
                    for(int j = 0; j<l.size(); j++){
                            map.put(j, (Contact)l.get(j));
                    }    
                } break;
                
                case DUPA_TELEFON:{
                    l = (List) Contacte.stream().sorted(Comparator.comparing(Contact::getNrTel)).collect(Collectors.toList());
                    for(int j = 0; j<l.size(); j++){
                            map.put(j, (Contact)l.get(j));
                    }
                } break;
                
            }
           
            return map;
        }
    }
    
    
    
    public void salveaza(File f){
         try{ //se folosesc streamuri pentru serializare
             FileOutputStream fos = new FileOutputStream(f); 
             ObjectOutputStream oos = new ObjectOutputStream(fos);
             oos.writeObject(Contacte);
             oos.close();
         } catch (FileNotFoundException ex) {
             //Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
             //Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
         }
         
    }
    
    
    
    public void incarca(File f) throws ClassNotFoundException{
        //se folosesc streamuri pentru deserializare
       try{
           FileInputStream fis = new FileInputStream(f);
           ObjectInputStream ois = new ObjectInputStream(fis);
           //Catzel c1 = (Catzel) ois.readObject();
           Agenda a = (Agenda) ois.readObject();
           ois.close();
           fis.close(); 
       } catch (FileNotFoundException ex) {
             //Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
            // Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
   
    
    
}
