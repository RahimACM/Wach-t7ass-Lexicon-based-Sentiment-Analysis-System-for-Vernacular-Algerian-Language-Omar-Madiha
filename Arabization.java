/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package traductionEtDetectionDeLangue;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringEscapeUtils;
import calculeDePolarite.ManipulerLesLexiques;
import calculeDePolarite.TokenDsLexique;
import java.util.Vector;
import java.lang.Math;

/**
 * Cette classe permet de donner la listes des mots (codés en Arabe)
 * les plus proche à un mot Arabish (codé en Latin) donné comme entré,
 * comme pour le mot <b>Jamil</b> son correspondant en Arabe est <b>جميل</b>
 * @author "OmarMadiha"
 */
public class Arabization {
    // Classe interne
    /**
     * Cette classe permet de sauvegarder une liste de mot passés en paramètre
     * avec le nom de leur lexique (L1, L2 ou L3)
     */
    
    // Attributs
    private ProcessBuilder pb ;
    private String cheminDuProcess = "C:\\Users\\Madiha\\Desktop\\arabish-master-python2\\arabish\\dist\\main";
    private String token = "jamil";
    private String resultatPython = "";
    private Vector<String> tokensCandidats = new Vector<String>() ;
    private Vector<String> listeDeTokens = new Vector<String>() ;
    private Vector<String> listeDeTokensArabizes = new Vector<String>() ;
    private String patternDuLatin = "[a-zA-Z_0-9_çèéêëîôœûùüÿàâæï_']+";
    private boolean testerRacineArabize = false;

    // Chemins des fichiers lexiques
    private String L1 = "fichiers/lexique1.txt";
    private String L2 = "fichiers/l2.txt";
    private String L3 = "fichiers/l3.txt";
    private ManipulerLesLexiques manipDesLexique ;

    /**
     * Constructeur par défaut
     */
    public Arabization(){
        this.manipDesLexique = new ManipulerLesLexiques(L1,L2,L3);
    }

    /**
     * Constructeur surchargé
     * @param token: Le mot à arabizer
     */
    public Arabization(String token){
        this.token = token;
        this.manipDesLexique = new ManipulerLesLexiques(L1,L2,L3);
        ProcessBuilder pb = new ProcessBuilder("C:\\Users\\Madiha\\Desktop\\arabish-master-python2\\arabish\\dist\\main", token);
        pb.redirectErrorStream(true);
        try {
            Process p = pb.start();
            BufferedReader stdout = new BufferedReader (
                new InputStreamReader(p.getInputStream()));
            resultatPython = stdout.readLine();
            //arabizerToken();
            p.getInputStream().close();
            p.getOutputStream().close();
            p.getErrorStream().close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Arabization(Vector<String> texte){
        this.manipDesLexique = new ManipulerLesLexiques(L1,L2,L3);
        this.listeDeTokens = texte;
        ProcessBuilder pb = new ProcessBuilder("C:\\Users\\Madiha\\Desktop\\arabish-master-python2\\arabish\\dist\\main", token);
        pb.redirectErrorStream(true);
        try {
            Process p = pb.start();
            BufferedReader stdout = new BufferedReader (
                new InputStreamReader(p.getInputStream()));
            resultatPython = stdout.readLine();
            //arabizerToken();
            p.getInputStream().close();
            p.getOutputStream().close();
            p.getErrorStream().close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Arabization(String token, String L1, String L2, String L3){
        this.L1 = L1;
        this.L2 = L2;
        this.L3 = L3;
        this.manipDesLexique = new ManipulerLesLexiques(L1,L2,L3);
        this.token = token;
        ProcessBuilder pb = new ProcessBuilder("C:\\Users\\Madiha\\Desktop\\arabish-master-python2\\arabish\\dist\\main", token);
        pb.redirectErrorStream(true);
        try {
            Process p = pb.start();
            BufferedReader stdout = new BufferedReader (
                new InputStreamReader(p.getInputStream()));
            resultatPython = stdout.readLine();
            //arabizerToken();
            p.getInputStream().close();
            p.getOutputStream().close();
            p.getErrorStream().close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Arabization(Vector<String> texte, String L1, String L2, String L3){
        this.L1 = L1;
        this.L2 = L2;
        this.L3 = L3;
        this.manipDesLexique = new ManipulerLesLexiques(L1,L2,L3);
        this.listeDeTokens = texte;
        ProcessBuilder pb = new ProcessBuilder("C:\\Users\\Madiha\\Desktop\\arabish-master-python2\\arabish\\dist\\main", token);
        pb.redirectErrorStream(true);
        try {
            Process p = pb.start();
            BufferedReader stdout = new BufferedReader (
                new InputStreamReader(p.getInputStream()));
            resultatPython = stdout.readLine();
            //arabizerToken();
            p.getInputStream().close();
            p.getOutputStream().close();
            p.getErrorStream().close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Arabization(Vector<String> texte, String L1, String L2, String L3, boolean testerRacineArabize){
        this.L1 = L1;
        this.L2 = L2;
        this.L3 = L3;
        this.manipDesLexique = new ManipulerLesLexiques(L1,L2,L3);
        this.listeDeTokens = texte;
        this.testerRacineArabize = testerRacineArabize;
        ProcessBuilder pb = new ProcessBuilder("C:\\Users\\Madiha\\Desktop\\arabish-master-python2\\arabish\\dist\\main", token);
        pb.redirectErrorStream(true);
        try {
            Process p = pb.start();
            BufferedReader stdout = new BufferedReader (
                new InputStreamReader(p.getInputStream()));
            resultatPython = stdout.readLine();
            //arabizerToken();
            p.getInputStream().close();
            p.getOutputStream().close();
            p.getErrorStream().close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Vector<String> arabizerToken(){
        if (resultatPython!=""){
            String tmp = resultatPython;
            tmp = tmp.replaceAll("set|\\(|\\)|\\[|\\]|u'| ", "");
            tmp = tmp.replaceAll("'", "");
            String [] tmpVec = tmp.split(",");
            System.out.println("Résultat d'arabization du token \""+token+"\" :");
            int i = 0;
            for(String s : tmpVec){
                s = StringEscapeUtils.unescapeJava(s);
                tokensCandidats.add(s);
                System.out.println(s);
            }
            /*
            File ff=new File("C:\\Users\\Madiha\\Desktop\\resultat.txt"); // définir l'arborescence
            try {
                ff.createNewFile();
                FileWriter ffw=new FileWriter(ff);
                ffw.write("****** ");  // écrire une ligne dans le fichier resultat.txt
                ffw.write("\n"); // forcer le passage à la ligne
                for(String s : tokensCandidats){
                    System.out.println(StringEscapeUtils.unescapeJava(s));
                    ffw.write("\n"); // forcer le passage à la ligne
                    ffw.write(StringEscapeUtils.unescapeJava(s)); // forcer le passage à la ligne
                }
                ffw.close(); // fermer le fichier à la fin des traitements
            } catch (IOException ex) {
                Logger.getLogger(Arabization.class.getName()).log(Level.SEVERE, null, ex);
            }
             *
             */
        }
        resultatPython="";
        return tokensCandidats;
    }

    public Vector<String> arabizerToken(String token){
        ProcessBuilder pb = new ProcessBuilder("C:\\Users\\Madiha\\Desktop\\arabish-master-python2\\arabish\\dist\\main", token);
        pb.redirectErrorStream(true);
        try {
            Process p = pb.start();
            BufferedReader stdout = new BufferedReader (
                new InputStreamReader(p.getInputStream()));
            resultatPython = stdout.readLine();
            //arabizerToken();
            p.getInputStream().close();
            p.getOutputStream().close();
            p.getErrorStream().close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (resultatPython!=""){
            String tmp = resultatPython;
            //System.out.println("resultatPython = "+resultatPython);
            tmp = tmp.replaceAll("set|\\(|\\)|\\[|\\]|u'| ", "");
            tmp = tmp.replaceAll("'", "");
            String [] tmpVec = tmp.split(",");
            System.out.println("Résultat d'arabization du token \""+token+"\" :");
            int i = 0;
            tokensCandidats = new Vector<String>();
            for(String s : tmpVec){
                s = StringEscapeUtils.unescapeJava(s);
                tokensCandidats.add(s);
                System.out.println(s);
            }
            resultatPython="";
        }
        return tokensCandidats;
    }

    public Vector<String> arabizerTexte(){
        for(String mot: listeDeTokens){
            if(cestDuLatin(mot)){
                // l'Arabisation exige des caractère en miniscule
                String tmp = mot.toLowerCase();
                listeDeTokensArabizes.add(calculerLeTokenArabeLePlusProche(tmp));
            }
            else{
                listeDeTokensArabizes.add(mot);
            }
        }
        return listeDeTokensArabizes;
    }

    public boolean cestDuLatin(String mot){
        return mot.matches(patternDuLatin);
    }

    /**
     * Cette méthode permet - à partir d'une liste de mots candidats : l'attribut <b>tokensCandidats</b> - de récuperer un
     * vecteurs d'objets instanciés de la classe <b>TokensDansLexique</b>, chaque objets contient une liste de mots
     * et le nom du lexiques dont ils appartiennent.
     * @return Un vecteurs d'objets instanciés de la classe <b>TokensDansLexique</b>
     */
    public Vector<TokensDansLexique> recupererListeDeTokensCandidatsExistants(){
        Vector<TokensDansLexique> vecTokensDsLexique = new Vector<TokensDansLexique>();
        // vecTokensDsLexique.get(0) --> tokens ds L1, vecTokensDsLexique.get(1) --> tokens ds L2, vecTokensDsLexique.get(2) --> tokens ds L3
        TokensDansLexique tDsL1 = new TokensDansLexique("L1");
        TokensDansLexique tDsL2 = new TokensDansLexique("L2");
        TokensDansLexique tDsL3 = new TokensDansLexique("L3");
        try{
            //System.out.println("*************************");
            for(String tCandidats: tokensCandidats){
                TokenDsLexique tokenDsL = manipDesLexique.estDansLexique(tCandidats, testerRacineArabize);
                //System.out.println(tokenDsL.toString());
                if (tokenDsL.getIndexDsLexique()!=-1){
                    if(tokenDsL.getLexique() == "L1"){
                        tDsL1.ajouterToken(tokenDsL.getToken());
                        tDsL1.setExiste(true);
                    }
                    else if(tokenDsL.getLexique() == "L2"){
                        tDsL2.ajouterToken(tokenDsL.getToken());
                        tDsL2.setExiste(true);
                    }
                    else{
                        tDsL3.ajouterToken(tokenDsL.getToken());
                        tDsL3.setExiste(true);
                    }
                }
            }
            vecTokensDsLexique.add(tDsL1);
            vecTokensDsLexique.add(tDsL2);
            vecTokensDsLexique.add(tDsL3);
        }catch(NullPointerException ex){
            ex.printStackTrace();
        }
        return vecTokensDsLexique;
    }

    public String calculerLeTokenArabeLePlusProche(String mot){
        String motArabe = new String(mot);
        int minDif = 100, j =0;
        arabizerToken(mot);
        Vector<TokensDansLexique> vec = new Vector<TokensDansLexique>();
        vec = recupererListeDeTokensCandidatsExistants();
        TokensDansLexique t = new TokensDansLexique();
        while (j<vec.size()){
            t = vec.get(j);
            if (t.isExiste()){
                int i =0;
                String tmp = new String();

                while(minDif!=0 && i<t.getTokensAppartenantsAuLexique().size()){
                    tmp = t.getTokensAppartenantsAuLexique().get(i);
                    System.out.println("Token Existant ds l'un des lexiques :---------------- "+t.getTokensAppartenantsAuLexique().get(i));
                    //System.out.println(t.toString());
                    if (minDif > (tmp.length() - mot.length())){
                        minDif = Math.abs(tmp.length() - mot.length());
                        motArabe = tmp;
                    }
                    i++;
                }
            }
            minDif = 100;
            j++;
        }
        
        System.out.println("Le mot arabizé le plus proche au token = \""+mot+"\" est :"+motArabe);
        return motArabe;
        /*
        for(TokensDansLexique t :recupererListeDeTokensCandidatsExistants()){
           //System.out.println(t.toString());
            if (t.isExiste()){
                int i =0;
                String tmp = new String();

                while(minDif!=0 && i<t.getTokensAppartenantsAuLexique().size()){
                    tmp = t.getTokensAppartenantsAuLexique().get(i);
                    //System.out.println("Existe ds l'un des lexiques :---------------- "+t.getTokensAppartenantsAuLexique().get(i));
                    //System.out.println("tmp= "+tmp);
                    System.out.println(t.toString());
                    if (minDif > (tmp.length() - mot.length())){
                        minDif = Math.abs(tmp.length() - mot.length());
                        motArabe = tmp;
                    }
                    i++;
                }
            }
            minDif = 100;
        }
        System.out.println("Le mot arabizé le plus proche au token = \""+mot+"\" est :"+motArabe);
        return motArabe;
         *
         */
    }

    public String getL1() {
        return L1;
    }

    public void setL1(String L1) {
        this.L1 = L1;
    }

    public String getL2() {
        return L2;
    }

    public void setL2(String L2) {
        this.L2 = L2;
    }

    public String getL3() {
        return L3;
    }

    public void setL3(String L3) {
        this.L3 = L3;
    }

    public String getCheminDuProcess() {
        return cheminDuProcess;
    }

    public void setCheminDuProcess(String cheminDuProcess) {
        this.cheminDuProcess = cheminDuProcess;
    }

    public Vector<String> getListeDeTokens() {
        return listeDeTokens;
    }

    public void setListeDeTokens(Vector<String> listeDeTokens) {
        this.listeDeTokens = listeDeTokens;
    }

    public Vector<String> getListeDeTokensArabizes() {
        return listeDeTokensArabizes;
    }

    public void setListeDeTokensArabizes(Vector<String> listeDeTokensArabizes) {
        this.listeDeTokensArabizes = listeDeTokensArabizes;
    }

    public ManipulerLesLexiques getManipDesLexique() {
        return manipDesLexique;
    }

    public void setManipDesLexique(ManipulerLesLexiques manipDesLexique) {
        this.manipDesLexique = manipDesLexique;
    }

    public String getPatternDuLatin() {
        return patternDuLatin;
    }

    public void setPatternDuLatin(String patternDuLatin) {
        this.patternDuLatin = patternDuLatin;
    }

    public ProcessBuilder getPb() {
        return pb;
    }

    public void setPb(ProcessBuilder pb) {
        this.pb = pb;
    }

    public String getResultatPython() {
        return resultatPython;
    }

    public void setResultatPython(String resultatPython) {
        this.resultatPython = resultatPython;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Vector<String> getTokensCandidats() {
        return tokensCandidats;
    }

    public void setTokensCandidats(Vector<String> tokensCandidats) {
        this.tokensCandidats = tokensCandidats;
    }

    public static void main(String[] args) {
        
        Vector<String> vec = new Vector<String>();
        vec.add("HASSAN");
        //vec.add("mlih");

        Arabization test = new Arabization(vec);
        test.arabizerTexte();
        //System.out.println("Token Arabizé le Plus Proche = "+test.calculerLeTokenArabeLePlusProche("jamil"));

    }
}