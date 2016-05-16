/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analyserDesFichier;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.Vector;

/**
 * 
 * @author "OmarMadiha"
 */
public class AnalyserFichierXML extends AnalyserFichierTxt {

    
    /**
     * Constructeur par défaut.
     */
    public AnalyserFichierXML(){
        super("C:\\Users\\Madiha\\Desktop\\testeXml.xml");
    }

    /**
     *
     * @param cheminDuFichier
     */
    public AnalyserFichierXML(String cheminDuFichier){
        super(cheminDuFichier);
    }

    /**
     *
     * @param cheminDuFichier
     * @param testerSimilarite
     * @param cheminDeLaBddDuCorpusC2
     * @param seuil : Utiliser comme seuil minimum pour le calcul de similarité.
     * @param utiliserStemmer
     * @param stemmingMethode
     * @param utiliserTraduction
     * @param nomDuTraducteur
     * @param utiliserArabization
     * @param testerRacineArabize
     * @param considererCaractèreRépeter
     */
    public AnalyserFichierXML(String cheminDuFichier,
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
        super( cheminDuFichier,
              testerSimilarite,
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
    
    public void lireFichierXML(){
        try {
            System.out.println("Lecture du fichier XML:");
            File fXmlFile = new File(cheminDuFichier);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("texte");
            String phraseTmp = new String();
            //phrases = new Vector<StructurePhrase>();

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    //System.out.println("texte id : " + eElement.getAttribute("id"));
                    System.out.println("contenu : " + eElement.getElementsByTagName("contenu").item(0).getTextContent());
                    phraseTmp = eElement.getElementsByTagName("contenu").item(0).getTextContent();
                    StructurePhrase structPhrase = new StructurePhrase(phraseTmp, 0, 0);
                    phrases.add(structPhrase);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
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
        lireFichierXML();
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
    
    
    public static void main(String []args){
        try {
            AnalyserFichierXML teste = new AnalyserFichierXML();
            teste.lireFichierXML();
            teste.analyserFichier('n', 2);
        } catch (Exception ex) {
            
        }

    }

}
