/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package calculeDePolarite;

/**
 * 
 * @author "OmarMadiha"
 */
public class TokenDsLexique{
    private String lexique;
    private int indexDsLexique;
    private String token;

    public TokenDsLexique(){
        this.lexique = new String("Aucun_lexique");
        this.indexDsLexique = -1;
        this.token = "pas_de_token";
    }

    public TokenDsLexique(String lexique, int indexDsLexique, String token){
        this.lexique = lexique;
        this.indexDsLexique = indexDsLexique;
        this.token = token;
    }

    @Override
    public String toString(){
        String str = new String();
        str = "token: "+token+" | lexique: "+lexique+" | index:"+indexDsLexique;
        return str;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public int getIndexDsLexique() {
        return indexDsLexique;
    }

    public void setIndexDsLexique(int indexDsLexique) {
        this.indexDsLexique = indexDsLexique;
    }

    public String getLexique() {
        return lexique;
    }

    public void setLexique(String lexique) {
        this.lexique = lexique;
    }

}
    