/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhormiserver;

import java.io.Serializable;
import java.util.Date;
import javax.swing.Icon;

/**
 *
 * @author asuspc
 */
public class Noticia implements Serializable{
    public Integer codigo;
    public String titulo;
    public String texto;
    public Topico topico;
    public String dataPublicacao;
    public Escritor publicador;
    public Icon icone;
    public boolean lida;
    
    public Noticia(String titulo, String texto, Topico topico, Escritor publicador){
        this.titulo = titulo;
        this.texto = texto;
        this.topico = topico;
        this.publicador = publicador;
        //dataPublicacao = getDataHora;//vem do servidor
    }
    
    public Noticia(Integer codigo, String titulo, String texto, Integer codTopico, String nomeTopico, String data){
        this.codigo = codigo;
        this.titulo = titulo;
        this.texto  = texto;
        this.topico = new Topico(codTopico, nomeTopico);
        this.dataPublicacao = data;
        
    }
    
    public Noticia(Noticia noticia) {
        this.codigo = noticia.codigo;
        this.titulo = noticia.titulo;
        this.texto = noticia.texto;
        this.topico = noticia.topico;
        this.publicador = noticia.publicador;
        this.dataPublicacao = noticia.dataPublicacao;
        this.lida = noticia.lida;
    }
}
