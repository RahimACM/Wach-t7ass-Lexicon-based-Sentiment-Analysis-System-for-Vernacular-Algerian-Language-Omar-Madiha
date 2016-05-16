/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package calculeDePolarite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Vector;
import java.util.Arrays;
import java.util.Scanner;
import khojaArabicStemmer.KhojaArabicStemmer;
import luceneArabicStemmer.LuceneArabicStemmer;

/**
 * Cette classe permet de calculer la
 * polarité du texte passé comme paramètre
 * en entré sous forme d'un vecteur de String.
 * @author "OmarMadiha"
 */
public class CalculeDePolarite {

    // Attribus

    // La variable qui va contenir la polarité finale du commentaire
    protected double TSO = 0;
    // Vecteur de String récupérer à partir du module précedent, il contiens tous les tokens d'un seul commentaire
    public static Vector<String>  tokens = new Vector<String>();
    // Chemin des fichiers lexiques
    protected String L1 = new String();
    protected String L2 = new String();
    protected String L3 = new String();
    protected String L4 = new String();

    protected boolean considererCaracteresRepetes = false;
    // Poid des caractères répétés
    protected double poidDesCaracteresRepetes = 1;
    protected int nbrDeTokenSubjectif = 0;
    protected int nbrDeTokenTotale = 0;
    protected double subjectivite = 0.0;

    /**
     * Constructeur surchargé, les fichiers <br>
     * doivent etre structurés de cette façon:<br>
     * <file>
     *      # <br>
     *      mot polarité<br>
     *      mot polarité<br>
     *      mot polarité<br>
     *      ...
     * </file>
     * @param L1: chemin du fichier des motClé,
     * @param L2: chemin du fichier des mot de négation,
     * @param L3: chemin du fichier des intensificateurs.
     */
    public CalculeDePolarite(String L1, String L2, String L3){
        // Initialiser les variables de calcule de subjecivité
        nbrDeTokenTotale = tokens.size();
        nbrDeTokenSubjectif = nbrDeTokenTotale;

        this.L1 = L1;
        this.L2 = L2;
        this.L3 = L3;
        L4 = "fichiers/l4.txt";
    }

    /**
     * Constructeur surchargé, les fichiers <br>
     * doivent etre structurés de cette façon:<br>
     * <file>
     *      # <br>
     *      mot polarité<br>
     *      mot polarité<br>
     *      mot polarité<br>
     *      ...
     * </file>
     * @param L1: chemin du fichier des motClé,
     * @param L2: chemin du fichier des mot de négation,
     * @param L3: chemin du fichier des intensificateurs.
     * @param L4: chemin du fichier des mots subjectifs.
     */
    public CalculeDePolarite(String L1, String L2, String L3, String L4){
        // Initialiser les variables de calcule de subjecivité
        nbrDeTokenTotale = tokens.size();
        nbrDeTokenSubjectif = nbrDeTokenTotale;

        this.L1 = L1;
        this.L2 = L2;
        this.L3 = L3;
        this.L4 = L4;
    }

    /**
     * Constructeur surchargé, les fichiers <br>
     * doivent etre structurés de cette façon:<br>
     * <file>
     *      # <br>
     *      mot polarité<br>
     *      mot polarité<br>
     *      mot polarité<br>
     *      ...
     * </file>
     * @param tokens: le texte dont on veut calculer la polarité
     * @param L1: chemin du fichier des motClé,
     * @param L2: chemin du fichier des mot de négation,
     * @param L3: chemin du fichier des intensificateurs.
     */
    public CalculeDePolarite(Vector<String> tokens, String L1, String L2, String L3){
        this.tokens = tokens;
        // Initialiser les variables de calcule de subjecivité
        nbrDeTokenTotale = tokens.size();
        nbrDeTokenSubjectif = nbrDeTokenTotale;
        
        this.L1 = L1;
        this.L2 = L2;
        this.L3 = L3;
        L4 = "fichiers/l4.txt";
    }

    /**
     * Constructeur surchargé, les fichiers <br>
     * doivent etre structurés de cette façon:<br>
     * <file>
     *      # <br>
     *      mot polarité<br>
     *      mot polarité<br>
     *      mot polarité<br>
     *      ...
     * </file>
     * @param tokens: le texte dont on veut calculer la polarité
     * @param L1: chemin du fichier des motClé,
     * @param L2: chemin du fichier des mot de négation,
     * @param L3: chemin du fichier des intensificateurs.
     * @param L4: chemin du fichier des mots subjectifs.
     */
    public CalculeDePolarite(Vector<String> tokens, String L1, String L2, String L3, String L4){
        this.tokens = tokens;
        // Initialiser les variables de calcule de subjecivité
        nbrDeTokenTotale = tokens.size();
        nbrDeTokenSubjectif = nbrDeTokenTotale;

        this.L1 = L1;
        this.L2 = L2;
        this.L3 = L3;
        this.L4 = L4;
    }

    /**
     * Méthode pour vérifier l'existance du token dans un des lexique l1,l2 ou l3,
     * l1: lexique des mots clés,
     * l2: lexique des mots de négation,
     * l3: lexique des intensificateurs
     * @param mot: token qu'on veut chercher dans un vecteur
     * @param lexique: vecteur des tokens
     * @return la position du token dans le vecteur s'il existe, -1 sinon
     */
    public int verifierExistanceDuTokenDsLexique(String mot, Vector<String> lexique){
	int i = 0, j=-1;
        boolean found = false;
       	while (found !=true && i < lexique.size()){
            //System.out.println("lexique.get(i) = "+lexique.get(i));
            //System.out.println("mot = "+mot);
            if( mot.equals(lexique.get(i))) {
                //System.out.println(lexique.get(i)+" existe ds le lexique");
                found = true;
    		j=i;
    		i=0;
            }
            i++;
   	}
    	return j;
    }

    /**
     * Méthode pour lire le fichier contenant le lexique,
     * soi l1 ,l2 ou l3, puis retourner le contenu dans un un vecteur de string.
     * @param cheminDuFichierLexique
     * @return
     */
    public static Vector<String> lireLeLexique(String cheminDuFichierLexique){
	Vector<String> lexique = new Vector<String>();
        try {
            Scanner lexiqueFile = new Scanner(new File(cheminDuFichierLexique));
            //System.out.println("Lecture du lexique: \""+cheminDuFichierLexique+"\" -----------------");
            while(lexiqueFile.hasNext()){
                String tmp = lexiqueFile.next();
                //System.out.println(tmp);
               	lexique.add(tmp);
            }
            //System.out.println("Fin de la lecture du lexique: \""+cheminDuFichierLexique+"\" -----------------");
            lexiqueFile.close();
	}
        catch (FileNotFoundException e){
            System.err.println(e.getMessage());
            System.exit(-1);
	}
	return lexique;
    }

     /**
     * Récuperer la polarité d'un token existant
     * déjà dans l'un des lexiques l1, l2 ou l3
     * @param mot: le token à chercher sa polarité
     * @param lexique
     * @return la polarité du token s'il appartient au lexique passés en paramètres, sinon 0
     */
    public double RecupererLaPolarite(String mot, Vector<String> lexique){
    	double polarite = 0;
	int temp = verifierExistanceDuTokenDsLexique(mot, lexique);
	if(temp!=-1 && temp <lexique.size()-1){ // La polarité d'un token c'est son successeur dans le fichier
            temp = temp + 1;
            polarite = Double.parseDouble(lexique.get(temp));
        }
	return polarite;
    }

    /**
     * Vérifier le predecesseur de chaque token, afin de décider qu'il
     * est précedé par un mot de l1 ou de l2
     * @param mot: le token dont on cherche le predecesseur
     * @return le predecesseur du token mot
     */
    public String recupererPredecesseur(String mot){
        String[]  token = new String[this.tokens.size()] ;
        for(int i = 0; i <this.tokens.size(); i++){
            token[i] = tokens.get(i);
        }
	int indice=-1, indicePre=-1;
	String pred="";
        indice = Arrays.asList(token).indexOf(mot);
	indicePre = indice-1;
	if(indicePre>=0)
            pred = token[indicePre];
	return pred;
    }

     /**
     * Vérifier le successeur de chaque token afin de confirmer qu'il suuceder par un mot de  l2
     * @param mot: le token dont on cherche le successeur
     * @return le successeur du token mot
     */
    public String recupererSuccesseur(String mot){
        String[]  token = new String[this.tokens.size()] ;
        for(int i = 0; i <this.tokens.size(); i++){
            token[i] = tokens.get(i);
        }
        int indice=-1,indiceSucc=-1;
	String succ="";
	indice = Arrays.asList(token).indexOf(mot);
	// Pas de successeur pour le dernier token
        if(indice < (token.length - 1) && indice>=0){
            indiceSucc = indice+1;
            succ = token[indiceSucc];
        }
	return succ;
    }

    public String raciniserToken(String token, String methodeDeRacinisation){
        String tmp = new String();
        if (methodeDeRacinisation.toLowerCase() == "khoja"){
            KhojaArabicStemmer stemmer = new khojaArabicStemmer.KhojaArabicStemmer();
            tmp = stemmer.raciniserToken(token);
            //System.out.println(tmp+"= racine de "+token);
        }
        else if (methodeDeRacinisation.toLowerCase() == "lucene"){
            LuceneArabicStemmer stemmer = new LuceneArabicStemmer();
            tmp = stemmer.raciniserToken(token);
            //System.out.println(tmp+" = racine de"+token);
        }
        return tmp;
    }

    /**
     * 
     * @param utiliserRacinisation si vous voulez utiliser un stemmer <br> ce paramètre doit etre égale <b>true</b>, sion initialiser le par <b>false</b><br>
     * @param methodeDeRacinisation nom de la méthode de stemming, ex: <b>KHOJA</b> ou <b>LUCENE</b> si <b>utiliserRacinisation</b> est à <b>true</b>, sinon mettez peut importe et la méthode fonctionne
     * @return L'orientation sentimentale totale du texte.
     */
    public double calculerPolarite(boolean utiliserRacinisation, String methodeDeRacinisation) {
        System.out.println("--------------Calcule de polarité-----------------");
        if(utiliserRacinisation){
            System.out.println("-------Racinisation avec  \""+methodeDeRacinisation+"\" stemmer!--------");
        }
        if(tokens.size()>0){
            //p1:polarité du mot clé ,
            //p2:poids du mot de négation ,
            //p3: poids de l'intensificateur
            //pCR: poid des caractères répétés
            double p1=0, p2=0, p3=0, pCR=0;
            int ic=0;
            String succ, pred;//successeur et prédecesseur du mot clé
            // lire les differents lexiques
            Vector<String> lexique1 = lireLeLexique(this.L1);
            Vector<String> lexique2 = lireLeLexique(this.L2);
            Vector<String> lexique3 = lireLeLexique(this.L3);

            while ( ic < tokens.size()){
                String tokenCourant = tokens.get(ic);
                //System.out.println("tokenCourant = "+tokenCourant);

                // vérifier l'existance du tokenCourant dans le lexique l1
                //System.out.println("Existance du tokenCourant : "+tokenCourant+" ds l1 = "+verifierExistanceDuTokenDsLexique(tokenCourant, lexique1));
                if(verifierExistanceDuTokenDsLexique(tokenCourant, lexique1)!=-1){
                    p1 = RecupererLaPolarite(tokenCourant, lexique1);
                    pred = recupererPredecesseur(tokenCourant);
                    succ = recupererSuccesseur(tokenCourant);

                    //System.out.println(pred);
                    //System.out.println(succ);

                    // "pred" n'est pas vide et il est ds l2 ou l3
                    if(pred!="" && (verifierExistanceDuTokenDsLexique(pred, lexique2)!=-1 || verifierExistanceDuTokenDsLexique(pred, lexique3)!=-1)){
                        // vérifier l'existance du "pred" dans le lexique l2
                        //System.out.println("Existance du token : "+pred+" ds l2 = "+verifierExistanceDuTokenDsLexique(pred, lexique2));
                        // "pred" est ds l2
                        if(verifierExistanceDuTokenDsLexique(pred, lexique2)!=-1){
                            p2 = RecupererLaPolarite(pred, lexique2);
                            // "succ" n'est pas vide et il est ds l3
                            if(succ!="" && (verifierExistanceDuTokenDsLexique(succ, lexique3)!=-1)){
                                p3 = RecupererLaPolarite(pred, lexique3);
                                TSO = TSO + p2*p1 + p1*p3;
                                ic+=2;
                            }
                            // le "succ" ne rentre pas ds le calcule de la polarité du "tokenCourant"
                            else{
                                TSO = TSO + p2*p1;
                                ic++;
                            }
                        }
                        // vérifier l'existance du "pred" dans le lexique l3
                        else if(verifierExistanceDuTokenDsLexique(pred, lexique3)!=-1){
                            //System.out.println("Existance du token : "+pred+" ds l3 = "+verifierExistanceDuTokenDsLexique(pred, lexique3));
                            p3 = RecupererLaPolarite(pred, lexique3);
                            TSO = TSO + p3*p1;
                            ic++;
                        }
                    }
                    // "succ" n'est pas vide et il est ds l3
                    else if(succ!="" && verifierExistanceDuTokenDsLexique(succ, lexique3)!=-1){
                        // vérifier l'existance du "succ" dans le lexique l3
                        //System.out.println("Existance du token : "+succ+" ds l3 = "+verifierExistanceDuTokenDsLexique(succ, lexique3));
                        if(verifierExistanceDuTokenDsLexique(succ, lexique3)!=-1){
                            p3 = RecupererLaPolarite(succ, lexique3);
                            TSO = TSO + p3*p1;
                            ic+=2;
                        }
                   }
                    // "succ" et "pred" ne rentrent pas ds le calcule de la polarité du "tokenCourant"
                    else {
                        TSO = TSO + p1;
                        ic++;
                    }

                }
                //------------------------------------------------------------------------------------------------------------
                // Essayer de chercher la racine du "tokenCourant" ds l1, si la racinisation est activée
                //------------------------------------------------------------------------------------------------------------
                else if (utiliserRacinisation &&
                         verifierExistanceDuTokenDsLexique(tokenCourant, lexique2)==-1 &&
                         verifierExistanceDuTokenDsLexique(tokenCourant, lexique3)==-1 &&
                         verifierExistanceDuTokenDsLexique(raciniserToken(tokenCourant, methodeDeRacinisation), lexique1)!=-1){
                    String racineTokenCourant = raciniserToken(tokenCourant, methodeDeRacinisation);
                    //System.out.println("La racine du token courant est: "+racineTokenCourant);
                    p1 = RecupererLaPolarite(racineTokenCourant, lexique1);
                    pred = recupererPredecesseur(tokenCourant);
                    succ = recupererSuccesseur(tokenCourant);

                    //System.out.println(pred);
                    //System.out.println(succ);

                    // "pred" n'est pas vide et il est ds l2 ou l3
                    if(pred!="" && (verifierExistanceDuTokenDsLexique(pred, lexique2)!=-1 || verifierExistanceDuTokenDsLexique(pred, lexique3)!=-1)){
                        // vérifier l'existance du "pred" dans le lexique l2
                        //System.out.println("Existance du token : "+pred+" ds l2 = "+verifierExistanceDuTokenDsLexique(pred, lexique2));
                        // "pred" est ds l2
                        if(verifierExistanceDuTokenDsLexique(pred, lexique2)!=-1){
                            p2 = RecupererLaPolarite(pred, lexique2);
                            // "succ" n'est pas vide et il est ds l3
                            if(succ!="" && (verifierExistanceDuTokenDsLexique(succ, lexique3)!=-1)){
                                p3 = RecupererLaPolarite(pred, lexique3);
                                TSO = TSO + p2*p1 + p1*p3;
                                ic+=2;
                            }
                            // le "succ" ne rentre pas ds le calcule de la polarité du "tokenCourant"
                            else{
                                TSO = TSO + p2*p1;
                                ic++;
                            }
                        }
                        // vérifier l'existance du "pred" dans le lexique l3
                        else if(verifierExistanceDuTokenDsLexique(pred, lexique3)!=-1){
                            //System.out.println("Existance du token : "+pred+" ds l3 = "+verifierExistanceDuTokenDsLexique(pred, lexique3));
                            p3 = RecupererLaPolarite(pred, lexique3);
                            TSO = TSO + p3*p1;
                            ic++;
                        }
                    }
                    // "succ" n'est pas vide et il est ds l3
                    else if(succ!="" && verifierExistanceDuTokenDsLexique(succ, lexique3)!=-1){
                        // vérifier l'existance du "succ" dans le lexique l3
                        //System.out.println("Existance du token : "+succ+" ds l3 = "+verifierExistanceDuTokenDsLexique(succ, lexique3));
                        if(verifierExistanceDuTokenDsLexique(succ, lexique3)!=-1){
                            p3 = RecupererLaPolarite(succ, lexique3);
                            TSO = TSO + p3*p1;
                            ic+=2;
                        }
                   }
                    // "succ" et "pred" ne rentrent pas ds le calcule de la polarité du "tokenCourant"
                    else {
                        TSO = TSO + p1;
                        ic++;
                    }
                }
                else{
                    nbrDeTokenSubjectif--;
                    //System.out.println(nbrDeTokenSubjectif);
                    ic++;
                }
            }
            //System.out.println("------------------------------------------");
            System.out.println("La polarité totale du texte est = "+TSO);
            System.out.println("La subjectivité estimée est = "+calculerSubjectivite());
            //System.out.println(nbrDeTokenSubjectif);
            System.out.println("--------------------------------------------------");
        }
        else{
            System.out.println("L'attribut \"tokens\" est vide, utiliser la méthode setTokens() pour l'initialiser!");
        }
        return this.TSO;
    }

     
    /**
     * La méthode calcule la polarité du texte passé ds l'attributs <b>tokens</b>, elle donne plus de paramètres à modifier.
     * @param utiliserRacinisation: Si vous voulez utiliser un stemmer <br> ce paramètre doit etre égale <b>true</b>, sion initialiser le par <b>false</b>.
     * @param methodeDeRacinisation: Nom de la méthode de stemming, ex: <b>KHOJA</b> ou <b>LUCENE</b> si <b>utiliserRacinisation</b> est à <b>true</b>, sinon mettez peut importe et la méthode fonctionne.
     * @param considererCaracteresRepetes: Si vous voulez prendre en cosidération qu'un <br>mot cotenant des caractères répétés est plus pondéré; ce paramètre doit etre égale <b>true</b>, sion initialiser le par <b>false</b>.<br>ex: "روووعة" aura comme poids +1*poidDesCaracteresRepetes si ce paramètre est à <b>true</b> et non +1.
     * @param poidDesCaracteresRepetes: Par défaut il est égale à +1.5.
     * @return L'orientation sentimentale totale du texte.
     */
    public double calculerPolarite(boolean utiliserRacinisation,
                                   String methodeDeRacinisation,
                                   boolean considererCaracteresRepetes,
                                   double poidDesCaracteresRepetes) {
        this.considererCaracteresRepetes = considererCaracteresRepetes;
        
        System.out.println("--------------Calcule de polarité-----------------");
        if(utiliserRacinisation){
            System.out.println("-------Racinisation avec  \""+methodeDeRacinisation+"\" stemmer!--------");
        }
        if(tokens.size()>0){
            //p1:polarité du mot clé ,
            //p2:poids du mot de négation ,
            //p3: poids de l'intensificateur
            double p1=0, p2=0, p3=0;
            int ic=0;
            String succ, pred;//successeur et prédecesseur du mot clé
            // lire les differents lexiques
            Vector<String> lexique1 = lireLeLexique(this.L1);
            Vector<String> lexique2 = lireLeLexique(this.L2);
            Vector<String> lexique3 = lireLeLexique(this.L3);
            Vector<String> lexique4 = lireLeLexique(this.L4);

            while ( ic < tokens.size()){
                String tokenCourant = tokens.get(ic);
                System.out.println("tokenCourant = "+tokenCourant);

                // vérifier l'existance du tokenCourant dans le lexique l1
                //System.out.println("Existance du tokenCourant : "+tokenCourant+" ds l1 = "+verifierExistanceDuTokenDsLexique(tokenCourant, lexique1));
                if(verifierExistanceDuTokenDsLexique(eliminerLesCaractereRepetes(tokenCourant, considererCaracteresRepetes), lexique1)!=-1){
                    // Traitement des caractères répétés pour "tokenCourant"
                    if (considererCaracteresRepetes && contientDesCaractereRepete(tokenCourant)){
                        this.poidDesCaracteresRepetes = poidDesCaracteresRepetes;
                        tokenCourant = eliminerLesCaractereRepetes(tokenCourant, considererCaracteresRepetes);
                        //System.out.println("tokenCourant = "+tokenCourant);
                    }
                    p1 = RecupererLaPolarite(tokenCourant, lexique1)*this.poidDesCaracteresRepetes;
                    this.poidDesCaracteresRepetes = 1;

                    System.out.println("tokenCourant = "+tokenCourant+" est ds L1 polarité= "+p1+" ***************");
                    //pred = recupererPredecesseur(tokenCourant);
                    //succ = recupererSuccesseur(tokenCourant);

                    pred = predecesseurDuVecteur(ic);
                    succ = successeurDuVecteur(ic);
                    

                    //System.out.println("tokenCourant= "+tokenCourant+". pred= "+pred+". succ= "+succ);
                   
                    // Traitement des caractères répétés pour le "pred"
                    if (considererCaracteresRepetes && contientDesCaractereRepete(pred)){
                        this.poidDesCaracteresRepetes = poidDesCaracteresRepetes;
                        pred = eliminerLesCaractereRepetes(pred, considererCaracteresRepetes);
                    }
                    // "pred" n'est pas vide et il est ds l2 ou l3
                    if(pred!="" && (verifierExistanceDuTokenDsLexique(pred, lexique2)!=-1 || verifierExistanceDuTokenDsLexique(pred, lexique3)!=-1)){
                        // vérifier l'existance du "pred" dans le lexique l2
                        //System.out.println("Existance du token : "+pred+" ds l2 = "+verifierExistanceDuTokenDsLexique(pred, lexique2));
                        // "pred" est ds l2
                        if(verifierExistanceDuTokenDsLexique(pred, lexique2)!=-1){
                            p2 = (double)RecupererLaPolarite(pred, lexique2)*this.poidDesCaracteresRepetes;
                            System.out.println("token = "+pred+" est ds L2 polarité= "+p2+" ***************");
                            this.poidDesCaracteresRepetes = 1;

                            // Traitement des caractères répétés pour le "succ"
                            if (considererCaracteresRepetes && contientDesCaractereRepete(succ)){
                                this.poidDesCaracteresRepetes = poidDesCaracteresRepetes;
                                succ = eliminerLesCaractereRepetes(succ, considererCaracteresRepetes);
                            }
                            // "succ" n'est pas vide et il est ds l3
                            if(succ!="" && (verifierExistanceDuTokenDsLexique(succ, lexique3)!=-1)){
                                p3 = RecupererLaPolarite(succ, lexique3)*this.poidDesCaracteresRepetes;
                                System.out.println("token = "+succ+" est ds L3 polarité= "+p3+" ***************");
                                this.poidDesCaracteresRepetes = 1;

                                TSO = TSO + p2*p1 + p1*p3;
                                ic+=2;
                            }
                            // le "succ" ne rentre pas ds le calcule de la polarité du "tokenCourant"
                            else{
                                TSO = TSO + p2*p1;
                                ic++;
                            }
                        }
                        // vérifier l'existance du "pred" dans le lexique l3
                        else if(verifierExistanceDuTokenDsLexique(pred, lexique3)!=-1){
                            //System.out.println("Existance du token : "+pred+" ds l3 = "+verifierExistanceDuTokenDsLexique(pred, lexique3));
                            p3 = RecupererLaPolarite(pred, lexique3)*this.poidDesCaracteresRepetes;
                            System.out.println("token = "+succ+" est ds L3 polarité= "+p3+" ***************");
                            //System.out.println("p3="+p3+" p1="+p1+" TSO="+TSO);
                            this.poidDesCaracteresRepetes = 1;
                            TSO = TSO + p3*p1;
                            ic++;
                        }
                    }
                    // "succ" (ou bien le "succ" sans caractères répétés) n'est pas vide et il est ds l3
                    else if(succ!="" && (verifierExistanceDuTokenDsLexique(succ, lexique3)!=-1 || verifierExistanceDuTokenDsLexique(eliminerLesCaractereRepetes(succ, true), lexique3)!=-1)){
                        // Traitement des caractères répétés pour le "succ"
                        if (considererCaracteresRepetes && contientDesCaractereRepete(succ)){
                            this.poidDesCaracteresRepetes = poidDesCaracteresRepetes;
                            succ = eliminerLesCaractereRepetes(succ, considererCaracteresRepetes);
                        }
                        //System.out.println("Existance du token : "+succ+" ds l3 = "+verifierExistanceDuTokenDsLexique(succ, lexique3));
                        if(verifierExistanceDuTokenDsLexique(succ, lexique3)!=-1){
                            p3 = RecupererLaPolarite(succ, lexique3)*this.poidDesCaracteresRepetes;
                            System.out.println("token = "+succ+" est ds L3 polarité= "+p3+" ***************");
                            this.poidDesCaracteresRepetes = 1;
                            TSO = TSO + p3*p1;
                            ic+=2;
                        }
                   }
                    // "succ" et "pred" ne rentrent pas ds le calcule de la polarité du "tokenCourant"
                    else {
                        TSO = TSO + p1;
                        ic++;
                    }

                }
                //------------------------------------------------------------------------------------------------------------
                // Essayer de chercher la racine du "tokenCourant" ds l1, si la racinisation est activée et il n'est ni ds l2 ni ds l3
                //------------------------------------------------------------------------------------------------------------
                else if (utiliserRacinisation &&
                         verifierExistanceDuTokenDsLexique(eliminerLesCaractereRepetes(tokenCourant, considererCaracteresRepetes), lexique2)==-1 &&
                         verifierExistanceDuTokenDsLexique(eliminerLesCaractereRepetes(tokenCourant, considererCaracteresRepetes), lexique3)==-1 &&
                         verifierExistanceDuTokenDsLexique(raciniserToken(eliminerLesCaractereRepetes(tokenCourant, considererCaracteresRepetes), methodeDeRacinisation), lexique1)!=-1){

                    // Traitement des caractères répétés pour "tokenCourant"
                    if (considererCaracteresRepetes && contientDesCaractereRepete(tokenCourant)){
                        this.poidDesCaracteresRepetes = poidDesCaracteresRepetes;
                        tokenCourant = eliminerLesCaractereRepetes(tokenCourant, considererCaracteresRepetes);
                        //System.out.println("tokenCourant = "+tokenCourant);
                    }
                    String racineTokenCourant = raciniserToken(tokenCourant, methodeDeRacinisation);
                    //System.out.println("La racine du token courant est: "+racineTokenCourant);
                    p1 = RecupererLaPolarite(racineTokenCourant, lexique1)*this.poidDesCaracteresRepetes;
                    System.out.println("token = "+racineTokenCourant+" est ds L1 polarité= "+p1+" ***************");
                    this.poidDesCaracteresRepetes = 1;
                    
                    //pred = recupererPredecesseur(tokenCourant);
                    //succ = recupererSuccesseur(tokenCourant);

                    pred = predecesseurDuVecteur(ic);
                    succ = successeurDuVecteur(ic);

                    // Traitement des caractères répétés pour le "pred"
                    if (considererCaracteresRepetes && contientDesCaractereRepete(pred)){
                        this.poidDesCaracteresRepetes = poidDesCaracteresRepetes;
                        pred = eliminerLesCaractereRepetes(pred, considererCaracteresRepetes);
                    }
                    
                    //System.out.println(pred);
                    //System.out.println(succ);
                    
                    // "pred" n'est pas vide et il est ds l2 ou l3
                    if(pred!="" && (verifierExistanceDuTokenDsLexique(pred, lexique2)!=-1 || verifierExistanceDuTokenDsLexique(pred, lexique3)!=-1)){
                        // vérifier l'existance du "pred" dans le lexique l2
                        //System.out.println("Existance du token : "+pred+" ds l2 = "+verifierExistanceDuTokenDsLexique(pred, lexique2));
                        // "pred" est ds l2
                        if(verifierExistanceDuTokenDsLexique(pred, lexique2)!=-1){
                            p2 = RecupererLaPolarite(pred, lexique2)*this.poidDesCaracteresRepetes;
                            System.out.println("token = "+pred+" est ds L2 polarité= "+p2+" ***************");
                            this.poidDesCaracteresRepetes = 1;

                            // Traitement des caractères répétés pour le "succ"
                            if (considererCaracteresRepetes && contientDesCaractereRepete(succ)){
                                this.poidDesCaracteresRepetes = poidDesCaracteresRepetes;
                                succ = eliminerLesCaractereRepetes(succ, considererCaracteresRepetes);
                            }
                            // "succ" n'est pas vide et il est ds l3
                            if(succ!="" && (verifierExistanceDuTokenDsLexique(succ, lexique3)!=-1)){
                                p3 = RecupererLaPolarite(pred, lexique3)*this.poidDesCaracteresRepetes;
                                System.out.println("token = "+succ+" est ds L3 polarité= "+p3+" ***************");
                                this.poidDesCaracteresRepetes = 1;
                                TSO = TSO + p2*p1 + p1*p3;
                                ic+=2;
                            }
                            // le "succ" ne rentre pas ds le calcule de la polarité du "tokenCourant"
                            else{
                                TSO = TSO + p2*p1;
                                ic++;
                            }
                        }
                        // vérifier l'existance du "pred" dans le lexique l3
                        else if(verifierExistanceDuTokenDsLexique(pred, lexique3)!=-1){
                            //System.out.println("Existance du token : "+pred+" ds l3 = "+verifierExistanceDuTokenDsLexique(pred, lexique3));
                            p3 = RecupererLaPolarite(pred, lexique3);
                            System.out.println("token = "+succ+" est ds L3 polarité= "+p3+" ***************");
                            TSO = TSO + p3*p1;
                            ic++;
                        }
                    }
                    // "succ" (ou bien le "succ" sans caractères répétés) n'est pas vide et il est ds l3
                    else if(succ!="" && (verifierExistanceDuTokenDsLexique(succ, lexique3)!=-1 || verifierExistanceDuTokenDsLexique(eliminerLesCaractereRepetes(succ, true), lexique3)!=-1)){
                        // Traitement des caractères répétés pour le "succ"
                        if (considererCaracteresRepetes && contientDesCaractereRepete(succ)){
                            this.poidDesCaracteresRepetes = poidDesCaracteresRepetes;
                            succ = eliminerLesCaractereRepetes(succ, considererCaracteresRepetes);
                        }
                        // vérifier l'existance du "succ" dans le lexique l3
                        //System.out.println("Existance du token : "+succ+" ds l3 = "+verifierExistanceDuTokenDsLexique(succ, lexique3));
                        if(verifierExistanceDuTokenDsLexique(succ, lexique3)!=-1){
                            p3 = RecupererLaPolarite(succ, lexique3)*this.poidDesCaracteresRepetes;
                            System.out.println("token = "+succ+" est ds L3 polarité= "+p3+" ***************");
                            this.poidDesCaracteresRepetes = 1;
                            TSO = TSO + p3*p1;
                            ic+=2;
                        }
                   }
                    // "succ" et "pred" ne rentrent pas ds le calcule de la polarité du "tokenCourant"
                    else {
                        TSO = TSO + p1;
                        ic++;
                    }
                }
                else{
                    if (verifierExistanceDuTokenDsLexique(eliminerLesCaractereRepetes(tokenCourant, considererCaracteresRepetes),lexique2)==-1
                                                       && verifierExistanceDuTokenDsLexique(eliminerLesCaractereRepetes(tokenCourant, considererCaracteresRepetes),lexique3)==-1
                                                       && verifierExistanceDuTokenDsLexique(eliminerLesCaractereRepetes(tokenCourant, considererCaracteresRepetes),lexique4)==-1)
                        nbrDeTokenSubjectif--;
                    //System.out.println(nbrDeTokenSubjectif);
                    ic++;
                }
            }
            //System.out.println("------------------------------------------");
            System.out.println("La polarité totale du texte est = "+TSO);
            System.out.println("La subjectivité estimée est = "+calculerSubjectivite());
            //System.out.println(nbrDeTokenSubjectif);
            System.out.println("--------------------------------------------------");
        }
        else{
            System.out.println("L'attribut \"tokens\" est vide, utiliser la méthode setTokens() pour l'initialiser!");
        }
        return this.TSO;
    }

    /**
     * La méthode vérifie si la chaine de caractères <b>token</b> <br>
     * contient des caractères répérés ou pas.
     * @param token: La chaine de caractères à vérifier.
     * @return <b>true</b> si token contient des caractères répérés, <b>false</b> sinon.
     */
    public boolean contientDesCaractereRepete(String token){
        return token.matches(".*(.)\\1{1,}.*");
    }

    /**
     * La méthode élimine les caractères répétés dans une chiane de caractères passée<br>
     * en paramètre, le résultat est retourné sous forme de <b>String</b>
     * @param token: La chaine de caractères à traiter
     * @param oui: Eliminer ou pas
     * @return Une chaine de caractères sans caractères répétés
     */
    public String eliminerLesCaractereRepetes(String token, boolean oui){
        if (oui)
            return token.replaceAll("(.)\\1{1,}", "$1");
        return token;
    }

    public String successeurDuVecteur(int ic){
        if (ic<tokens.size()-1) return tokens.get(ic+1);
        else return "";
    }

    public String predecesseurDuVecteur(int ic){
        if (ic>0) return tokens.get(ic-1);
        else return "";
    }

    public String getL1() {
        return L1;
    }

    public String getL2() {
        return L2;
    }

    public String getL3() {
        return L3;
    }
    
    public double getTSO() {
        return TSO;
    }

    public static Vector<String> getTokens() {
        return tokens;
    }

    public void setL1(String L1) {
        this.L1 = L1;
    }

    public void setL2(String L2) {
        this.L2 = L2;
    }

    public void setL3(String L3) {
        this.L3 = L3;
    }

    public void setTokens(Vector<String> tokens) {
        CalculeDePolarite.tokens = tokens;
        nbrDeTokenTotale = tokens.size();
        nbrDeTokenSubjectif = nbrDeTokenTotale;
    }

    public double calculerSubjectivite(){
        subjectivite = (double)nbrDeTokenSubjectif / nbrDeTokenTotale;
        return subjectivite;
    }

    public boolean isConsidererCaracteresRepetes() {
        return considererCaracteresRepetes;
    }

    public void setConsidererCaracteresRepetes(boolean considererCaracteresRepetes) {
        this.considererCaracteresRepetes = considererCaracteresRepetes;
    }

    public int getNbrDeTokenSubjectif() {
        return nbrDeTokenSubjectif;
    }

    public void setNbrDeTokenSubjectif(int nbrDeTokenSubjectif) {
        this.nbrDeTokenSubjectif = nbrDeTokenSubjectif;
    }

    public int getNbrDeTokenTotale() {
        return nbrDeTokenTotale;
    }

    public void setNbrDeTokenTotale(int nbrDeTokenTotale) {
        this.nbrDeTokenTotale = nbrDeTokenTotale;
    }

    public double getPoidDesCaracteresRepetes() {
        return poidDesCaracteresRepetes;
    }

    public void setPoidDesCaracteresRepetes(double poidDesCaracteresRepetes) {
        this.poidDesCaracteresRepetes = poidDesCaracteresRepetes;
    }

    public double getSubjectivite() {
        return subjectivite;
    }

    public void setSubjectivite(double subjectivite) {
        this.subjectivite = subjectivite;
    }

    // Main de test
    public static void main(String []args){
        Vector<String> vec = new Vector<String>();
        
        //vec.add("الأوساخ");

        //vec.add("لااااا");
        //vec.add("ماشي");
        //vec.add("تبالي");
        //vec.add("ببببززززااااف");
        vec.add("عاشق");
        
        
        //vec.add("كاينة");

        //vec.add("روعه");
        //vec.add("بزاف");

        
        CalculeDePolarite test = new CalculeDePolarite(vec,"fichiers/lexique1.txt","fichiers/l2.txt","fichiers/l3.txt");
        test.calculerPolarite(true, "khoja", true, 1.5);
        //System.out.println(test.recupererSuccesseur("روووعه"));
        //test.calculerPolarite(true, "khoja");

        //test.raciniserToken(vec.get(1), "khoja");
        //System.out.println(test.raciniserToken(vec.get(1), "khoja").equals("وسخ"));

        //test.lireLeLexique(test.getL1());
        //System.out.println(test.recupererSuccesseur(vec.get(2)));
        
        //System.out.println(test.verifierExistanceDuTokenDsLexique("وسخ", test.lireLeLexique("fichiers/l1.txt")));
        //System.out.println(test.RecupererLaPolarite("بزاف", test.lireLeLexique("fichiers/l3.txt")));
        //String s = "بزاف";
        //System.out.println(s.equals(vec.get(2)));
                
    }

}


