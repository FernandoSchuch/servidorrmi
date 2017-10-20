/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhormiserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Projeto....: TrabalhoRMIServer Criado em..: 30/09/2017, 09:59:39 Arquivo....:
 * GerenciadorInscricoes.java
 *
 * @author Pablo Oliveira (jntpablo)
 */
public class GerenciadorInscricoes {

    //  <codigoTopico, listaUsuarios>
    private final Map<Integer, List<String>> inscricoes = new HashMap();
    private static GerenciadorInscricoes self = null;

    private GerenciadorInscricoes() {

    }

    public static GerenciadorInscricoes getInstance() {
        if (self == null) {
            self = new GerenciadorInscricoes();
        }
        return self;
    }

    public boolean adicionarInscricao(Integer codigoTopico, String loginUsuario) {
        if (!this.inscricoes.get(codigoTopico).contains(loginUsuario)) {
            this.inscricoes.get(codigoTopico).add(loginUsuario);
            GerenciadorLog.getInstance().printLogSucesso("Usuário " + loginUsuario + " inscrito ao tópico de código " + codigoTopico);
        } else {
            GerenciadorLog.getInstance().printLogFalha("Usuário " + loginUsuario + " JA ESTÁ inscrito ao tópico de código " + codigoTopico);
        }
        return true;
    }

    public boolean removerInscricao(Integer codigoTopico, String loginUsuario) {
        if (this.inscricoes.get(codigoTopico).contains(loginUsuario)) {
            this.inscricoes.get(codigoTopico).remove(loginUsuario);
            GerenciadorLog.getInstance().printLogSucesso("Usuário " + loginUsuario + " deixou de seguir o tópico de código " + codigoTopico);
        } else {
            GerenciadorLog.getInstance().printLogFalha("Usuário " + loginUsuario + " não segue o tópico de código " + codigoTopico);
        }
        return true;
    }

    private boolean topicoJaRegistrado(Integer codigoTopico) {
        return this.inscricoes.keySet().contains(codigoTopico);
    }

    public ArrayList<Topico> getTopicosInscritos(String userLogin) {
        ArrayList<Topico> topicosInscritos = new ArrayList<Topico>();
        for (Integer topico : this.inscricoes.keySet()) {
            if (this.inscricoes.get(topico).contains(userLogin)) {
                topicosInscritos.add(CadastroTopico.getInstance().getTopico(topico));
            }
        }
        return topicosInscritos;
    }

    public List<String> getUsuariosInscritos(Integer codigoTopico) {
        return this.inscricoes.get(codigoTopico);
    }

    public ArrayList<Topico> getTopicosNaoInscritos(String userLogin) {
        ArrayList<Topico> topicosInscritos = new ArrayList<Topico>();
        for (Integer topico : this.inscricoes.keySet()) {
            if (!this.inscricoes.get(topico).contains(userLogin)) {
                topicosInscritos.add(CadastroTopico.getInstance().getTopico(topico));
            }
        }
        return topicosInscritos;
    }

    public void adicionarTopicoGerenciavel(Integer codigoTopico) {
        if (!topicoJaRegistrado(codigoTopico)) {
            this.inscricoes.put(codigoTopico, new ArrayList<>());
        }
    }
}
