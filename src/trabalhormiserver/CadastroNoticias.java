/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhormiserver;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Projeto....: TrabalhoRMIServer Criado em..: 25/09/2017, 21:59:21 Arquivo....:
 * CadastroNoticias.java
 *
 * @author Pablo Oliveira (jntpablo)
 */
public class CadastroNoticias {

    
    private final Map<Integer, Noticia> noticiasCadastradas = new LinkedHashMap<>();
    private static CadastroNoticias self = null;

    private CadastroNoticias() {
        Topico topico = CadastroTopico.getInstance().getTopico(1);
        Escritor e = (Escritor) CadastroUsuario.getInstance().getUsuario("escritor");
        Noticia noticia;
        noticia = new Noticia("Fernandocão", "Texto do Pablito", topico, e);
        noticia.codigo = noticiasCadastradas.size() + 1;
        noticia.dataPublicacao = "12/12/2012";
        this.noticiasCadastradas.put(noticia.codigo, noticia);
        GerenciadorNoticiasLidasRecebidas.getInstance().adicionarUsuarioParaControle("leitor");
        GerenciadorNoticiasLidasRecebidas.getInstance().iniciarMonitoramentoNoticiaPorUsuario(noticia);
    }

    public static CadastroNoticias getInstance() {
        if (self == null) {
            self = new CadastroNoticias();
        }
        return self;
    }

    public Noticia cadastrarNoticia(Noticia noticia) {
        noticia.codigo = noticiasCadastradas.size() + 1;
        noticia.dataPublicacao = DateUtil.gerDataAtualFormatada();
        noticiasCadastradas.put(noticia.codigo, noticia);
        return noticia;
    }

    public ArrayList<Noticia> getTodasNoticias() {
        ArrayList noticias = new ArrayList<Noticia>();
        for (Integer codigo : noticiasCadastradas.keySet()) {
            noticias.add(noticiasCadastradas.get(codigo));
        }
        return noticias;
    }

    public ArrayList<Noticia> getNoticias(Topico topic, String dataIni, String dataFim) {
        ArrayList noticias = new ArrayList<Noticia>();
        for (Integer codigo : noticiasCadastradas.keySet()) {
            Noticia noticia = noticiasCadastradas.get(codigo);
            if (noticia.topico.codigo.compareTo(topic.codigo) == 0 && DateUtil.isBetween(noticia.dataPublicacao, dataIni, dataFim)) {
                noticias.add(noticiasCadastradas.get(codigo));
            }
        }
        return noticias;
    }
    
    public ArrayList<Noticia> getTodasNoticiasTopico(Topico topic) {
        ArrayList noticias = new ArrayList<Noticia>();
        for (Integer codigo : noticiasCadastradas.keySet()) {
            Noticia noticia = noticiasCadastradas.get(codigo);
            if (noticia.topico.codigo.compareTo(topic.codigo) == 0) {
                noticias.add(noticiasCadastradas.get(codigo));
            }
        }
        return noticias;
    }

    public Noticia getUltimaNoticia(Topico topic) {
        List noticiasDesteTopico = new LinkedList<Noticia>();
        Noticia ultimaNoticia;
        for (Integer codigo : noticiasCadastradas.keySet()) {
            Noticia noticia = noticiasCadastradas.get(codigo);
            if (noticia.topico.codigo.compareTo(topic.codigo) == 0) {
                noticiasDesteTopico.add(noticiasCadastradas.get(codigo));
            }
        }
        int indiceUltimaNoticia = noticiasDesteTopico.size() - 1;
        ultimaNoticia = (Noticia) noticiasDesteTopico.get(indiceUltimaNoticia);
        return ultimaNoticia;
    }
    
    public Noticia getNoticia(Integer codigoNoticia){
        Noticia noticia = this.noticiasCadastradas.get(codigoNoticia);
        return noticia;
    }

}
