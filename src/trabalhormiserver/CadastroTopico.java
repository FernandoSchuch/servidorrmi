/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhormiserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Projeto....: TrabalhoRMIServer Criado em..: 25/09/2017, 21:49:13 
 * Arquivo....: CadastroTopico.java
 *
 * @author Pablo Oliveira (jntpablo)
 */
public class CadastroTopico {

    private final Map<Integer, Topico> topicosCadastrados = new HashMap();
    private static CadastroTopico self = null;

    private CadastroTopico() {
        adicionarTopico("Futebol");
        adicionarTopico("Politica");
    }

    public static CadastroTopico getInstance() {
        if (self == null) {
            self = new CadastroTopico();
        }
        return self;
    }

    public Topico getTopico(Integer codigo) {
        return this.topicosCadastrados.get(codigo);
    }
    
    public ArrayList<Topico> getTodosTopicos(){
        ArrayList<Topico> todosTopicos = new ArrayList<Topico>();
        for (Integer codigoTopico : this.topicosCadastrados.keySet()) {
            todosTopicos.add(this.topicosCadastrados.get(codigoTopico));
        }
        return todosTopicos;
    }
    
    public Integer adicionarTopico(String descricao){
        Topico t1 = new Topico(topicosCadastrados.size() + 1, descricao);
        this.topicosCadastrados.put(t1.codigo, t1);
        GerenciadorInscricoes.getInstance().adicionarTopicoGerenciavel(t1.codigo);
        return t1.codigo;
    }
}
