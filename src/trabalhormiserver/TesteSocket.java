/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhormiserver;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Projeto....: TrabalhoRMIServer Criado em..: 30/09/2017, 08:34:26 Arquivo....:
 * TesteSocket.java
 *
 * @author Pablo Oliveira (jntpablo)
 */
public class TesteSocket {

    public static void dmain(String[] args) {

        Socket cliente;
        try {

            cliente = new Socket("localhost", 12399);
            if (cliente.isConnected()) {
                System.out.println("Conectado");
            } else {
                System.out.println("Nao Conectado");
            }
            System.out.println("O cliente se conectou ao servidor!");
            Noticia noticia = CadastroNoticias.getInstance().getTodasNoticias().get(0);
            PrintStream saida = new PrintStream(cliente.getOutputStream());
            saida.println(NoticiaProtocol.NoticiaProtocol(noticia));
            saida.close();
            cliente.close();
        } catch (IOException ex) {
            Logger.getLogger(TesteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        GerenciadorLog.getInstance().ativarLogs();
        Usuario usuario = CadastroUsuario.getInstance().getUsuario("leitor");
        usuario.ip = "localhost";
        usuario.porta = 12399;
        GerenciadorNotificacoes.notificarUsuario(usuario, CadastroNoticias.getInstance().getNoticia(1));
    }
}
