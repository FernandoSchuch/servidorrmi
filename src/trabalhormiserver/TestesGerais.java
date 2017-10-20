/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trabalhormiserver;

/** 
 * Projeto....: TrabalhoRMIServer
 * Criado em..: 10/10/2017, 13:37:10 
 * Arquivo....: TestesGerais.java
 *
 * @author Pablo Oliveira (jntpablo)
 */
public class TestesGerais {
    public static void main(String[] args) {
        Noticia noticia = new Noticia(CadastroNoticias.getInstance().getNoticia(1));
        GerenciadorNoticiasLidasRecebidas.getInstance().iniciarMonitoramentoNoticiaPorUsuario(noticia);
        System.out.println(noticia.titulo);
        System.out.println(CadastroNoticias.getInstance().getNoticia(1).titulo);
        System.out.println("--------");
        noticia.titulo = "asdasd";
        System.out.println(noticia.titulo);
        System.out.println(CadastroNoticias.getInstance().getNoticia(1).titulo);
    }

}
