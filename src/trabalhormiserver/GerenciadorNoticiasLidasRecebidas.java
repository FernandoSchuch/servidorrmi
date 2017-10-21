package trabalhormiserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
    private final Map<String, List<NoticiaMonitorada>> noticiasPorUsuario = new HashMap();
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
                    noticiaMonitorada = new NoticiaMonitorada(StatusNoticia.AGUARDANDO_ENVIO, noticia);
                } else {
                    noticiaMonitorada = new NoticiaMonitorada(StatusNoticia.NAO_LIDA, noticia);
                }
                this.noticiasPorUsuario.get(loginUsuario).add(noticiaMonitorada);
            } else {
                for (String usuario : this.noticiasPorUsuario.keySet()) {
                    if (usuariosInscritosNesteTopico.contains(usuario)) {
                        noticiaMonitorada = new NoticiaMonitorada(StatusNoticia.AGUARDANDO_ENVIO, noticia);
                    } else {
                        noticiaMonitorada = new NoticiaMonitorada(StatusNoticia.NAO_LIDA, noticia);
                    }
                    this.noticiasPorUsuario.get(usuario).add(noticiaMonitorada);
                }
            }
        }

    }

    public void iniciarMonitoramentoNoticiaPorUsuario(Noticia noticia) {
        ArrayList<Noticia> noticias = new ArrayList<Noticia>();
        noticias.add(noticia);
        iniciarMonitoramentoNoticiaPorUsuario(null, noticias);
    }

    public Noticia getNoticia(String loginUsuario, Integer codigoNoticia) {
        Noticia copia = null;
        for (NoticiaMonitorada noticia : this.noticiasPorUsuario.get(loginUsuario)) {
            if (noticia.noticia.codigo.compareTo(codigoNoticia) == 0) {
                copia = new Noticia(noticia.noticia);
                if (noticia.status.equals(StatusNoticia.LIDA)) {
                    copia.lida = true;
                }
            }
        }
        return copia;
    }

    public ArrayList<Noticia> getNoticiasNaoLidas(String loginUsuario) {
        ArrayList noticiasNaoLidas = new ArrayList<Noticia>();
        Noticia copia;
        for (NoticiaMonitorada noticiaMonitorada : this.noticiasPorUsuario.get(loginUsuario)) {
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

        for (NoticiaMonitorada noticiaMonitorada : this.noticiasPorUsuario.get(loginUsuario)) {
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

        for (NoticiaMonitorada noticiaMonitorada : this.noticiasPorUsuario.get(loginUsuario)) {
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

        for (NoticiaMonitorada noticiaMonitorada : this.noticiasPorUsuario.get(loginUsuario)) {
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
            this.noticiasPorUsuario.put(loginUsuario, new ArrayList<>());
        }
    }

    private boolean usuarioJaRegistrado(String loginUsuario) {
        return this.noticiasPorUsuario.keySet().contains(loginUsuario);
    }

    public boolean moverNaoRecebidaToNaoLida(String loginUsuario, ArrayList<Noticia> noticias) {
        for (Noticia noticia : noticias) {
            moverNaoRecebidaToNaoLida(loginUsuario, noticia);
        }
        return true;
    }

    public boolean moverNaoRecebidaToNaoLida(String loginUsuario, Noticia noticia) {
        if (noticia != null) {
            List<NoticiaMonitorada> noticiasUsuario = this.noticiasPorUsuario.get(loginUsuario);
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
            List<NoticiaMonitorada> noticiasUsuario = this.noticiasPorUsuario.get(loginUsuario);
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

    public boolean marcarComoNaoRecebida(String loginUsuario, Noticia noticia) {
        if (noticia != null) {
            List<NoticiaMonitorada> noticiasUsuario = this.noticiasPorUsuario.get(loginUsuario);
            for (NoticiaMonitorada noticiaMonitorada : noticiasUsuario) {
                if (noticiaMonitorada.noticia.codigo.equals(noticia.codigo)) {
                    noticiaMonitorada.status = StatusNoticia.NAO_RECEBIDA;
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
            List<NoticiaMonitorada> noticiasUsuario = this.noticiasPorUsuario.get(loginUsuario);
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

    public boolean removerNoticiaDoMonitoramento(Noticia noticia) {
        if (noticia != null) {
            for (String loginUsuario : this.noticiasPorUsuario.keySet()) {
                int indiceNoticiaAExcluir = -1;
                List<NoticiaMonitorada> noticiasUsuario = this.noticiasPorUsuario.get(loginUsuario);
                for (int i = 0; i < noticiasUsuario.size(); i++) {
                    NoticiaMonitorada noticiaMonitorada = noticiasUsuario.get(i);
                    if (noticiaMonitorada.noticia.codigo.compareTo(noticia.codigo) == 0) {
                        indiceNoticiaAExcluir = i;
                        break;
                    }
                }
                if (indiceNoticiaAExcluir >= 0) {
                    this.noticiasPorUsuario.get(loginUsuario).remove(indiceNoticiaAExcluir);
                }
            }
        } else {
            return false;
        }
        return true;
    }

}
