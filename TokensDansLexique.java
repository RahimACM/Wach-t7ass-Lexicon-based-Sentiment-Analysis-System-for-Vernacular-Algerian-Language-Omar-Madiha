/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package traductionEtDetectionDeLangue;

import java.util.Vector;

/**
 * 
 * @author "OmarMadiha"
 */
class TokensDansLexique{
    private boolean existe = false;
    private Vector<String> tokensAppartenantsAuLexique = new Vector<String>();
    private String nomDuLexique;

    TokensDansLexique(){
        this.nomDuLexique = "";
    }

    TokensDansLexique(String nomDuLexique){
        this.nomDuLexique = nomDuLexique;
    }

    TokensDansLexique(Vector<String> tokensAppartenantsAuLexique, String nomDuLexique, boolean existe){
        this.tokensAppartenantsAuLexique = tokensAppartenantsAuLexique;
        this.nomDuLexique = nomDuLexique;
        this.existe = existe;
    }

    public void ajouterToken(String token) {
        tokensAppartenantsAuLexique.add(token);
    }

    @Override
    public String toString(){
        String resultat = "";
        for( String s:this.tokensAppartenantsAuLexique){
            resultat+="|"+s;
        }
        resultat+=" : "+this.nomDuLexique+" -- "+existe;
        return resultat;
    }

    public boolean isExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }
    
    public String getNomDuLexique() {
        return nomDuLexique;
    }

    public void setNomDuLexique(String nomDuLexique) {
        this.nomDuLexique = nomDuLexique;
    }

    public Vector<String> getTokensAppartenantsAuLexique() {
        return tokensAppartenantsAuLexique;
    }

    public void setTokensAppartenantsAuLexique(Vector<String> tokensAppartenantsAuLexique) {
        this.tokensAppartenantsAuLexique = tokensAppartenantsAuLexique;
    }
}
