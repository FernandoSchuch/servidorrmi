/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhormiserver;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Projeto....: TrabalhoRMIServer Criado em..: 12/10/2017, 13:26:35 Arquivo....:
 * Notificador.java
 *
 * @author Pablo Oliveira (jntpablo)
 */
public class Notificador implements Runnable {

    private Usuario usuario;
    private Noticia noticia;

    public Notificador(Usuario usuario, Noticia noticia) {
        this.usuario = usuario;
        this.noticia = noticia;
    }

    @Override
    public void run() {
        Socket cliente;
        try {
            GerenciadorLog.getInstance().printLog("Inciando conexão com " + this.usuario.login);
            cliente = new Socket(this.usuario.ip, this.usuario.porta);
            GerenciadorLog.getInstance().printLogSucesso("Usuário " + this.usuario.login + " está online!");
            GerenciadorLog.getInstance().printLog("Enviando noticia...");
            PrintStream saida = new PrintStream(cliente.getOutputStream());
            saida.println(NoticiaProtocol.NoticiaProtocol(this.noticia));
            GerenciadorNoticiasLidasRecebidas.getInstance().marcarComoNaoLida(this.usuario.login, noticia);
            saida.close();
            cliente.close();
            GerenciadorLog.getInstance().printLogSucesso("Noticia enviada com sucesso");
        } catch (IOException ex) {
            GerenciadorLog.getInstance().printLogFalha("Ocorreu algum problema na conexão com o usuario " + this.usuario.login);
        }
    }

}
