/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhormiserver;

import java.util.List;

/**
 * Projeto....: TrabalhoRMIServer Criado em..: 30/09/2017, 08:45:15 Arquivo....:
 * GerenciadorNotificacoes.java
 *
 * @author Pablo Oliveira (jntpablo)
 */
public class GerenciadorNotificacoes {

    public static void notificarUsuario(List<String> usuarios, Noticia noticia) {
        for (String usuario : usuarios) {
            notificarUsuario(usuario, noticia);
        }
    }

    public static void notificarUsuario(String loginUsuario, Noticia noticia) {
        Usuario usuario = CadastroUsuario.getInstance().getUsuario(loginUsuario);
        notificarUsuario(usuario, noticia);
    }

    public static void notificarUsuario(Usuario usuario, Noticia noticia) {
        Notificador notificador = new Notificador(usuario, noticia);
        Thread notificacao = new Thread(notificador);
        notificacao.start();
    }
}
