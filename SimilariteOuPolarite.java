/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package calculeDeSimilarite;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import pretraitement.EliminationDesStopWords;
import pretraitement.Normaliseur;
import pretraitement.PreTraitement;
import pretraitement.Tokenization;
import calculeDePolarite.CalculeDePolarite;

/**
 * Cette classe permet de passer un texte par un calcul de similarité,
 * le calcul se fait à base des expréssion du corpus C2.
 * Si le résultat est < au seuil donc passer le texte au calcul de polarité et de subjectivité,
 * sinon la polarité du texte est déduite à partir de son expression similaire
 * @author "OmarMadiha"
 */
public class SimilariteOuPolarite {
    // Attributs
    private double polarite = 0;
    private double subjectivite = 0;
    private boolean testerSimilarite = true;
    private double seuil = 0.8;
    private double simMax = 0;
    private String expressionSimilaire;
    private boolean polariteEstCalculeaBaseDeSimilarite = false;
    private boolean considererCaractèreRépeter = true;
    
    private String cheminDeLaBDD = new String("C:/Users/Madiha/Desktop/PFE/SQLite/Corpus2.db");
    private Connection connexion;
    private Statement etatExpression;
    private ResultSet resultatExpression;

    private boolean utiliserStemmer = true;
    private String stemmingMethode = new String("khoja");
    private boolean utiliserTraduction = true;
    private String nomDuTraducteur ="ms";
    private boolean utiliserArabization = true;
    boolean testerRacineArabize = false;
    
    /**
     * Constructeur par défaut, le teste de similarité est activer par défaut,
     * ainsi que le seuil de similarité est fixé à <b>0.9</b>.La <b>Traduction</b>
     * avec <b>"Microsoft translator"</b> est activée et l'<b>Arabization</b> est activée aussi.
     */
    public SimilariteOuPolarite(){
        this.testerSimilarite = true;
        this.cheminDeLaBDD = "jdbc:sqlite:"+cheminDeLaBDD;
        System.out.println("--------------Similarité ou Polarité-----------------");
        try {
            // Connexion à la bdd
            connexion = DriverManager.getConnection(this.cheminDeLaBDD);
            if (connexion != null){
                System.out.println("\n--------------Connection Réussit-----------------");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Constructeur surchargé.
     * @param testerSimilarite
     * @param cheminDeLaBddDuCorpusC2
     * @param seuil
     */
    public SimilariteOuPolarite(boolean testerSimilarite,
                        String cheminDeLaBddDuCorpusC2,
                        double seuil){
        this.testerSimilarite = testerSimilarite;
        this.cheminDeLaBDD = "jdbc:sqlite:"+cheminDeLaBddDuCorpusC2;
        this.seuil = seuil;
        System.out.println("--------------Similarité ou Polarité-----------------");
        try {
            // Connexion à la bdd
            connexion = DriverManager.getConnection(this.cheminDeLaBDD);
            if (connexion != null){
                System.out.println("\n--------------Connection Réussit-----------------");

                // Instancier etatExpression
                etatExpression = connexion.createStatement();

                // Récuperer toutes les expressions
                resultatExpression = etatExpression.executeQuery("select * from expressions");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Constructeur surchargé permettat de paramétrer toutes les options.
     * @param testerSimilarite
     * @param cheminDeLaBddDuCorpusC2
     * @param seuil
     * @param utiliserStemmer
     * @param stemmingMethode
     * @param utiliserTraduction
     * @param nomDuTraducteur
     * @param utiliserArabization
     * @param testerRacineArabize
     * @param considererCaractèreRépeter
     */
    public SimilariteOuPolarite(boolean testerSimilarite,
                        String cheminDeLaBddDuCorpusC2,
                        double seuil,
                        boolean utiliserStemmer,
                        String stemmingMethode,
                        boolean utiliserTraduction,
                        String nomDuTraducteur,
                        boolean utiliserArabization,
                        boolean testerRacineArabize,
                        boolean considererCaractèreRépeter
                        ){
        this.testerSimilarite = testerSimilarite;
        this.cheminDeLaBDD = "jdbc:sqlite:"+cheminDeLaBddDuCorpusC2;
        this.seuil = seuil;
        this.utiliserStemmer = utiliserStemmer;
        this.stemmingMethode = stemmingMethode;
        this.utiliserTraduction = utiliserTraduction;
        this.nomDuTraducteur = nomDuTraducteur;
        this.utiliserArabization = utiliserArabization;
        this.testerRacineArabize = testerRacineArabize;
        this.considererCaractèreRépeter = considererCaractèreRépeter;

        System.out.println("--------------Similarité ou Polarité-----------------");
        try {
            // Connexion à la bdd
            connexion = DriverManager.getConnection(this.cheminDeLaBDD);
            if (connexion != null){
                System.out.println("\n--------------Connection au Corpus C2 succès-----------------");

                // Instancier etatExpression
                etatExpression = connexion.createStatement();

                // Récuperer toutes les expressions
                resultatExpression = etatExpression.executeQuery("select * from expressions");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Cette classe permet de caculer la polarité ainsi que la subjectivite du texte passé en entrée.
     * Les résultats sont stockés directement dans les deux attributs <b>polarie</b> et <b>subjectivite</b>.
     * @param texte : le texte à analyser.
     */
    public void calculerPolarite(String texte){
        // Praitraitement
        String separateurs = ",،²&\"'~#|`[@] \n";// "/,:،1234567890²&\"'(-_)=~#|`[^@] \n";
        String fichierDesStopWords="fichiers/stopwrd.txt";
        PreTraitement preTraiteur = new PreTraitement(texte,
                                        separateurs,
                                        fichierDesStopWords,
                                        true,
                                        utiliserTraduction,
                                        nomDuTraducteur,
                                        utiliserArabization,
                                        testerRacineArabize);
        preTraiteur.lancerPreTraitement();

        // Calcule de polarité
        CalculeDePolarite calculateur = new CalculeDePolarite(preTraiteur.getListDeTokenPretraite(),
                                                              "fichiers/lexique1.txt",
                                                              "fichiers/l2.txt",
                                                              "fichiers/l3.txt");
        polarite = calculateur.calculerPolarite(true,
                                                "khoja",
                                                considererCaractèreRépeter,
                                                1.5);
        subjectivite = calculateur.getSubjectivite();
    }

    /**
     * Cette méthode permet de calculer la polarité du texte passé en entrée,
     * en la récupérant d'une expression similaire ou en la calculant selon
     * une approche basé lexique, le choix dépand du degré de similarité est-il > seuil ou non.
     * La valeur par défaut du seuil est = 0.9 .
     * @param texte : le texte à analyser
     * @param methodeDeCalcul : la méthode de cacul de similartité, tel que<br>
     *      <b>l</b> : pour Levenshtein <br>
     *      <b>d</b> : pour Damerau <br>
     *      <b>j</b> : pour JaroWinkler <br>
     *      <b>p</b> : pour Plus longue Sous-sequence <br>
     *      <b>n</b> : pour n-gramme <br>
     * @param n : le paramètre <b>n</b> pour la méthode N-gramme, il doit etre >0 et <6.
     */
    public void LancerLeCalcul(String texte, char methodeDeCalcul, int n){
        try{
            // Instancier etatExpression
            etatExpression = connexion.createStatement();
            // Récuperer toutes les expressions
            resultatExpression = etatExpression.executeQuery("select * from expressions");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // Tester Similarité
        if (testerSimilarite){
            System.out.println("--------------Calcul de Similarités-----------------");
            CalculeDeSimilarite calculSim = new CalculeDeSimilarite();
            try{
                // Tester tous les éléments du Corpus C2
                while(resultatExpression.next() && simMax<1){
                    String expression ;
                    expression = resultatExpression.getString("expression");
                    double simTmp = calculSim.clalculerSimilarite(expression, texte, methodeDeCalcul, n);
                    if (simTmp > simMax){
                        simMax = simTmp;
                        expressionSimilaire = expression;
                        polarite = resultatExpression.getDouble("polarity");
                    }
                }
                // C'est le calcul de similarité qui donne le jugement de sentiment
                if (simMax>seuil){
                    polariteEstCalculeaBaseDeSimilarite = true;
                    subjectivite = 1;
                    System.out.println("Le calcul de polarité est fait à base des similarités:");
                    System.out.println("L'expression \""+expressionSimilaire+"\" est similaire à "+simMax+" au texte à analyser \""+texte+"\"");
                    System.out.println("La polarité = "+polarite+" *******************");
                    System.out.println("La subjectivité = "+subjectivite+" ***************");
                }
                // Le calcule de polarité se fait à base des lexiques
                else{
                    System.out.println("------------Calcul de Polarité basé lexique---------------");
                    calculerPolarite(texte);
                    System.out.println("Le calcule de polarité est fait à base des lexiques:");
                    System.out.println("La polarité = "+polarite+" *******************");
                    System.out.println("La subjectivité = "+subjectivite+" ***************");
                }
            }catch(SQLException ex) {
                ex.printStackTrace();
            }
        }
        else{ // Calcul de polarité basé lexique
            System.out.println("------------Calcul de Polarité basé lexique---------------");
            calculerPolarite(texte);
            System.out.println("Le calcule de polarité est fait à base des lexiques:");
            System.out.println("La polarité = "+polarite+" *******************");
            System.out.println("La subjectivité = "+subjectivite+" ***************");
        }
    }

    /**
     * Cette méthode permet de remettre au valeurs par défaut tous les attriuts de sortie.
     */
    public void actualiser(){
        this.polarite = 0;
        this.subjectivite = 0;
        this.simMax = 0;
    }
    public String getCheminDeLaBDD() {
        return cheminDeLaBDD;
    }

    public void setCheminDeLaBDD(String cheminDeLaBDD) {
        this.cheminDeLaBDD = cheminDeLaBDD;
    }

    public Connection getConnexion() {
        return connexion;
    }

    public void setConnexion(Connection connexion) {
        this.connexion = connexion;
    }

    public Statement getEtatExpression() {
        return etatExpression;
    }

    public void setEtatExpression(Statement etatExpression) {
        this.etatExpression = etatExpression;
    }

    public String getNomDuTraducteur() {
        return nomDuTraducteur;
    }

    public void setNomDuTraducteur(String nomDuTraducteur) {
        this.nomDuTraducteur = nomDuTraducteur;
    }

    public double getPolarite() {
        return polarite;
    }

    public void setPolarite(double polarite) {
        this.polarite = polarite;
    }

    public ResultSet getResultatExpression() {
        return resultatExpression;
    }

    public void setResultatExpression(ResultSet resultatExpression) {
        this.resultatExpression = resultatExpression;
    }

    public double getSeuil() {
        return seuil;
    }

    public void setSeuil(double seuil) {
        this.seuil = seuil;
    }

    public String getStemmingMethode() {
        return stemmingMethode;
    }

    public void setStemmingMethode(String stemmingMethode) {
        this.stemmingMethode = stemmingMethode;
    }

    public double getSubjectivite() {
        return subjectivite;
    }

    public void setSubjectivite(double subjectivite) {
        this.subjectivite = subjectivite;
    }

    public boolean isTesterRacineArabize() {
        return testerRacineArabize;
    }

    public void setTesterRacineArabize(boolean testerRacineArabize) {
        this.testerRacineArabize = testerRacineArabize;
    }

    public boolean isTesterSimilarite() {
        return testerSimilarite;
    }

    public void setTesterSimilarite(boolean testerSimilarite) {
        this.testerSimilarite = testerSimilarite;
    }

    public boolean isUtiliserArabization() {
        return utiliserArabization;
    }

    public void setUtiliserArabization(boolean utiliserArabization) {
        this.utiliserArabization = utiliserArabization;
    }

    public boolean isUtiliserStemmer() {
        return utiliserStemmer;
    }

    public void setUtiliserStemmer(boolean utiliserStemmer) {
        this.utiliserStemmer = utiliserStemmer;
    }

    public boolean isUtiliserTraduction() {
        return utiliserTraduction;
    }

    public void setUtiliserTraduction(boolean utiliserTraduction) {
        this.utiliserTraduction = utiliserTraduction;
    }

    public double getSimMax() {
        return simMax;
    }

    public void setSimMax(double simMax) {
        this.simMax = simMax;
    }

    public String getExpressionSimilaire() {
        return expressionSimilaire;
    }

    public void setExpressionSimilaire(String expressionSimilaire) {
        this.expressionSimilaire = expressionSimilaire;
    }

    public boolean isConsidererCaractèreRépeter() {
        return considererCaractèreRépeter;
    }

    public void setConsidererCaractèreRépeter(boolean considererCaractèreRépeter) {
        this.considererCaractèreRépeter = considererCaractèreRépeter;
    }

    public boolean isPolariteEstCalculeaBaseDeSimilarite() {
        return polariteEstCalculeaBaseDeSimilarite;
    }

    public void setPolariteEstCalculeaBaseDeSimilarite(boolean polariteEstCalculeaBaseDeSimilarite) {
        this.polariteEstCalculeaBaseDeSimilarite = polariteEstCalculeaBaseDeSimilarite;
    }

    public static void main(String args[]){
        SimilariteOuPolarite test = new SimilariteOuPolarite();
        test.LancerLeCalcul("حتى تولد البقلة",'n',2);
    }
    
}
