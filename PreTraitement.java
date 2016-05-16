/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pretraitement;

import java.util.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import calculeDePolarite.CalculeDePolarite;
import calculeDePolarite.ManipulerLesLexiques;

import traductionEtDetectionDeLangue.MicrosoftTraduction;
import traductionEtDetectionDeLangue.TraduireTexte;
import traductionEtDetectionDeLangue.Arabization;
import calculeDePolarite.ManipulerLesLexiques;


/**
 * Cette classe fait la Tokenization, la Normalization et l'elimination des Stop-words
 * pour un texte en entré
 * @author "OmarMadiha"
 */
public class PreTraitement {

     // Attributs
    // Utilisés en Tokenization
    private String texte;
    private String separateurs ;

    // Utilisés en Normalisation
    private Vector<String> listDeToken = new Vector<String>();
    private Vector<String> listDeTokenNormalise = new Vector<String>();
    private boolean eliminerLesCaractereRepetes = true;

    // Utilisés en Elimination de StopWords
    private String fichierDesStopWords;
    private Vector<String> listDeTokenPretraite = new Vector<String>();

    // Utilisé en traduction
    private boolean utiliserTraduction = false;
    private String nomDuTraducteur ="ms" ;
    private Vector<String> listDeTokenTraduits = new Vector<String>();
    private boolean normalisationApresTraduction = false;

    // Utilisé en arabization
    private boolean utiliserArabization = false;
    private Vector<String> listDeTokenArabizes = new Vector<String>();
    private String L1 = "fichiers/lexique1.txt";
    private String L2 = "fichiers/l2.txt";
    private String L3 = "fichiers/l3.txt";
    private boolean testerRacineArabize = false;
    private boolean normalisationApresArabization = false;
    private String patternDuLatin = "[a-zA-Z_0-9_çèéêëîôœûùüÿàâæï_']+";

    /**
     * Constructeur par défaut,
     * la listes de séparateurs par défaut est "\",+*[]@=\'/ \n\r\t\f"
     * le texte à prétraiter doit etre passer via la méthode "setTexte"
     * @param eliminerLesCaractereRepetes: A true ou à false
     */
    public PreTraitement (boolean eliminerLesCaractereRepetes){
        this.eliminerLesCaractereRepetes = eliminerLesCaractereRepetes;
        this.texte = null;
        separateurs = "\",+*[]@=\'/ \n\r\t\f";
    }

    /**
     * Constructeur surchargé, la listes de séparateurs par défaut est "\",+*[]@=\'/ \n\r\t\f"
     * @param texte: Le texte à pré-traiter
     * @param eliminerLesCaractereRepetes: A true ou à false
     */
    public PreTraitement (String texte, boolean eliminerLesCaractereRepetes){
        this.eliminerLesCaractereRepetes = eliminerLesCaractereRepetes;
        this.texte = texte;
        separateurs = "\",+*[]@=\'/ \n\r\t\f";
    }

    /**
     * Constructeur surchargé
     * @param texte: Le texte à pré-traiter, c'est une chaine de caractères
     * @param separateurs: Listes de séparateurs, c'est une chaine de caractères
     * @param eliminerLesCaractereRepetes: A true ou à false
     */
    public PreTraitement (String texte, String separateurs, boolean eliminerLesCaractereRepetes){
        this.texte = texte;
        this.separateurs = separateurs;
        this.eliminerLesCaractereRepetes = eliminerLesCaractereRepetes;
    }

    /**
     * Constructeur surchargé
     * @param texte: Le texte à pré-traiter, c'est une chaine de caractères
     * @param separateurs: Listes de séparateurs, c'est une chaine de caractères
     * @param fichierDesStopWords: Chemin du fichier txt contenant les stopWords
     * @param eliminerLesCaractereRepetes: A true ou à false
     */
    public PreTraitement (String texte,
                          String separateurs,
                          String fichierDesStopWords,
                          boolean eliminerLesCaractereRepetes ){
        this.texte = texte;
        this.separateurs = separateurs;
        this.fichierDesStopWords = fichierDesStopWords;
        this.eliminerLesCaractereRepetes= eliminerLesCaractereRepetes;
    }

    /**
     * Constructeur surchargé
     * @param texte: Le texte à pré-traiter, c'est une chaine de caractères.
     * @param separateurs: Listes de séparateurs, c'est une chaine de caractères.
     * @param fichierDesStopWords: Chemin du fichier txt contenant les stopWords.
     * @param eliminerLesCaractereRepetes: A true ou à false.
     * @param utiliserTraduction: A true ou à false.
     * @param nomDuTraducteur: par ex:"ms" pour microsoft traduction.
     */
    public PreTraitement (String texte,
                          String separateurs,
                          String fichierDesStopWords,
                          boolean eliminerLesCaractereRepetes,
                          boolean utiliserTraduction,
                          String nomDuTraducteur ){
        this.texte = texte;
        this.separateurs = separateurs;
        this.fichierDesStopWords = fichierDesStopWords;
        this.eliminerLesCaractereRepetes= eliminerLesCaractereRepetes;
        this.utiliserTraduction = utiliserTraduction;
        this.nomDuTraducteur = nomDuTraducteur;
    }

    /**
     * Constructeur surchargé
     * @param texte: Le texte à pré-traiter, c'est une chaine de caractères.
     * @param separateurs: Listes de séparateurs, c'est une chaine de caractères.
     * @param fichierDesStopWords: Chemin du fichier txt contenant les stopWords.
     * @param eliminerLesCaractereRepetes: A true ou à false.
     * @param utiliserTraduction: A true ou à false.
     * @param nomDuTraducteur: par ex:"ms" pour microsoft traduction.
     * @param normalisationApresTraduction: Activer la normalisation apres la traduction.
     */
    public PreTraitement (String texte,
                          String separateurs,
                          String fichierDesStopWords,
                          boolean eliminerLesCaractereRepetes,
                          boolean utiliserTraduction,
                          String nomDuTraducteur,
                          boolean normalisationApresTraduction){
        this.texte = texte;
        this.separateurs = separateurs;
        this.fichierDesStopWords = fichierDesStopWords;
        this.eliminerLesCaractereRepetes= eliminerLesCaractereRepetes;
        this.utiliserTraduction = utiliserTraduction;
        this.nomDuTraducteur = nomDuTraducteur;
        this.normalisationApresTraduction = normalisationApresTraduction;
    }

    /**
     * Constructeur surchargé
     * @param texte: Le texte à pré-traiter, c'est une chaine de caractères.
     * @param separateurs: Listes de séparateurs, c'est une chaine de caractères.
     * @param fichierDesStopWords: Chemin du fichier txt contenant les stopWords.
     * @param eliminerLesCaractereRepetes: A true ou à false.
     * @param utiliserTraduction: pour activer ou désactiver la Traduction.
     * @param nomDuTraducteur: par ex:"ms" pour microsoft traduction.
     * @param utiliserArabization: pour activer ou désactiver l'Arabization.
     * @param testerRacineArabize: Pour activer la vérification de la racine du mot arabizé, si elle existe ds l'un des lexique ou pas.
     */
    public PreTraitement (String texte,
                          String separateurs,
                          String fichierDesStopWords,
                          boolean eliminerLesCaractereRepetes,
                          boolean utiliserTraduction,
                          String nomDuTraducteur,
                          boolean utiliserArabization,
                          boolean testerRacineArabize){
        this.texte = texte;
        this.separateurs = separateurs;
        this.fichierDesStopWords = fichierDesStopWords;
        this.eliminerLesCaractereRepetes= eliminerLesCaractereRepetes;
        this.utiliserTraduction = utiliserTraduction;
        this.nomDuTraducteur = nomDuTraducteur;
        this.utiliserArabization = utiliserArabization;
        this.testerRacineArabize = testerRacineArabize;
    }

    /**
     * Constructeur surchargé
     * @param texte: Le texte à pré-traiter, c'est une chaine de caractères.
     * @param separateurs: Listes de séparateurs, c'est une chaine de caractères.
     * @param fichierDesStopWords: Chemin du fichier txt contenant les stopWords.
     * @param eliminerLesCaractereRepetes: A true ou à false.
     * @param utiliserTraduction: pour activer ou désactiver la Traduction.
     * @param nomDuTraducteur: par ex:"ms" pour microsoft traduction.
     * @param utiliserArabization: pour activer ou désactiver l'Arabization.
     * @param L1: chemin du fichier des motClé,
     * @param L2: chemin du fichier des mot de négation,
     * @param L3: chemin du fichier des intensificateurs.
     */
    public PreTraitement (String texte,
                          String separateurs,
                          String fichierDesStopWords,
                          boolean eliminerLesCaractereRepetes,
                          boolean utiliserTraduction,
                          String nomDuTraducteur,
                          boolean utiliserArabization,
                          String L1,
                          String L2,
                          String L3){
        this.texte = texte;
        this.separateurs = separateurs;
        this.fichierDesStopWords = fichierDesStopWords;
        this.eliminerLesCaractereRepetes= eliminerLesCaractereRepetes;
        this.utiliserTraduction = utiliserTraduction;
        this.nomDuTraducteur = nomDuTraducteur;
        this.utiliserArabization = utiliserArabization;
        this.L1 = L1;
        this.L2 = L2;
        this.L3 = L3;
    }

    /**
     * Constructeur surchargé
     * @param texte: Le texte à pré-traiter, c'est une chaine de caractères.
     * @param separateurs: Listes de séparateurs, c'est une chaine de caractères.
     * @param fichierDesStopWords: Chemin du fichier txt contenant les stopWords.
     * @param eliminerLesCaractereRepetes: A true ou à false.
     * @param utiliserTraduction: pour activer ou désactiver la Traduction.
     * @param nomDuTraducteur: par ex:"ms" pour microsoft traduction.
     * @param utiliserArabization: pour activer ou désactiver l'Arabization.
     * @param L1: chemin du fichier des motClé,
     * @param L2: chemin du fichier des mot de négation,
     * @param L3: chemin du fichier des intensificateurs.
     * @param testerRacineArabize: Pour activer la vérification de la racine du mot arabizé, si elle existe ds l'un des lexique ou pas.
     */
    public PreTraitement (String texte,
                          String separateurs,
                          String fichierDesStopWords,
                          boolean eliminerLesCaractereRepetes,
                          boolean utiliserTraduction,
                          String nomDuTraducteur,
                          boolean utiliserArabization,
                          String L1,
                          String L2,
                          String L3,
                          boolean testerRacineArabize){
        this.texte = texte;
        this.separateurs = separateurs;
        this.fichierDesStopWords = fichierDesStopWords;
        this.eliminerLesCaractereRepetes= eliminerLesCaractereRepetes;
        this.utiliserTraduction = utiliserTraduction;
        this.nomDuTraducteur = nomDuTraducteur;
        this.utiliserArabization = utiliserArabization;
        this.L1 = L1;
        this.L2 = L2;
        this.L3 = L3;
        this.testerRacineArabize = testerRacineArabize;
    }

    /**
     * Constructeur surchargé
     * @param texte: Le texte à pré-traiter, c'est une chaine de caractères.
     * @param separateurs: Listes de séparateurs, c'est une chaine de caractères.
     * @param fichierDesStopWords: Chemin du fichier txt contenant les stopWords.
     * @param eliminerLesCaractereRepetes: A true ou à false.
     * @param utiliserTraduction: pour activer ou désactiver la Traduction.
     * @param nomDuTraducteur: par ex:"ms" pour microsoft traduction.
     * @param utiliserArabization: pour activer ou désactiver l'Arabization.
     * @param L1: chemin du fichier des motClé,
     * @param L2: chemin du fichier des mot de négation,
     * @param L3: chemin du fichier des intensificateurs.
     * @param testerRacineArabize: Pour activer la vérification de la racine du mot arabizé, si elle existe ds l'un des lexique ou pas.
     * @param : normalisationApresArabization: Pour activer la normalisation des résultats de l'arabisation.
     * @param normalisationApresTraduction: Pour activer la normalisation des résultats de la traduction.
     */
    public PreTraitement (String texte,
                          String separateurs,
                          String fichierDesStopWords,
                          boolean eliminerLesCaractereRepetes,
                          boolean utiliserTraduction,
                          String nomDuTraducteur,
                          boolean normalisationApresTraduction,
                          boolean utiliserArabization,
                          String L1,
                          String L2,
                          String L3,
                          boolean testerRacineArabize,
                          boolean normalisationApresArabization){
        this.texte = texte;
        this.separateurs = separateurs;
        this.fichierDesStopWords = fichierDesStopWords;
        this.eliminerLesCaractereRepetes= eliminerLesCaractereRepetes;
        this.utiliserTraduction = utiliserTraduction;
        this.nomDuTraducteur = nomDuTraducteur;
        this.utiliserArabization = utiliserArabization;
        this.L1 = L1;
        this.L2 = L2;
        this.L3 = L3;
        this.testerRacineArabize = testerRacineArabize;
        this.normalisationApresArabization = normalisationApresArabization;
        this.normalisationApresTraduction = normalisationApresTraduction;
    }

    /**
     * Cette méthode permet de subdiviser le texte contenu dans l'attribut <br>
     * <b>texte</b> à base des séparateurs contenus dans l'attribut<b>separateurs</b>,<br>
     * Donc il faut initialiser les attributs:<br>
     * <ul>
     *      <li><b>texte</b></li>
     *      <li><b>separateur</b></li>
     * </ul>
     */
    public void lancerTokenization(){
        System.out.println("-----------------Tokenization---------------------");
        System.out.println("Texte : ------------------------------------------\n "+texte);
        // Tokenization
        Tokenization subdiviseur = new Tokenization(texte, separateurs);
        if (subdiviseur.subdiviser()){
            System.out.println("Résultat : --------------------------------------- ");
            listDeToken = subdiviseur.getListDeToken();
            for (int i=0; i< listDeToken.size(); i++){
                System.out.println(listDeToken.get(i));
            }
        }
        else
            System.out.println("Erreur de Tokenization!");
        // Fin Tokenization
    }

    /**
     * Cette méthode normalise le texte 
     * Il faut lancer la <strong>Tokenization</strong> avant d'appeler cette méthode
     */
    public void lancerNormalization(){
        System.out.println("-----------------Normalisation--------------------");
        // Normalisation
        Normaliseur normaliseur = new Normaliseur(listDeToken);
        listDeTokenNormalise = normaliseur.normaliser(eliminerLesCaractereRepetes);
        for (int i=0; i< listDeToken.size(); i++){
             System.out.println(listDeTokenNormalise.get(i));
        }
        // Fin Normalisation
    }

    /**
     * Cette méthode permet d'enlever les stopWords<br>
     * Il faut lancer la <strong>Normalisation</strong> avant d'appeler cette méthode
     */
    public void lancerEliminationDesStopWords(){
        System.out.println("-----------Elimination des StopWords--------------");
        // Elimination des StopWords
        EliminationDesStopWords  filtreStopWord = new EliminationDesStopWords(fichierDesStopWords, listDeTokenNormalise);
        filtreStopWord.enleverStopWords();
        listDeTokenPretraite = filtreStopWord.getTexteResultat();
        // Fin Elimination des StopWords
        for (int i=0; i< listDeTokenPretraite.size(); i++){
             System.out.println(listDeTokenPretraite.get(i));
        }
    }
    
    /**
     * Cette méthode permet de lancer toute les phases de prétraitement
     * <b>Tokenization</b>, <b>Normalisation</b> et <b>Elimination de stopWords</b>,
     * il faut que les attribus: <b>texte</b>, <b>separateurs</b> et <b>fichierDesStopWords</b>
     * soient initialiser par soi le <b>constructeur</b> ou les <b>setters</b>, le resultat 
     * finale du traitement est dans l'attribut <b>listDeTokenPretraite</b> qui est un vecteur de String.
     */
    public void lancerPreTraitement(){
        System.out.println("----------------Pre-traitement--------------------");
        System.out.println("--------------------------------------------------");

        //----------------------------------------------------------------------
        
        // Tokenization
        System.out.println("-----------------Tokenization---------------------");
        System.out.println("Texte : ------------------------------------------\n "+texte);
        Tokenization subdiviseur = new Tokenization(texte, separateurs);
        if (subdiviseur.subdiviser()){
            System.out.println("Résultat : --------------------------------------- ");
            listDeToken = subdiviseur.getListDeToken();
            for (int i=0; i< listDeToken.size(); i++){
                System.out.println(listDeToken.get(i));
            }
        }
        else
            System.out.println("Erreur de Tokenization!");
        // Fin Tokenization

        //----------------------------------------------------------------------

        // Normalisation
        System.out.println("-----------------Normalisation--------------------");
        Normaliseur normaliseur = new Normaliseur(listDeToken);
        listDeTokenNormalise = normaliseur.normaliser(eliminerLesCaractereRepetes);
        for (int i=0; i< listDeTokenNormalise.size(); i++){
             System.out.println(listDeTokenNormalise.get(i));
        }
        // Fin Normalisation

        //----------------------------------------------------------------------

        // Arabization
        if(utiliserArabization){
            System.out.println("------------------Arabization---------------------");
            Arabization arabizeur = new Arabization(listDeTokenNormalise, L1, L2, L3, this.testerRacineArabize);
            this.listDeTokenArabizes = arabizeur.arabizerTexte();
            // Normalisation
            if (normalisationApresArabization){
                System.out.println("--------Normalisation aprés Arabization---------");
                Normaliseur normaliseur3 = new Normaliseur(listDeTokenArabizes);
                listDeTokenArabizes = normaliseur3.normaliser(eliminerLesCaractereRepetes);
                for (int i=0; i< listDeTokenArabizes.size(); i++){
                     System.out.println(listDeTokenArabizes.get(i));
                }
            }
            // Fin Normalisation
        }
         else{
            this.listDeTokenArabizes = this.listDeTokenNormalise;
         }

        // Fin de l'Arabization
        
        //----------------------------------------------------------------------

        // Traduction
        if(utiliserTraduction){
            System.out.println("------------------Traduction---------------------");
            TraduireTexte traducteur = new TraduireTexte("ms", listDeTokenArabizes, false, separateurs);
            this.listDeTokenTraduits = traducteur.traduire();
            for (int i=0; i< listDeTokenTraduits.size(); i++){
                System.out.println(listDeTokenTraduits.get(i));
            }
            if(normalisationApresTraduction){
                // Normalisation
                System.out.println("--------Normalisation aprés Traduction----------");
                Normaliseur normaliseur2 = new Normaliseur(listDeTokenTraduits);
                listDeTokenTraduits = normaliseur2.normaliser(eliminerLesCaractereRepetes);
                for (int i=0; i< listDeTokenTraduits.size(); i++){
                     System.out.println(listDeTokenTraduits.get(i));
                }
            }
            // Fin Normalisation
        }
         else{
            this.listDeTokenTraduits = this.listDeTokenArabizes;
         }

        // Fin de Traduction

        //----------------------------------------------------------------------

        // Elimination des StopWords
        System.out.println("-----------Elimination des StopWords--------------");
        EliminationDesStopWords  filtreStopWord = new EliminationDesStopWords(fichierDesStopWords, listDeTokenTraduits);
        filtreStopWord.enleverStopWords();
        listDeTokenPretraite = filtreStopWord.getTexteResultat();
        // Fin Elimination des StopWords
        for (int i=0; i< listDeTokenPretraite.size(); i++){
             System.out.println(listDeTokenPretraite.get(i));
        }
    }

   /**
    * Cette méthode permet de parcourir une BDD posts/comments<br>
    * et de récuperer tout les tokens des commentaires, puis les <br>
    * stocker dans la BDD.
    * @param bddUrl: le chemin de la bdd
    * @param fichierDesStopWords: le chemin du fichier contenant
    * @return true si tout va bien, false sinon
    */
    public boolean testerPreTraitementBddSqlite3(String bddUrl, String fichierDesStopWords){
        boolean pasErreur = true;
        try {
            Class.forName("org.sqlite.JDBC");
            String mBddUrl = "jdbc:sqlite:"+bddUrl;
            Connection conn1 = DriverManager.getConnection(mBddUrl);
            if (conn1 != null) {
               System.out.println("Connecté à la bdd avec succes!");
               // Créer un element de requete SQLite
               Statement etat1 = conn1.createStatement();
               // Récuperer les résultats de la requete
               ResultSet resultatRequete = etat1.executeQuery("select * from comments");
               while(resultatRequete.next()) {
                   String id_comment;
                   String token;
                   // Lire les résultats
                   id_comment = resultatRequete.getString("id_comment") ;
                   texte = resultatRequete.getString("comment");
                   // Traiter le commentaire
                   this.fichierDesStopWords = fichierDesStopWords;
                   lancerPreTraitement();
                   Statement etat2 = conn1.createStatement();
                   int i=0;
                   while(i < this.listDeTokenPretraite.size()){
                       token = this.listDeTokenPretraite.get(i);
                   }
               }
               conn1.close();
            }
        } catch (ClassNotFoundException ex) {
            pasErreur = false;
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
            pasErreur = false;
        }
        return pasErreur;
    }

    public boolean cestDuLatin(String mot){
        return mot.matches(patternDuLatin);
    }

    public Vector<String> getListDeToken() {
        return listDeToken;
    }

    public Vector<String> getListDeTokenNormalise() {
        return listDeTokenNormalise;
    }

    public Vector<String> getListDeTokenPretraite() {
        return listDeTokenPretraite;
    }

    public Vector<String> getListDeTokenTraduits() {
        return listDeTokenTraduits;
    }

    public void setListDeTokenTraduits(Vector<String> listDeTokenTraduits) {
        this.listDeTokenTraduits = listDeTokenTraduits;
    }

    public String getNomDuTraducteur() {
        return nomDuTraducteur;
    }

    public void setNomDuTraducteur(String nomDuTraducteur) {
        this.nomDuTraducteur = nomDuTraducteur;
    }

    public boolean isUtiliserTraduction() {
        return utiliserTraduction;
    }

    public void setUtiliserTraduction(boolean utiliserTraduction) {
        this.utiliserTraduction = utiliserTraduction;
    }

    public String getSeparateurs() {
        return separateurs;
    }

    public String getTexte() {
        return texte;
    }

    public boolean isEliminerLesCaractereRepetes() {
        return eliminerLesCaractereRepetes;
    }

    public void setEliminerLesCaractereRepetes(boolean eliminerLesCaractereRepetes) {
        this.eliminerLesCaractereRepetes = eliminerLesCaractereRepetes;
    }

    public String getFichierDesStopWords() {
        return fichierDesStopWords;
    }

    public void setFichierDesStopWords(String fichierDesStopWords) {
        this.fichierDesStopWords = fichierDesStopWords;
    }

    public void setSeparateurs(String separateurs) {
        this.separateurs = separateurs;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

}
