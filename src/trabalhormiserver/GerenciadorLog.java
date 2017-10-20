/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhormiserver;

/**
 * Projeto....: TrabalhoRMIServer Criado em..: 12/10/2017, 13:43:46 Arquivo....:
 * GerenciadorLog.java
 *
 * @author Pablo Oliveira (jntpablo)
 */
public class GerenciadorLog {

    private boolean logsAtivos;
    private static GerenciadorLog self = null;

    private GerenciadorLog() {
        this.logsAtivos = false;
    }

    public static GerenciadorLog getInstance() {
        if (self == null) {
            self = new GerenciadorLog();
        }
        return self;
    }

    public void ativarLogs() {
        this.logsAtivos = Boolean.TRUE;
    }

    public void desativarLogs() {
        this.logsAtivos = Boolean.FALSE;
    }

    public void printLog(String mensagemLog) {
        if (logsAtivos) {
            System.out.println("[INFO] - " + mensagemLog);
        }
    }

    public void printLogSucesso(String mensagemLog) {
        if (logsAtivos) {
            System.out.println("[SUCESSO] - " + mensagemLog);
        }
    }

    public void printLogFalha(String mensagemLog) {
        if (logsAtivos) {
            System.out.println("[FALHA] - " + mensagemLog);
        }
    }
}
