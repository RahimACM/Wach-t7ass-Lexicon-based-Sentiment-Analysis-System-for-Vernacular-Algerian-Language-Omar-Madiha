/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analyserDesFichier;

/**
 * 
 * @author "OmarMadiha"
 */
public class StructurePhrase {

    // Attributs
    private String phrase;
    private double polarite = 0;
    private double subjectivite = 0;

    public StructurePhrase(){
        phrase = new String();
    }

    public StructurePhrase(String phrase, double polarite, double subjectivite){
        this.phrase = phrase;
        this.polarite = polarite;
        this.subjectivite = subjectivite;
    }

    @Override
    public String toString(){
        String tmp = phrase+" *** polarité = "+polarite+" *** subjectivité = "+subjectivite+" **************";
        return tmp;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public double getPolarite() {
        return polarite;
    }

    public void setPolarite(double polarite) {
        this.polarite = polarite;
    }

    public double getSubjectivite() {
        return subjectivite;
    }

    public void setSubjectivite(double subjectivite) {
        this.subjectivite = subjectivite;
    }

}
