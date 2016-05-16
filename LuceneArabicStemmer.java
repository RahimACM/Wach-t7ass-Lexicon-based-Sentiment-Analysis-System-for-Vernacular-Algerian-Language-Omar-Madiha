/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package luceneArabicStemmer;

//import org.apache.lucene.analysis.ar.*;
import java.util.*;
import lucene.util.*;
import lucene.*;

/**
 * Cette classe permet de raciniser un vecteur de String codé en Arabe <br>
 * passé dans l'attribut <b>tokens</b> et de retourner le résultat dans <br>
 * l'attribut <b>tokensRacine</b> qui est un vecteur de string.<br>
 * la méthode utilisé c'est la méthode de racinisation dite "light stemming".
 * @author "OmarMadiha"
 */
public class LuceneArabicStemmer {
    // Attributs
    private Vector<String> tokens = new Vector<String>();
    private Vector<String> tokensRacine = new Vector<String>();
    private String token = new String();
    private String tokenRacine = new String();

    /**
     * Calcule la racine du paramètre passé en entré "token"
     * @param token: le mot à raciniser
     * @return la racine du mot passé en entré
     */
    public String raciniserToken(String token){
        ArabicStemmer stemmer = new ArabicStemmer();
        char [] tmp = token.toCharArray();
        stemmer.stemSuffix(tmp, tmp.length);
        stemmer.stemPrefix(tmp, tmp.length);
        tokenRacine = String.valueOf(tmp);
        System.out.println(tokenRacine);
        return tokenRacine;
    }
    
    public static void main(String[] args){
//        ArabicStemmer stemmer = new ArabicStemmer();
//        String txt = "يأكل";
//        char [] t = txt.toCharArray();
//        stemmer.stemSuffix(t, t.length);
//        stemmer.stemPrefix(t, t.length);
//        System.out.println(t);
        LuceneArabicStemmer tst = new LuceneArabicStemmer();
        tst.raciniserToken("واهيه");
    }

}
