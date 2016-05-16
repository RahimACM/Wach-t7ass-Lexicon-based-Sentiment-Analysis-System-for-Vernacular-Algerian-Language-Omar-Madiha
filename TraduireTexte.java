/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package traductionEtDetectionDeLangue;
import traductionEtDetectionDeLangue.MicrosoftTraduction;
import calculeDePolarite.ManipulerLesLexiques;
import java.util.Vector;
import pretraitement.Tokenization;

/**
 * 
 * @author "OmarMadiha"
 */
public class TraduireTexte {

    // Attrubuts
    
    // Utilisé en Tokenization d'un resultat de traduction
    private String separateurs = ",،²&\"'~#|`[@] \n" ;

    // Utilisé en Traduction
    private String nomDuTraducteur;
    private boolean utiliserArabisation = false;
    private Vector<String> listeDeTokens = new Vector<String>();
    private Vector<String> listeDeTokenTraduits = new Vector<String>();
    
    // Chemins des fichiers lexiques
    private String L1 = "fichiers/lexique1.txt";
    private String L2 = "fichiers/l2.txt";
    private String L3 = "fichiers/l3.txt";
    private ManipulerLesLexiques manipulateur ;

    // Traducteurs
    MicrosoftTraduction msTraducteur = new MicrosoftTraduction();

    public TraduireTexte(String nomDuTraducteur, Vector<String> listeDeTokens, boolean utiliserArabisation, String separateurs ){
        this.nomDuTraducteur = nomDuTraducteur;
        this.listeDeTokens = listeDeTokens;
        this.utiliserArabisation = utiliserArabisation;
        this.separateurs = separateurs;
        manipulateur = new ManipulerLesLexiques(L1, L2, L3);
    }

    public TraduireTexte(String nomDuTraducteur, Vector<String> listeDeTokens, String L1, String L2, String L3, boolean utiliserArabisation, String separateurs ){
        this.nomDuTraducteur = nomDuTraducteur;
        this.listeDeTokens = listeDeTokens;
        this.L1 = L1;
        this.L2 = L2;
        this.L3 = L3;
        this.utiliserArabisation = utiliserArabisation;
        this.separateurs = separateurs;
        manipulateur = new ManipulerLesLexiques(L1, L2, L3);
    }

    public Vector<String> traduire(){
        if (nomDuTraducteur=="microsoft" || nomDuTraducteur=="ms" ){
            int ic=0;
            while(ic<listeDeTokens.size()){
                String texteTmp = msTraducteur.traduireMot(listeDeTokens.get(ic), true);

                // Tokenizer le resultat donné par le traducteur
                Tokenization subdiviseur = new Tokenization(texteTmp, separateurs);
                if (subdiviseur.subdiviser() && subdiviseur.getListDeToken().size()>1 ){
                    Vector<String> listDeToken = subdiviseur.getListDeToken();
                    System.out.println("Le mot:\""+listeDeTokens.get(ic)+"\" est traduit en\""+texteTmp+"\" --> Tokenization : ");
                    
                    for (int i=0; i< listDeToken.size(); i++){
                        System.out.println(listDeToken.get(i));
                        listeDeTokenTraduits.add(listDeToken.get(i));
                    }
                    System.out.println("Fin de la Tokenization d'un résultat de traduction ----------------- ");
                }
                else{
                    listeDeTokenTraduits.add(texteTmp);
                }
                ic++;
            }
        }
        return this.listeDeTokenTraduits;
    }

    public String getL1() {
        return L1;
    }

    public void setL1(String L1) {
        this.L1 = L1;
    }

    public String getL2() {
        return L2;
    }

    public void setL2(String L2) {
        this.L2 = L2;
    }

    public String getL3() {
        return L3;
    }

    public void setL3(String L3) {
        this.L3 = L3;
    }

    public Vector<String> getListeDeTokenTraduits() {
        return listeDeTokenTraduits;
    }

    public void setListeDeTokenTraduits(Vector<String> listeDeTokenTraduits) {
        this.listeDeTokenTraduits = listeDeTokenTraduits;
    }

    public Vector<String> getListeDeTokens() {
        return listeDeTokens;
    }

    public void setListeDeTokens(Vector<String> listeDeTokens) {
        this.listeDeTokens = listeDeTokens;
    }

    public ManipulerLesLexiques getManipulateur() {
        return manipulateur;
    }

    public void setManipulateur(ManipulerLesLexiques manipulateur) {
        this.manipulateur = manipulateur;
    }

    public MicrosoftTraduction getMsTraducteur() {
        return msTraducteur;
    }

    public void setMsTraducteur(MicrosoftTraduction msTraducteur) {
        this.msTraducteur = msTraducteur;
    }

    public String getNomDuTraducteur() {
        return nomDuTraducteur;
    }

    public void setNomDuTraducteur(String nomDuTraducteur) {
        this.nomDuTraducteur = nomDuTraducteur;
    }

    public static void main(String args[]){
        Vector<String> vec = new Vector<String>();
        vec.add("bon");
        vec.add("travaille");
        vec.add("حاجة");
        vec.add("فور");
        TraduireTexte test = new TraduireTexte("ms", vec, false, ",،²&\"'~#|`[@] \n");
        System.out.println(test.traduire());
    }

}
