/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analyserDesFichier;

import calculeDeSimilarite.SimilariteOuPolarite;
import calculeDePolarite.CalculeDePolarite;
import calculeDePolarite.ManipulerLesLexiques;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

/**
 * 
 * @author "OmarMadiha"
 */
public class AnalyserFichierTxt {
    // Attributs
    // En entrée
    protected String cheminDuFichier;
    protected boolean utiliserStemmer = true;
    protected String stemmingMethode = new String("khoja");
    protected boolean utiliserTraduction = false;
    protected String nomDuTraducteur ="ms";
    protected boolean utiliserArabization = true;
    protected boolean testerRacineArabize = false;
    protected String cheminDeLaBddDuCorpusC2;
    protected boolean considererCaractèreRépeter=false;

    // En sortie
    protected double polariteTotale;
    protected double subjectiviteTotale;
    int nbrPhrase;
    
    // Internes
    protected SimilariteOuPolarite simOuPol;
    protected Vector<StructurePhrase> phrases = new Vector<StructurePhrase>();

    /**
     * Constructeur par défaut
     */
    AnalyserFichierTxt(){
        
    }

    /**
     * Constructeur par défaut.
     * @param cheminDuFichier
     */
    public AnalyserFichierTxt(String cheminDuFichier){
        this.cheminDuFichier= cheminDuFichier;
        simOuPol = new SimilariteOuPolarite();
    }
    /**
     * Constructeur surchargé.
     * @param cheminDuFichier
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
    public AnalyserFichierTxt(String cheminDuFichier,
                            boolean testerSimilarite,
                            String cheminDeLaBddDuCorpusC2,
                            double seuil,
                            boolean utiliserStemmer,
                            String stemmingMethode,
                            boolean utiliserTraduction,
                            String nomDuTraducteur,
                            boolean utiliserArabization,
                            boolean testerRacineArabize,
                            boolean considererCaractèreRépeter){
        this.cheminDuFichier = cheminDuFichier;
        this.cheminDeLaBddDuCorpusC2 = cheminDeLaBddDuCorpusC2;
        this.utiliserStemmer = utiliserStemmer;
        this.stemmingMethode = stemmingMethode;
        this.utiliserTraduction = utiliserTraduction;
        this.nomDuTraducteur = nomDuTraducteur;
        this.utiliserArabization = utiliserArabization;
        this.testerRacineArabize = testerRacineArabize;
        this.considererCaractèreRépeter = considererCaractèreRépeter;
        this.simOuPol = new SimilariteOuPolarite(testerSimilarite,
                                                cheminDeLaBddDuCorpusC2,
                                                seuil,
                                                utiliserStemmer,
                                                stemmingMethode,
                                                utiliserTraduction,
                                                nomDuTraducteur,
                                                utiliserArabization,
                                                testerRacineArabize,
                                                considererCaractèreRépeter);
    }

    /**
     * Cette méthode permet de lire un fichier de texte dont l'extension est <b>txt</b>.
     * Les phrases dans le texte doivent être séparées par un retour chariot (Entré du clavier).
     */
    public void lireFichierTxt(){
	System.out.println("Lecture du fichier texte:");
        try {
            Scanner fichier = new Scanner(new File(cheminDuFichier));
            String phraseTmp = new String();
            while(fichier.hasNextLine()){
                phraseTmp = fichier.nextLine();
                System.out.println(phraseTmp);
                StructurePhrase structPhrase = new StructurePhrase(phraseTmp, 0, 0);
                phrases.add(structPhrase);
            }
            fichier.close();
	}
        catch (FileNotFoundException e){
            System.err.println(e.getMessage());
            System.exit(-1);
	}
    }

    /**
     * Cette méthode permet d'analyser tout les textes du fichier dont le chemin est passé
     * dans le paramètre <b>cheminDuFichier</b>.
     * @param methodeDeCalculDeSimilarite : la méthode de cacul de similartité, tel que<br>
     *      <b>l</b> : pour Levenshtein <br>
     *      <b>d</b> : pour Damerau <br>
     *      <b>j</b> : pour JaroWinkler <br>
     *      <b>p</b> : pour Plus longue Sous-sequence <br>
     *      <b>n</b> : pour n-gramme <br>
     * @param n : le paramètre <b>n</b> pour la méthode N-gramme, il doit etre >0 et <6.
     */
    public void analyserFichier(char methodeDeCalculDeSimilarite, int n){
        lireFichierTxt();
        int i=0;
        while (i < phrases.size()){
            simOuPol.LancerLeCalcul(phrases.get(i).getPhrase(), methodeDeCalculDeSimilarite, n);
            phrases.get(i).setPolarite(simOuPol.getPolarite());
            phrases.get(i).setSubjectivite(simOuPol.getSubjectivite());
            simOuPol.actualiser();
            i++;
        }
        for (StructurePhrase s: phrases){
            System.out.println(s.toString());
        }
    }

    public String getCheminDeLaBddDuCorpusC2() {
        return cheminDeLaBddDuCorpusC2;
    }

    public void setCheminDeLaBddDuCorpusC2(String cheminDeLaBddDuCorpusC2) {
        this.cheminDeLaBddDuCorpusC2 = cheminDeLaBddDuCorpusC2;
    }

    public String getCheminDuFichier() {
        return cheminDuFichier;
    }

    public void setCheminDuFichier(String cheminDuFichier) {
        this.cheminDuFichier = cheminDuFichier;
    }

    public boolean isConsidererCaractèreRépeter() {
        return considererCaractèreRépeter;
    }

    public void setConsidererCaractèreRépeter(boolean considererCaractèreRépeter) {
        this.considererCaractèreRépeter = considererCaractèreRépeter;
    }

    public int getNbrPhrase() {
        return nbrPhrase;
    }

    public void setNbrPhrase(int nbrPhrase) {
        this.nbrPhrase = nbrPhrase;
    }

    public String getNomDuTraducteur() {
        return nomDuTraducteur;
    }

    public void setNomDuTraducteur(String nomDuTraducteur) {
        this.nomDuTraducteur = nomDuTraducteur;
    }

    public Vector<StructurePhrase> getPhrases() {
        return phrases;
    }

    public void setPhrases(Vector<StructurePhrase> phrases) {
        this.phrases = phrases;
    }

    public double getPolariteTotale() {
        return polariteTotale;
    }

    public void setPolariteTotale(double polariteTotale) {
        this.polariteTotale = polariteTotale;
    }

    public SimilariteOuPolarite getSimOuPol() {
        return simOuPol;
    }

    public void setSimOuPol(SimilariteOuPolarite simOuPol) {
        this.simOuPol = simOuPol;
    }

    public String getStemmingMethode() {
        return stemmingMethode;
    }

    public void setStemmingMethode(String stemmingMethode) {
        this.stemmingMethode = stemmingMethode;
    }

    public double getSubjectiviteTotale() {
        return subjectiviteTotale;
    }

    public void setSubjectiviteTotale(double subjectiviteTotale) {
        this.subjectiviteTotale = subjectiviteTotale;
    }

    public boolean isTesterRacineArabize() {
        return testerRacineArabize;
    }

    public void setTesterRacineArabize(boolean testerRacineArabize) {
        this.testerRacineArabize = testerRacineArabize;
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

    public static void main (String []args){
        AnalyserFichierTxt teste = new AnalyserFichierTxt("C:\\Users\\Madiha\\Desktop\\testeTxt.txt");
        //teste.lireFichierTxt();
        teste.analyserFichier('n', 2);
    }

}
