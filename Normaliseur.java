package pretraitement;

import java.util.*;
import org.apache.lucene.analysis.ar.*;

/**
 * Cette classe sert à normaliser un texte en entré
 * Exemple de Normalisation: 'أإآء' deviennent 'ا'
 * @author OmarMadiha
 */
public class Normaliseur {

    // Attributs
    private Vector<String> texte;
    private Vector<String> texteResultat = new Vector<String>();
    private String tokenResultat;

    /**
    * Constructeur par defaut
    */
    public Normaliseur (){
        texte = new Vector<String>();
    }

    /**
     * Constructeur surchargé
     * @param texte: Le texte à normalisé
     */
    public Normaliseur (Vector<String> texte){
        this.texte = texte;
    }

    /**
     * La méthode normalise un texte en entré
     * @param texte: Le texte à normaliser est un vecteur de chaines de caractères
     * @return Un vecteur chaines de caractères normalisées
     */
    public Vector<String> normaliser(Vector<String> texte, boolean eliminerLesCaractereRepetes){
        for (int i=0; i< texte.size(); i++){
            texteResultat.addElement(normaliserToken(texte.get(i), eliminerLesCaractereRepetes));
        }
        return texteResultat;
    }

    /**
     * La méthode normalise un texte en entré, Le texte à normaliser doit etre<br>
     * déjà passé en entré avec la méthode "setTexte" ou via "le constructeur"
     * @return Un vecteur de chaines de caractères normalisées
     */
    public Vector<String> normaliser(boolean eliminerLesCaractereRepetes){
        String tmp;
        for (int i=0; i< texte.size(); i++){
            tmp = normaliserToken(texte.get(i), eliminerLesCaractereRepetes);
            // Pour eliminer les chiffres isolés ex: "1 2 3 ..." ou b1 les caractères spéciaux comme: "#=)$ ..."
            if (!estUnCaracterSpecialIsole(tmp))
                texteResultat.addElement(tmp);
        }
        return texteResultat;
    }

    /**
     * La méthode élimine les caractères répétés dans une chiane de caractères passée<br>
     * en paramètre, le résultat est retourné sous forme de <b>String</b>
     * @param token: La chaine de caractères à traiter
     * @return Une chaine de caractères sans caractères répétés
     */
    public String eliminerLesCaractereRepetes(String token){
        return token.replaceAll("(.)\\1{1,}", "$1");
    }

    public boolean estUnCaracterSpecialIsole(String token){
        return token.matches("[^\\\\w]");
    }

    /**
     * La méthode normalise un seul token (suite de caractères sans " "),
     * @param token: le token à normaliser
     * @return Un token normalisé
     */
    public String normaliserToken(String token, boolean eliminerLesCaractereRepetes){
        // Eliminer Les Caractere Repetes
        if(eliminerLesCaractereRepetes){
            token = eliminerLesCaractereRepetes(token);
        }
        // Appeler le normaliseur de lucene
        char [] tab  = new char [token.length()];
        for(int i=0; i<tab.length; i++){
            tab[i] = token.charAt(i);
        }
        ArabicNormalizer  normaliseur = new ArabicNormalizer() ;
        normaliseur.normalize(tab, tab.length);
        // Convertir char[] tab en String
        tokenResultat = String.valueOf(tab);

        return tokenResultat;
    }

    public Vector<String> getTexte() {
        return texte;
    }

    public Vector<String> getTexteResultat() {
        return texteResultat;
    }
    
    public void setTexte(Vector<String> texte) {
        this.texte = texte;
    }

}