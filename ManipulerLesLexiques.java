/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package calculeDePolarite;
import calculeDePolarite.CalculeDePolarite;
import java.util.Vector;
import khojaArabicStemmer.KhojaArabicStemmer;

/**
 * 
 * @author "OmarMadiha"
 */
public class ManipulerLesLexiques extends CalculeDePolarite{

    // Attributs
    private Vector<String> lexique1 = new Vector<String>();
    private Vector<String> lexique2 = new Vector<String>();
    private Vector<String> lexique3 = new Vector<String>();
    
    /**
     * Constructeur surchargé, les fichiers <br>
     * doivent etre structurés de cette façon:
     * <file><br>
     *      # <br>
     *      mot polarité<br>
     *      mot polarité<br>
     *      mot polarité<br>
     *      ...
     * </file>
     * @param tokens: le texte dont on veut calculer la polarité
     * @param L1: chemin du fichier des motClé,
     * @param L2: chemin du fichier des mot de négation,
     * @param L3: chemin du fichier des intensificateurs.
     */
    public ManipulerLesLexiques(String L1, String L2, String L3){
        super(L1, L2, L3);
        this.lexique1 = lireLeLexique(this.L1);
        this.lexique2 = lireLeLexique(this.L2);
        this.lexique3 = lireLeLexique(this.L3);
    }
    
    public TokenDsLexique estDansLexique(String mot, boolean testerLaRacine){
        //System.out.println(testerLaRacine);
        int i, j=-1, k=0;
        String lexique = new String();
        TokenDsLexique tDsLexique = new TokenDsLexique();
        KhojaArabicStemmer raciniseur = new KhojaArabicStemmer();
        Vector<String> l = lexique1;
        String racine = new String();
        if(testerLaRacine){
            racine = raciniseur.raciniserToken(mot);
        }
        boolean found = false;
        while(found !=true && k<3){
            i=0;
            while (found != true && i < l.size()){
                //System.out.println("lexique.get(i) = "+l.get(i));
                //System.out.println("mot = "+mot+". racine = "+racine);
                if( mot.equals(l.get(i)) ) {
                    //System.out.println(lexique.get(i)+" existe ds le lexique");
                    found = true;
                    j=i;
                    tDsLexique.setIndexDsLexique(i);
                    if(l==lexique1){
                        lexique = "L1";
                    } else if(l==lexique2){
                        lexique = "L2";
                    } else{
                        lexique = "L3";
                    }
                    tDsLexique.setLexique(lexique);
                    tDsLexique.setToken(mot);
                }
                else if(testerLaRacine && racine.equals(l.get(i))){
                    //System.out.println(racine+" existe ds le lexique");
                    found = true;
                    j=i;
                    tDsLexique.setIndexDsLexique(i);
                    if(l==lexique1){
                        lexique = "L1";
                    } else if(l==lexique2){
                        lexique = "L2";
                    } else{
                        lexique = "L3";
                    }
                    tDsLexique.setLexique(lexique);
                    tDsLexique.setToken(racine);
                }
                else{
                    tDsLexique.setToken(mot);
                }
                i++;
            }
            k++;
            if (k==1)
                l =lexique2;
            else if (k==2)
                l =lexique3;
    	}
        return tDsLexique;
    }

    public Vector<String> getLexique1() {
        return lexique1;
    }

    public void setLexique1(Vector<String> lexique1) {
        this.lexique1 = lexique1;
    }

    public Vector<String> getLexique2() {
        return lexique2;
    }

    public void setLexique2(Vector<String> lexique2) {
        this.lexique2 = lexique2;
    }

    public Vector<String> getLexique3() {
        return lexique3;
    }

    public void setLexique3(Vector<String> lexique3) {
        this.lexique3 = lexique3;
    }

    public static void main(String []args){
        String L = new String();
        String L1 = "fichiers/lexique1.txt";
        String L2 = "fichiers/l2.txt";
        String L3 = "fichiers/l3.txt";
        ManipulerLesLexiques test = new ManipulerLesLexiques(L1, L2, L3);
        TokenDsLexique tds = test.estDansLexique("يرحم", false);
        System.out.println(tds.toString());
    }

}
