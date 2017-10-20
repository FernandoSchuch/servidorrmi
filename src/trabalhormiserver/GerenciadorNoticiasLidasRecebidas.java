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
 * Projeto....: TrabalhoRMIServer Criado em..: 09/10/2017, 20:06:01 Arquivo....:
 * GerenciadorNoticiasLidasRecebidas.java
 *
 * @author Pablo Oliveira (jntpablo)
 */
public class GerenciadorNoticiasLidasRecebidas {

    //  <loginUsuario, noticiasNaolLida>
    private final Map<String, List<NoticiaMonitorada>> noticiasNaoLidas = new HashMap();
    private static GerenciadorNoticiasLidasRecebidas self = null;

    private GerenciadorNoticiasLidasRecebidas() {

    }

    public static GerenciadorNoticiasLidasRecebidas getInstance() {
        if (self == null) {
            self = new GerenciadorNoticiasLidasRecebidas();
        }
        return self;
    }

    public void iniciarMonitoramentoNoticiaPorUsuario(String loginUsuario, ArrayList<Noticia> noticias) {

        for (Noticia noticia : noticias) {
            List<String> usuariosInscritosNesteTopico = GerenciadorInscricoes.getInstance().getUsuariosInscritos(noticia.topico.codigo);
            NoticiaMonitorada noticiaMonitorada = null;
            if (loginUsuario != null && !loginUsuario.isEmpty()) {
                if (usuariosInscritosNesteTopico.contains(loginUsuario)) {
                    noticiaMonitorada = new NoticiaMonitorada(StatusNoticia.NAO_RECEBIDA, noticia);
                } else {
                    noticiaMonitorada = new NoticiaMonitorada(StatusNoticia.NAO_LIDA, noticia);
                }
                this.noticiasNaoLidas.get(loginUsuario).add(noticiaMonitorada);
            } else {
                for (String usuario : this.noticiasNaoLidas.keySet()) {
                    if (usuariosInscritosNesteTopico.contains(usuario)) {
                        noticiaMonitorada = new NoticiaMonitorada(StatusNoticia.NAO_RECEBIDA, noticia);
                    } else {
                        noticiaMonitorada = new NoticiaMonitorada(StatusNoticia.NAO_LIDA, noticia);
                    }
                    this.noticiasNaoLidas.get(usuario).add(noticiaMonitorada);
                }
            }
        }

    }

    public void iniciarMonitoramentoNoticiaPorUsuario(Noticia noticia) {
        ArrayList<Noticia> noticias = new ArrayList<Noticia>();
        noticias.add(noticia);
        iniciarMonitoramentoNoticiaPorUsuario(null, noticias);
    }

//    public boolean adicionarNoticiaMonitorada(List<String> codigosUsuario, Noticia noticia) {
//        for (String loginUsuario : codigosUsuario) {
//            adicionarNoticiaMonitorada(loginUsuario, noticia);
//        }
//
//        return true;
//    }
//
//    public boolean adicionarNoticiaMonitorada(String loginUsuario, Noticia noticia) {
//        if (loginUsuario != null && !loginUsuario.isEmpty()) {
//            NoticiaMonitorada noticiaMonitorada = new NoticiaMonitorada(StatusNoticia.NAO_RECEBIDA, noticia);
//            adicionarUsuarioParaControle(loginUsuario);
//            this.noticiasNaoLidas.get(loginUsuario).add(noticiaMonitorada);
//        } else {
//            return false;
//        }
//        return true;
//    }
//    public boolean removerNoticia(String loginUsuario, Integer codigoNoticia) {
//        if (this.noticiasNaoLidas.get(loginUsuario).contains(codigoNoticia)) {
//            this.noticiasNaoLidas.get(loginUsuario).remove(codigoNoticia);
//        }
//        return true;
//    }
    public ArrayList<Noticia> getNoticiasNaoLidas(String loginUsuario) {
        ArrayList noticiasNaoLidas = new ArrayList<Noticia>();
        Noticia copia;
        for (NoticiaMonitorada noticiaMonitorada : this.noticiasNaoLidas.get(loginUsuario)) {
            if (noticiaMonitorada.status.equals(StatusNoticia.NAO_LIDA)) {
                copia = new Noticia(noticiaMonitorada.noticia);
                copia.lida = false;
                noticiasNaoLidas.add(copia);
            }
        }
        return noticiasNaoLidas;
    }

    public ArrayList<Noticia> getNoticiasNaoRecebidas(String loginUsuario) {
        ArrayList noticiasNaoRecebidas = new ArrayList<Noticia>();
        Noticia copia;

        for (NoticiaMonitorada noticiaMonitorada : this.noticiasNaoLidas.get(loginUsuario)) {
            if (noticiaMonitorada.status.equals(StatusNoticia.NAO_RECEBIDA)) {
                copia = new Noticia(noticiaMonitorada.noticia);
                noticiasNaoRecebidas.add(copia);
            }
        }
        return noticiasNaoRecebidas;
    }

    public ArrayList<Noticia> getNoticiasLidas(String loginUsuario) {
        ArrayList noticiasNaoRecebidas = new ArrayList<Noticia>();
        Noticia copia;

        for (NoticiaMonitorada noticiaMonitorada : this.noticiasNaoLidas.get(loginUsuario)) {
            if (noticiaMonitorada.status.equals(StatusNoticia.LIDA)) {
                copia = new Noticia(noticiaMonitorada.noticia);
                copia.lida = true;
                noticiasNaoRecebidas.add(copia);
            }
        }
        return noticiasNaoRecebidas;
    }

    public ArrayList<Noticia> getNoticiasTodasNoticiasMonitoradas(String loginUsuario) {
        ArrayList noticiasNaoRecebidas = new ArrayList<Noticia>();
        Noticia copia;

        for (NoticiaMonitorada noticiaMonitorada : this.noticiasNaoLidas.get(loginUsuario)) {
            copia = new Noticia(noticiaMonitorada.noticia);
            if (noticiaMonitorada.status.equals(StatusNoticia.LIDA)) {
                copia.lida = true;
            }
            noticiasNaoRecebidas.add(copia);
        }
        return noticiasNaoRecebidas;
    }

    public void adicionarUsuarioParaControle(String loginUsuario) {
        if (!usuarioJaRegistrado(loginUsuario)) {
            this.noticiasNaoLidas.put(loginUsuario, new ArrayList<>());
        }
    }

    private boolean usuarioJaRegistrado(String loginUsuario) {
        return this.noticiasNaoLidas.keySet().contains(loginUsuario);
    }
    
    public boolean moverNaoRecebidaToNaoLida(String loginUsuario, ArrayList<Noticia> noticias) {
        for (Noticia noticia : noticias) {
            moverNaoRecebidaToNaoLida(loginUsuario, noticia);
        }
        return true;
    }
    
    public boolean moverNaoRecebidaToNaoLida(String loginUsuario, Noticia noticia) {
        if (noticia != null) {
            List<NoticiaMonitorada> noticiasUsuario = this.noticiasNaoLidas.get(loginUsuario);
            for (NoticiaMonitorada noticiaMonitorada : noticiasUsuario) {
                if (noticiaMonitorada.noticia.codigo.equals(noticia.codigo) && noticiaMonitorada.status.equals(StatusNoticia.NAO_RECEBIDA)) {
                    noticiaMonitorada.status = StatusNoticia.NAO_LIDA;
                    break;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean marcarComoNaoLida(String loginUsuario, ArrayList<Noticia> noticias) {
        for (Noticia noticia : noticias) {
            marcarComoNaoLida(loginUsuario, noticia);
        }
        return true;
    }

    public boolean marcarComoNaoLida(String loginUsuario, Integer codigoNoticia) {
        Noticia noticia = CadastroNoticias.getInstance().getNoticia(codigoNoticia);
        marcarComoNaoLida(loginUsuario, noticia);
        return true;
    }

    public boolean marcarComoNaoLida(String loginUsuario, Noticia noticia) {
        if (noticia != null) {
            List<NoticiaMonitorada> noticiasUsuario = this.noticiasNaoLidas.get(loginUsuario);
            for (NoticiaMonitorada noticiaMonitorada : noticiasUsuario) {
                if (noticiaMonitorada.noticia.codigo.equals(noticia.codigo)) {
                    noticiaMonitorada.status = StatusNoticia.NAO_LIDA;
                    break;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean marcarComoLida(String loginUsuario, Noticia noticia) {
        if (noticia != null) {
            List<NoticiaMonitorada> noticiasUsuario = this.noticiasNaoLidas.get(loginUsuario);
            for (NoticiaMonitorada noticiaMonitorada : noticiasUsuario) {
                if (noticiaMonitorada.noticia.codigo.equals(noticia.codigo)) {
                    noticiaMonitorada.status = StatusNoticia.LIDA;
                    break;
                }
            }

        } else {
            return false;
        }
        return true;
    }

}
