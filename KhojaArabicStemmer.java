/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package khojaArabicStemmer;

import java.util.*;
import org.apache.lucene.search.similarities.Normalization;
import pretraitement.Normaliseur;


/**
 * Cette classe permet de raciniser un vecteur de String codé en Arabe <br>
 * passé dans l'attribut <b>tokens</b> et de retourner le résultat dans <br>
 * l'attribut <b>tokensRacine</b> qui est un vecteur de string.<br>
 * la méthode utilisé c'est la méthode de racinisation dite de "khoja".
 * @author "OmarMadiha"
 */
public class KhojaArabicStemmer {
    // Attributs
    private Vector<String> tokens = new Vector<String>();
    private Vector<String> tokensRacine = new Vector<String>();
    private String token = new String();
    private String tokenRacine = new String();

    /**
     * Constructeur par défaut, il initialise l'attribut "token" à null
     */
    public KhojaArabicStemmer(){
        this.token = null;
    }
    
    /**
     * Constructeur surchargé
     * @param token: mot à raciniser
     */
    public KhojaArabicStemmer(String token){
        this.token = token;
    }

    /**
     * Constructeur surchargé
     * @param tokens : vecteur de mot à raciniser
     */
    public KhojaArabicStemmer(Vector<String> tokens){
        this.tokens = tokens;
    }

    /**
     * Calcule la racine de chaque element du vecteur "tokens"
     * @return un vecteur de mots racinisés codés en Arabe
     */
    public Vector<String> raciniserTokens(){
        ArabicStemmer stemmer = new ArabicStemmer();
        int n = tokens.size();
        for (int i=0; i<n; i++){
            String token = tokens.elementAt(i);
            //System.out.println(stemmer.stemWord(token));
            tokensRacine.add(stemmer.stemWord(token));
        }
        return tokensRacine;
    }

    /**
     * Calcule la racine du paramètre passé en entré "token"
     * @param token: le mot à raciniser
     * @return la racine du mot passé en entré
     */
    public String raciniserToken(String token){
        System.out.println("token à raciniser : "+token);
        ArabicStemmer stemmer = new ArabicStemmer();
        Normaliseur normalizeur = new Normaliseur();
        //tokenRacine = stemmer.stemWord(token);
        //System.out.println(stemmer.stemWord(token));
        tokenRacine = stemmer.stemWord(normalizeur.normaliserToken(token, true));
        tokenRacine = normalizeur.normaliserToken(tokenRacine, false);
        
        //tokenRacine = stemmer.stemWord(token);
        System.out.println(token+" |---> "+tokenRacine);
        return tokenRacine;
    }

    public Vector<String> getTokens() {
        return tokens;
    }

    public void setTokens(Vector<String> tokens) {
        this.tokens = tokens;
    }

    public Vector<String> getTokensRacine() {
        return tokensRacine;
    }

    public void setTokensRacine(Vector<String> tokensRacine) {
        this.tokensRacine = tokensRacine;
    }

    public static void main(String[] args) {
        // TODO code application logic here
        Vector<String> vec = new Vector<String>();
        vec.add("عاشق");
        vec.add("دلو");
        vec.add("العشق");

        /*KhojaArabicStemmer stemmer = new KhojaArabicStemmer(vec);
        stemmer.raciniserTokens();*/
        
        KhojaArabicStemmer stemmer = new KhojaArabicStemmer();
        System.out.println(stemmer.raciniserToken(vec.get(0)));

        System.exit(0);

    }
    
}
