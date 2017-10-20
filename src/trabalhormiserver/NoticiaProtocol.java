/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhormiserver;

import java.text.SimpleDateFormat;
import java.util.Formatter;

/**
 *
 * @author asuspc
 */
public class NoticiaProtocol {
    
    public static String NoticiaProtocol(Noticia n){
        return n.codigo + "|" +
               n.titulo + "|" +  
               n.texto + "|" + 
               n.topico.codigo + "|" +
               n.topico.nome + "|" +
               n.dataPublicacao;
    }    
    
    public static Noticia NoticiaProtocol(String msg){
        String[] arr = msg.split("|");        
        Noticia n = new Noticia(Integer.parseInt(arr[0]), arr[1], arr[2], Integer.parseInt(arr[3]), arr[4], arr[5]);
        n.icone = Util.getMailIcon();
        return n;
    }
}
