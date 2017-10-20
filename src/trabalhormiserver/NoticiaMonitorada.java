/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trabalhormiserver;

/** 
 * Projeto....: TrabalhoRMIServer
 * Criado em..: 09/10/2017, 20:57:21 
 * Arquivo....: NoticiaMonitorada.java
 *
 * @author Pablo Oliveira (jntpablo)
 */
public class NoticiaMonitorada {
    StatusNoticia status;
    Noticia noticia;

    public NoticiaMonitorada(StatusNoticia status, Noticia noticia) {
        this.status = status;
        this.noticia = noticia;
    }
    
}
