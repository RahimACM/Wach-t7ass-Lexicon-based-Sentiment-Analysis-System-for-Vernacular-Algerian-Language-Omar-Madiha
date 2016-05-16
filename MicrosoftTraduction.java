/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package traductionEtDetectionDeLangue;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import com.memetix.mst.detect.Detect;
import com.memetix.mst.language.SpokenDialect;
import com.memetix.mst.speak.Speak;
import com.memetix.mst.sentence.BreakSentences;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cette classe permet de traduire un mot passé en entré (s'il n'est pas codé en arabe) vres l'arabe
 * @author "OmarMadiha"
 */
public class MicrosoftTraduction {
    // Attributs
    String ClientId="OmarMadihaMSTraslator";
    String ClientSecret="mW3xP1Mh+fNDwUIe3aBqxLyIg34sFCi31HkpmUPFfvA=";
    String patternDuLatin = "[a-zA-Z_0-9_çèéêëîôœûùüÿàâæï_']+";
    String token = new String();
    String tokenTraduit = new String();

    public MicrosoftTraduction(){
        Translate.setClientId(ClientId);
        Translate.setClientSecret(ClientSecret);
        Detect.setClientId(ClientId);
        Detect.setClientSecret(ClientSecret);
    }

    public MicrosoftTraduction(String ClientId, String ClientSecret){
        this.ClientSecret = ClientSecret;
        this.ClientId = ClientId;
        Translate.setClientId(ClientId);
        Translate.setClientSecret(ClientSecret);
        Detect.setClientId(ClientId);
        Detect.setClientSecret(ClientSecret);
    }

    public boolean cestDuLatin(String mot){
        return mot.matches(patternDuLatin);
    }

    public String traduireMot(String mot, boolean testerSiLeMotEstEnLatin){
        if (testerSiLeMotEstEnLatin){
            if(cestDuLatin(mot)){
                try {
                    tokenTraduit = Translate.execute(mot, Language.AUTO_DETECT, Language.ARABIC);
                }
                catch (Exception ex) {
                    Logger.getLogger(MicrosoftTraduction.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                tokenTraduit = mot;
            }
        }
        else {
            try {
                tokenTraduit = Translate.execute(mot, Language.AUTO_DETECT, Language.ARABIC);
            }
            catch (Exception ex) {
                Logger.getLogger(MicrosoftTraduction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tokenTraduit;
    }

    public String detecterLangue(String mot, boolean testerSiLeMotEstEnLatin){
        String langue = "ar";
        if (testerSiLeMotEstEnLatin){
            if(cestDuLatin(mot)){
                try {
                    langue = Detect.execute(mot).toString();
                }
                catch (Exception ex) {
                    Logger.getLogger(MicrosoftTraduction.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                tokenTraduit = mot;
            }
        }
        else {
            try {
                langue = Detect.execute(mot).toString();
            }
            catch (Exception ex) {
                Logger.getLogger(MicrosoftTraduction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return langue;
    }

    public static void main(String args[]){
        MicrosoftTraduction test = new MicrosoftTraduction();
        //System.out.println(test.traduireMot("jaw",true));
        System.out.println(test.detecterLangue("nrou7",true));
    }

}
