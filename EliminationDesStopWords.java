/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pretraitement;

import java.lang.*;
import java.util.*;
import java.io.*;
import java.util.regex.*;
import java.util.Scanner;

/**
 * Cette Classe permet de lire un ensemble de stopWords
 * à partir d'un fichier puis de les éliminer du texte
 * stocké dans le vecteur <at>texte</at>
 * @author "OmarMadiha"
 */
public class EliminationDesStopWords {

    // Attributs
    private String fichierDesStopWords;
    @SuppressWarnings("UseOfObsoleteCollectionType")
    private Vector<String> texte;
    private Vector<String> listDesStopWords = new Vector<String>();
    private Vector<String> texteResultat = new Vector<String>();

    /**
     * Constructeur surchargé, il permet de passer deux attributs
     * comme paramètres, les stopWords doivent etre séparés par <br>
     * l'un de ces symboles: <br>[ \n\r\t,.;:?!'\"]+
     * @param fichierDesStopWords: Chemin du fichier txt contenant les stopWords
     * @param texte: Vecteur de chaines de caractères à filtrer
     */
    EliminationDesStopWords(String fichierDesStopWords, Vector<String> texte ){
        this.fichierDesStopWords = fichierDesStopWords;
        this.texte = texte;
        texte = null;
    }

    /**
     * Cette méthode vérifie si <pram>token</pram> appartient à la liste
     * des stopWords stockée dans l'attribut <at>listDesStopWords</at>
     * @param token: le mot à vérifier s'il est stopWord
     * @return "true" si <pram>token</pram> est dans la listes <at>listDesStopWords</at>, "false" sinon
     */
    public Boolean estUnStopWord(String token){
    	int i=0 ;
    	boolean trouv = false;
       	while (!trouv && i<listDesStopWords.size()){
            if( token.equals(listDesStopWords.get(i))) {
    		trouv = true;
        	i=0;
            }
            i++;
	}
	return trouv;
    }

    /**
     * Cette méthode lit les stopWords à partire du fichier dont le chemin <br>
     * est dans l'attribut <param>fichierDesStopWords</param>, les stopWords
     * doivent etre séparés par l'un de ces symboles: <br>[ \n\r\t,.;:?!'\"]+
     * @return Un vecteur des chianes de caractères contenant les stopWords
     */
    public void lireStopWords(){
    	Vector<String> stopWords = new Vector<String>();
        int i=0;
    	try {
            Scanner stopWordsFile = new Scanner(new File(this.fichierDesStopWords));
            stopWordsFile.useDelimiter(Pattern.compile("[ \n\r\t,.;:?!'\"]+"));
            //System.out.println(stopWordsFile.hasNext());
            while(stopWordsFile.hasNext()){
                stopWords.addElement(stopWordsFile.next());
		//System.out.println(stopWords.elementAt(i));
                i++;
            }
            stopWordsFile.close();
	}
	catch (FileNotFoundException e){
            System.err.println(e.getMessage());
            System.exit(-1);
	}
        listDesStopWords = stopWords;
    }

    /**
     * Cette méthode permet de cloner le vecteur <at>texte</at> dans
     * le vecteur <at>texteResultat</at> en supprimant tout stopWord
     * se trouvant dans le vecteur <at>listDesStopWords</at>
     */
    public Vector<String> enleverStopWords(){
        lireStopWords();
        int i=0;
        //System.out.println(texte.size());
	while (i < texte.size()){
            if (estUnStopWord(texte.get(i)))
                System.out.println(texte.get(i) + " est un stopWord.");
            else{// token different de stopWord
                texteResultat.addElement(texte.get(i));
            }
            i++;
        }
        return texteResultat;
    }

    public void setFichierDesStopWords(String fichierDesStopWords) {
        this.fichierDesStopWords = fichierDesStopWords;
    }

    public void setTexte(Vector<String> texte) {
        this.texte = texte;
    }

    public String getFichierDesStopWords() {
        return fichierDesStopWords;
    }

    public Vector<String> getListDesStopWords() {
        return listDesStopWords;
    }

    public Vector<String> getTexte() {
        return texte;
    }

    public Vector<String> getTexteResultat() {
        return texteResultat;
    }

}
	


