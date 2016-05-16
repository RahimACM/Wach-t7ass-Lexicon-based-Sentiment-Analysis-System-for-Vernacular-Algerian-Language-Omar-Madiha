/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lancerTestSQLiteBDD;

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
 * 
 * @author Madiha
 */
public class analyserTextesSQLiteBDD {
    
    //Attributs
    private String cheminDeLaBDD = new String("C:/Users/Madiha/Desktop/PFE/SQLite/Corpus1.db");
    private Connection connexion;
    private Statement etatPost;
    private Statement etatComment;
    private ResultSet resultatPost;
    private ResultSet resultatComment;
    private boolean utiliserStemmer = true;
    private String stemmingMethode = new String("khoja");
    

    private boolean utiliserTraduction = false;
    private String nomDuTraducteur ="ms";
    private boolean utiliserArabization = false;
    boolean testerRacineArabize = false;

    private int vraisPos = 0; // Nbr de comments pos jugés pos
    private int vraisNeg = 0; // Nbr de comments neg jugés neg
    private int fauxPos = 0; // Nbr de comments neg jugés pos
    private int fauxNeg = 0; // Nbr de comments pos jugés neg
    private int scoreErr = 0; // Nbr de comments pos jugés neg ou l'inverse
    private double precision = 0;
    private double rappel = 0;
    private double exactitude = 0;
    private double f_score = 0;
    private double tauxDeSucces = 0;

    public analyserTextesSQLiteBDD(){
        this.cheminDeLaBDD = "jdbc:sqlite:"+cheminDeLaBDD;
        System.out.println("--------------Tester l'analyseur-----------------");
        try {
            // Connexion à la bdd
            connexion = DriverManager.getConnection(this.cheminDeLaBDD);
            if (connexion != null){
                System.out.println("\n--------------Connection Réussit-----------------");

                // Instancier etatPost
                etatPost = connexion.createStatement();

                // Récuperer tout les posts
                resultatPost = etatPost.executeQuery("select * from posts");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public analyserTextesSQLiteBDD(String cheminDeLaBDD, boolean utiliserStemmer, String stemmingMethode ){
        this.cheminDeLaBDD = "jdbc:sqlite:"+cheminDeLaBDD;
        this.utiliserStemmer = utiliserStemmer;
        this.stemmingMethode = stemmingMethode;
        
        System.out.println("--------------Tester l'analyseur-----------------");
        try {
            // Connexion à la bdd
            connexion = DriverManager.getConnection(this.cheminDeLaBDD);
            if (connexion != null){
                System.out.println("\n--------------Connection Réussit-----------------");

                // Instancier etatPost
                etatPost = connexion.createStatement();

                // Récuperer tout les posts
                resultatPost = etatPost.executeQuery("select * from posts");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

     public analyserTextesSQLiteBDD(String cheminDeLaBDD,
                                    boolean utiliserStemmer,
                                    String stemmingMethode,
                                    boolean utiliserTraduction,
                                    String nomDuTraducteur,
                                    boolean utiliserRacinization,
                                    boolean testerRacineArabize ){
        this.cheminDeLaBDD = "jdbc:sqlite:"+cheminDeLaBDD;
        this.utiliserStemmer = utiliserStemmer;
        this.stemmingMethode = stemmingMethode;
        this.utiliserTraduction = utiliserTraduction;
        this.nomDuTraducteur = nomDuTraducteur;
        this.utiliserArabization = utiliserRacinization;
        this.testerRacineArabize = testerRacineArabize;


        System.out.println("--------------Tester l'analyseur-----------------");
        try {
            // Connexion à la bdd
            connexion = DriverManager.getConnection(this.cheminDeLaBDD);
            if (connexion != null){
                System.out.println("\n--------------Connection au Corpus C1 avec succès-----------------");

                // Instancier etatPost
                etatPost = connexion.createStatement();

                // Récuperer tout les posts
                resultatPost = etatPost.executeQuery("select * from posts");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public double calculerPolarite(String texte){
        // Praitraitement
        String separateurs = ",،²&\"'~#|`[@] \n";// "/,:،1234567890²&\"'(-_)=~#|`[^@] \n";
        String fichierDesStopWords="fichiers/stopwrd.txt";
        PreTraitement preTraiteur = new PreTraitement(texte, separateurs, fichierDesStopWords, true, utiliserTraduction, nomDuTraducteur, utiliserArabization, testerRacineArabize);
        preTraiteur.lancerPreTraitement();

        // Traduction et Arabisation

        // Calcule de polarité
        CalculeDePolarite calculateur = new CalculeDePolarite(preTraiteur.getListDeTokenPretraite(),"fichiers/lexique1.txt","fichiers/l2.txt","fichiers/l3.txt");
        return calculateur.calculerPolarite(this.utiliserStemmer, this.stemmingMethode, true, 1.5);
    }

    public void lancerLeTest(boolean utiliserStemmer, String stemmingMethode){
        this.utiliserStemmer = utiliserStemmer;
        this.stemmingMethode = stemmingMethode;
        try{
            while(resultatPost.next()){
                String id_post ;
                id_post = resultatPost.getString("id_post") ;

                // Instancier etatComment
                etatComment = connexion.createStatement();

                // Récuperer tout les comments liés à ce post
                resultatComment = etatComment.executeQuery("select * from comments where id_post = '" + id_post + "'" );
                
                while(resultatComment.next()) {
                    String comment = resultatComment.getString("comment");
                    double taggPolarity = resultatComment.getDouble("polarity");
                    double calculePolarity = calculerPolarite(comment);

                    // Affichage
                    System.out.println("\ncomment :");
                    System.out.println(comment);
                    System.out.println("La polarité taggée du texte est = "+taggPolarity);
                    System.out.println("calculePolarity = "+calculePolarity);
                    if (taggPolarity>0 && calculePolarity>0){
                        vraisPos++;
                    }
                    else if(taggPolarity<0 && calculePolarity<0){
                        vraisNeg++;
                    }
                    else if (taggPolarity<0 && calculePolarity>0){
                        fauxPos++;
                    }
                    else if (taggPolarity>0 && calculePolarity<0){
                        fauxNeg++;
                    }
                    exactitude = (double)(vraisPos + vraisNeg )/(vraisPos + vraisNeg +fauxNeg + fauxPos) ;
                    precision = (double)(vraisPos)/(vraisPos + fauxPos);
                    rappel = (double)(vraisPos)/(vraisPos + fauxNeg);
                    f_score = 2*((double)(precision*rappel)/ (precision+rappel));

                    System.out.println("|---------------------------------------------------------------------------------------| ");
                    System.out.println("|---------------------------------------- Nombre des vrais positifs = "+vraisPos);
                    System.out.println("|---------------------------------------- Nombre des négatifs = "+vraisNeg);
                    System.out.println("|---------------------------------------- Nombre des positifs = "+vraisPos);
                    System.out.println("|---------------------------------------- Nombre des negatifs = "+vraisPos);
                    System.out.println("|---------------------------------------- L'exactitude = "+exactitude);
                    System.out.println("|---------------------------------------- La precision = "+precision);
                    System.out.println("|---------------------------------------- Le rappel = "+rappel);
                    System.out.println("|---------------------------------------------------------------------------------------| ");
                }
                System.out.println("|*******************************************************************************************| ");
                 System.out.println("|---------------------------------------- L'exactitude = "+exactitude);
                 System.out.println("|---------------------------------------- La precision = "+precision);
                 System.out.println("|---------------------------------------- Le rappel = "+rappel);
                 System.out.println("|*******************************************************************************************| ");
                
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        
    }

    public void lancerLeTest(int nbrDeCommentMax){
        int numDuComment=1;
        try{
            while(resultatPost.next() && numDuComment<nbrDeCommentMax){
                String id_post ;
                id_post = resultatPost.getString("id_post") ;

                // Instancier etatComment
                etatComment = connexion.createStatement();

                // Récuperer tout les comments lié à ce post
                resultatComment = etatComment.executeQuery("select * from comments where id_post = '" + id_post + "'" );

                while(resultatComment.next() && numDuComment<=nbrDeCommentMax) {
                    System.out.println("\n------------- "+numDuComment+" ------------------------");
                    System.out.println("id_comment : "+resultatComment.getString("id_comment"));
                    String comment = resultatComment.getString("comment");
                    double taggPolarity = resultatComment.getDouble("polarity");
                    double calculePolarity = calculerPolarite(comment);

                    // Affichage
                    // Affichage
                    System.out.println("\ncomment :");
                    System.out.println(comment);
                    System.out.println("La polarité taggée du texte est = "+taggPolarity);
                    System.out.println("calculePolarity = "+calculePolarity);
                    if (taggPolarity>0 && calculePolarity>0){
                        vraisPos++;
                    }
                    else if(taggPolarity<0 && calculePolarity<0){
                        vraisNeg++;
                    }
                    else if (taggPolarity<0 && calculePolarity>0){
                        fauxPos++;
                    }
                    else if (taggPolarity>0 && calculePolarity<0){
                        fauxNeg++;
                    }
                    exactitude = (double)(vraisPos + vraisNeg )/(vraisPos + vraisNeg +fauxNeg + fauxPos) ;
                    precision = (double)(vraisPos)/(vraisPos + fauxPos);
                    rappel = (double)(vraisPos)/(vraisPos + fauxNeg);
                    f_score = 2*((double)(precision*rappel)/ (precision+rappel));

                    System.out.println("|---------------------------------------------------------------------------------------| ");
                    System.out.println("|---------------------------------------- Nombre des vrais positifs = "+vraisPos);
                    System.out.println("|---------------------------------------- Nombre des vrais négatifs = "+vraisNeg);
                    System.out.println("|---------------------------------------- Nombre des faux positifs = "+fauxPos);
                    System.out.println("|---------------------------------------- Nombre des faux négatifs = "+fauxNeg);
                    System.out.println("|---------------------------------------- L'exactitude = "+exactitude);
                    System.out.println("|---------------------------------------- La precision = "+precision);
                    System.out.println("|---------------------------------------- Le rappel = "+rappel);
                    System.out.println("|---------------------------------------- F-score = "+f_score);
                    System.out.println("|---------------------------------------------------------------------------------------| ");
                    numDuComment++;
                }
                System.out.println("|************************************ Résultat final ***************************************| ");
                System.out.println("|*******************************************************************************************| ");
                System.out.println("|---------------------------------------- L'exactitude = "+exactitude);
                System.out.println("|---------------------------------------- La precision = "+precision);
                System.out.println("|---------------------------------------- Le rappel = "+rappel);
                System.out.println("|---------------------------------------- F-score = "+f_score);
                System.out.println("|*******************************************************************************************| ");
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void main (String[] args){
        analyserTextesSQLiteBDD test = new analyserTextesSQLiteBDD("C:/Users/Madiha/Desktop/PFE/SQLite/Corpus1.db", true, "khoja", true, "ms", true, false);
        test.lancerLeTest(2);
    }
    
}