/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pretraitement;

import java.util.*;

/**
 * Cette classe subdivise le texte en entré en tokens à base des
 * séparateurs passés comme paramètre
 * @author "OmarMadiha"
 */
public class Tokenization {

    //Attributs
    private StringTokenizer tokenizer;
    private String separateurs;
    private String texte;
    private Vector<String> listDeToken = new Vector<String>();
    
    /**
     * Constructeur surchargé, les séparateurs sont passés sous format String
     * @param texte: Le texte à subdiviser
     * @param separateurs: La chaine de caractères des séparateurs, ex: "/,;\n\t\f"
     */
    public Tokenization(String texte, String separateurs) {
        this.separateurs = separateurs;
        this.texte = texte;
    }

    /**
     * Constructeur surchargé, les séparateurs sont passés dans un fichier (texte ou XML)
     * @param texte: Le texte à subdiviser
     * @param fichierSepareurs: Le chemin du fichier des séparateurs
     * @param typeFichier: Le type du fichier est soit "xml" ou "texte
     */
    public Tokenization(String texte, String fichierSepareurs, String typeFichier){

    }

    /**
     * La méthode Subdivie un texte à base d'une suite de séparateurs
     * Le texte doit etre déjà passé en entré avec la méthode "setTexte" ou via "le constructeur"
     * Les séparateurs doivent aussi etre passés en entré avec la méthode "setSeparateurs" ou via "le constructeur"
     * @return "true" si elle a réussit, "false" sinon
     */
    public boolean subdiviser(){
        if (separateurs!=null && texte!=null){
            tokenizer = new StringTokenizer(texte, separateurs);
            while (tokenizer.hasMoreTokens()){
                listDeToken.addElement(tokenizer.nextToken());
            }
            return true;
        }
        return false;
    }

    public String getSeparateurs() {
        return separateurs;
    }

    public String getTexte() {
        return texte;
    }

    public Vector<String> getListDeToken() {
        return listDeToken;
    }

    public void setSeparateurs(String separateurs) {
        this.separateurs = separateurs;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

}

