/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package calculeDeSimilarite;

import info.debatty.java.stringsimilarity.*;

public class CalculeDeSimilarite  {
    /**
     * Cette méthode calcule la distance de <b>Levenshtein</b> entre deux chaines de caractères.
     * @param texte1 : première chaine de caractère.
     * @param texte2 : seconde chaine de caractère.
     * @return le degré de similarité entre lex deux chaines.
     */
    public double levenshtein( String texte1, String texte2){
        Levenshtein l = new Levenshtein();
        System.out.println("Degré de similarité basé sur la distane de \"Levenshtein\" = "+l.distance(texte1, texte2));
        return l.distance(texte1, texte2);
    }

    /**
     * Cette méthode calcule la distance de <b>Damerau</b> entre deux chaines de caractères.
     * @param texte1 : première chaine de caractère.
     * @param texte2 : seconde chaine de caractère.
     * @return le degré de similarité entre lex deux chaines.
     */
    public double damerau (String texte1, String texte2){
        Damerau d = new Damerau();
        System.out.println("Degré de similarité basé sur la distane de \"Damerau\" = "+d.distance(texte1, texte2));
        return d.distance(texte1, texte2);
    }

    /**
     * Cette méthode calcule la distance de <b>JaroWinkler</b> entre deux chaines de caractères.
     * @param texte1 : première chaine de caractère.
     * @param texte2 : seconde chaine de caractère.
     * @return le degré de similarité entre lex deux chaines.
     */
    public double jaroWinkler(String texte1, String texte2){
        JaroWinkler jw = new JaroWinkler();
        // substitution of s and t
        System.out.println("Degré de similarité basé sur la distane de \"JaroWinkler\" = "+jw.similarity(texte1, texte2));
        return jw.distance(texte1, texte2);
    }

    /**
     * Cette méthode calcule le degré se similarité entre deux chaines de caratères à base de la distance de
     * <b>la plus longue sous-chaine commune</b>.
     * @param texte1 : première chaine de caractère.
     * @param texte2 : seconde chaine de caractère.
     * @return le degré de similarité entre lex deux chaines.
     */
    public double plusLongueSousSequenceCommune(String texte1, String texte2){
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        System.out.println("Degré de similarité basé sur \"la plus longue sous-sequence-commune\" = "+lcs.distance(texte1, texte2));
        return lcs.distance(texte1, texte2);
    }

    /**
     * Cette méthode calcule la distance de <b>N-gramme</b> entre deux chaines de caractères.
     * @param texte1 : première chaine de caractère.
     * @param texte2 : seconde chaine de caractère.
     * @param n : nombre de caractère utilisé pour le <b>N-gramme</b>, p. ex. 2-gramme, 3-gramme, etc.
     * @return le degré de similarité entre lex deux chaines.
     */
    public double ngram(String texte1, String texte2, int n){
        NGram ngram = new NGram(n);
        System.out.println("Degré de similarité basé sur "+n+"-gramme = "+ngram.distance(texte1, texte2));
        return ngram.distance(texte2, texte1);
    }

    /**
     * Cette méthode permet e calculer la similarité entre deux chaines de caractères
     * en choisissant une des méthode permises.
     * @param texte1 : première chaine de caractères.
     * @param texte2 : seconde chaine de caractères.
     * @param methodeDeCalcul : c'est un caractère indiquant la méthode de cacul de similartité, tel que<br>
     *      <b>l</b> : pour Levenshtein <br>
     *      <b>d</b> : pour Damerau <br>
     *      <b>j</b> : pour JaroWinkler <br>
     *      <b>p</b> : pour Plus longue Sous-sequence <br>
     *      <b>n</b> : pour n-gramme <br>
     * @param n : le paramètre <b>n</b> pour la méthode N-gramme, il doit etre >0 et <6.
     * @return
     */
    public double clalculerSimilarite(String texte1, String texte2, char methodeDeCalcul, int n){
        double dgre=0;
        // Conversion en miniscule
        String tmpString = String.valueOf(methodeDeCalcul);
        tmpString = tmpString.toLowerCase();
        char tmpChar = tmpString.charAt(0);

        // n doit etre >0 et <6
        int tmpN = n;
        if (n<0 || tmpN>5)
            tmpN=2;
        
        switch (tmpChar) {
            case 'l':// Levenshtein
                dgre = levenshtein(texte1, texte2);
                break;
            case 'd':// Damerau
                dgre = damerau(texte1, texte2);
                break;
            case 'j':// JaroWinkler
                dgre = jaroWinkler(texte1, texte2);
                break;
            case 'p':// Plus longue Sous-sequence
                dgre = plusLongueSousSequenceCommune(texte1, texte2);
                break;
            case 'n':// N-gramme
                dgre = ngram(texte1, texte2, tmpN);
                break;
            default: // N-gramme par défaut
                dgre = ngram(texte1, texte2, tmpN);
        }
        return dgre;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        CalculeDeSimilarite test = new CalculeDeSimilarite();
        test.clalculerSimilarite("جميله", "جمل", 'p', 5);
    }
}






