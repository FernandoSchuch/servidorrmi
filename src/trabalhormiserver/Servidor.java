package trabalhormiserver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class Servidor extends UnicastRemoteObject implements Service {

    public static String IP;
    public static String PORTA;
    public static int NUMERO_MAXIMO_NOTICIAS_PUBLICADAS;

    public Servidor() throws RemoteException {
        super();
        carregarConfiguracoes();
        GerenciadorLog.getInstance().printLog("Iniciando servidor...");
    }

    public static void main(String[] args) {
        try {
            Servidor _servidor = new Servidor();
            String _local = "//" + _servidor.IP + ":" + _servidor.PORTA + "/service";
            Naming.rebind(_local, _servidor);
            GerenciadorLog.getInstance().printLogSucesso("Servidor iniciado!");
        } catch (RemoteException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (MalformedURLException ex) {
            System.err.println("Erro URL: " + ex.getMessage());
        }
    }

    @Override
    public InOut.OutLogar logar(Usuario user) {
        GerenciadorLog.getInstance().printLog("Usuario " + user.login + " solicitando autenticação.");
        InOut.OutLogar out = new InOut.OutLogar();
        if (!CadastroUsuario.getInstance().isUsuarioExistente(user.login)) {
            out.sucesso = false;
            out.erro = "Usuario inexistente";
            GerenciadorLog.getInstance().printLogFalha("Usuario " + user.login + " não cadastrado.");
            return out;
        }

        Usuario usuario = CadastroUsuario.getInstance().getUsuario(user.login);

        if (!usuario.senha.equals(user.senha)) {
            out.sucesso = false;
            out.erro = "Senha invalida";
            GerenciadorLog.getInstance().printLogFalha("Usuario " + user.login + " informou uma senha inválida.");
            return out;
        }
        GerenciadorLog.getInstance().printLog("Atualizando dados do usuario " + user.login);
        CadastroUsuario.getInstance().atualizarEnderecoUsuario(user);
        out.sucesso = true;
        out.user = CadastroUsuario.getInstance().getUsuario(user.login);
        GerenciadorLog.getInstance().printLogSucesso("Usuario " + user.login + " autenticado com sucesso.");
        return out;
    }

    @Override
    public String criar(Usuario user) {
        GerenciadorLog.getInstance().printLog("Usuario " + user.login + " solicitou cadastro.");
        if (CadastroUsuario.getInstance().isUsuarioExistente(user.login)) {
            GerenciadorLog.getInstance().printLogFalha("Usuario " + user.login + " já cadastrado.");
            return "Usuário já cadastrado";
        }
        CadastroUsuario.getInstance().cadastrarUsuario(user);
        GerenciadorNoticiasLidasRecebidas.getInstance().adicionarUsuarioParaControle(user.login);
        GerenciadorNoticiasLidasRecebidas.getInstance().iniciarMonitoramentoNoticiaPorUsuario(user.login, CadastroNoticias.getInstance().getTodasNoticias());
        GerenciadorLog.getInstance().printLogSucesso("Usuario " + user.login + " cadastrado com sucesso");
        return "";
    }

    @Override
    public void publicar(Noticia news) {
        GerenciadorLog.getInstance().printLog("Escritor " + news.publicador.login + " enviou uma noticia.");

        //Cadastrar tópico caso seja um tópico novo
        if (news.topico.codigo == null) {
            if (news.topico.nome == null || news.topico.nome.isEmpty()) {
                return;
            }
            String descricaoTopico = news.topico.nome;
            Integer codigoTopico = CadastroTopico.getInstance().adicionarTopico(descricaoTopico);
            news.topico = CadastroTopico.getInstance().getTopico(codigoTopico);
            GerenciadorLog.getInstance().printLogSucesso("Tópico " + news.topico.nome + " criado com sucesso!");
        }

        //Cadastrar noticia
        news = CadastroNoticias.getInstance().cadastrarNoticia(news);
        GerenciadorLog.getInstance().printLogSucesso("Noticia " + news.titulo + " registrada com sucesso!");

        //Marcar como nao recebida para usuarios seguidores
        GerenciadorNoticiasLidasRecebidas.getInstance().iniciarMonitoramentoNoticiaPorUsuario(news);
        GerenciadorLog.getInstance().printLogSucesso("Noticia " + news.titulo + " com monitoramento ativo!");

        //NotificarUsuariosNaoLidas
        GerenciadorLog.getInstance().printLog("Inciando notificação dos leitor inscritos no tópico " + news.topico.nome);
        List<String> usuariosInscritosNesteTopico = GerenciadorInscricoes.getInstance().getUsuariosInscritos(news.topico.codigo);
        GerenciadorNotificacoes.notificarUsuario(usuariosInscritosNesteTopico, news);
        GerenciadorLog.getInstance().printLog("Finalizando notificação dos leitor inscritos no tópico " + news.topico.nome);
    }

    @Override
    public ArrayList<Topico> getTopicosNaoInscritos(String userLogin) {
        GerenciadorLog.getInstance().printLog("Enviando todos os tópicos em que o usuário " + userLogin + " NÃO está inscrito.");
        return GerenciadorInscricoes.getInstance().getTopicosNaoInscritos(userLogin);
    }

    @Override
    public ArrayList<Topico> getTopicosInscritos(String userLogin) {
        GerenciadorLog.getInstance().printLog("Enviando todos os tópicos em que o usuário " + userLogin + " ESTÁ inscrito.");
        return GerenciadorInscricoes.getInstance().getTopicosInscritos(userLogin);
    }

    @Override
    public void inscreverUsuario(String userLogin, Integer codTopico) {
        GerenciadorLog.getInstance().printLog("Inscrevendo o usuário " + userLogin + " ao tópico de código " + codTopico);
        GerenciadorInscricoes.getInstance().adicionarInscricao(codTopico, userLogin);
    }

    @Override
    public void desinscreverUsuario(String userLogin, Integer codTopico) {
        GerenciadorLog.getInstance().printLog("Desinscrevendo o usuário " + userLogin + " ao tópico de código " + codTopico);
        GerenciadorInscricoes.getInstance().removerInscricao(codTopico, userLogin);
        Topico topico = CadastroTopico.getInstance().getTopico(codTopico);
        ArrayList<Noticia> noticias = CadastroNoticias.getInstance().getTodasNoticiasTopico(topico);
        GerenciadorNoticiasLidasRecebidas.getInstance().moverNaoRecebidaToNaoLida(userLogin, noticias);
    }

    @Override
    public ArrayList<Noticia> getNoticiasNaoRecebidas(String userLogin) {
        GerenciadorLog.getInstance().printLog("Enviando notícias que o usuário " + userLogin + " ainda não recebeu");
        GerenciadorLog.getInstance().printLog("Marcando notícias como recebidas para o usuário " + userLogin);
        ArrayList<Noticia> noticiasNaoRecebidas = GerenciadorNoticiasLidasRecebidas.getInstance().getNoticiasNaoRecebidas(userLogin);
        GerenciadorNoticiasLidasRecebidas.getInstance().moverNaoRecebidaToNaoLida(userLogin, noticiasNaoRecebidas);
        Collections.reverse(noticiasNaoRecebidas);
        return noticiasNaoRecebidas;
    }

    @Override
    public ArrayList<Noticia> getNoticias(String userLogin, Topico topic, String dataIni, String dataFim) {
        GerenciadorLog.getInstance().printLog("Enviando notícias de " + topic.nome + " que foram publicadas entre " + dataIni + " e " + dataFim + " para o usuário " + userLogin);
        ArrayList<Noticia> noticiasFiltradas = CadastroNoticias.getInstance().getNoticias(topic, dataIni, dataFim);
        if (userLogin.equalsIgnoreCase("visitante")) {
            return noticiasFiltradas;
        }
        ArrayList<Noticia> noticiasNaoLidasNaoRecebidas = GerenciadorNoticiasLidasRecebidas.getInstance().getNoticiasTodasNoticiasMonitoradas(userLogin);
        ArrayList<Noticia> noticiasMarcadas = blendNoticias(noticiasFiltradas, noticiasNaoLidasNaoRecebidas);
        Collections.reverse(noticiasMarcadas);

        return noticiasMarcadas;
    }

    @Override
    public ArrayList<Noticia> getTodasNoticias(String userLogin) {
        GerenciadorLog.getInstance().printLog("Enviando todas as notícias.");
        ArrayList<Noticia> noticiasFiltradas = CadastroNoticias.getInstance().getTodasNoticias();
        ArrayList<Noticia> noticiasNaoLidasNaoRecebidas = GerenciadorNoticiasLidasRecebidas.getInstance().getNoticiasTodasNoticiasMonitoradas(userLogin);
        ArrayList<Noticia> noticiasMarcadas = blendNoticias(noticiasFiltradas, noticiasNaoLidasNaoRecebidas);
        Collections.reverse(noticiasMarcadas);
        return noticiasMarcadas;
    }

    @Override
    public Noticia getUltimaNoticia(String userLogin, Topico topic) {
        GerenciadorLog.getInstance().printLog("Enviando a última notícia de " + topic.nome + " para o usuário " + userLogin);
        Noticia ultimaNoticia = CadastroNoticias.getInstance().getUltimaNoticia(topic);
        if (userLogin.equalsIgnoreCase("visitante")) {
            ultimaNoticia.lida = false;
            return ultimaNoticia;
        }
        Noticia noticiaControlada = GerenciadorNoticiasLidasRecebidas.getInstance().getNoticia(userLogin, ultimaNoticia.codigo);
        return noticiaControlada;
    }

    //OK
    @Override
    public ArrayList<Topico> getTopicos() {
        GerenciadorLog.getInstance().printLog("Enviando todos os topicos.");
        return CadastroTopico.getInstance().getTodosTopicos();
    }

    @Override
    public String getDataHora() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String invertString(String _valor) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void marcarComoLida(Integer codigoNoticia, String userLogin) throws RemoteException {
        GerenciadorLog.getInstance().printLog("Marcando notícia de código " + codigoNoticia + " como lida para o usuário.");
        Noticia noticia = CadastroNoticias.getInstance().getNoticia(codigoNoticia);
        GerenciadorNoticiasLidasRecebidas.getInstance().marcarComoLida(userLogin, noticia);
    }

    public ArrayList<Noticia> blendNoticias(ArrayList<Noticia> noticiasFiltradas, ArrayList<Noticia> noticiasNaoLidasNaoRecebidas) {
        ArrayList<Noticia> noticiasMarcadas = new ArrayList<Noticia>();
        for (Noticia noticia : noticiasFiltradas) {
            for (Noticia noticiaNaoLidaOuNaoRecebida : noticiasNaoLidasNaoRecebidas) {
                if (noticia.codigo.equals(noticiaNaoLidaOuNaoRecebida.codigo)) {
                    noticiasMarcadas.add(new Noticia(noticiaNaoLidaOuNaoRecebida));
                    break;
                }
            }
        }
        return noticiasMarcadas;
    }

    private void carregarConfiguracoes() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(System.getProperty("user.dir") + "/config.properties");
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            this.IP = prop.getProperty("ip");
            this.PORTA = prop.getProperty("porta");
            this.NUMERO_MAXIMO_NOTICIAS_PUBLICADAS = Integer.valueOf(prop.getProperty("numero_maximo_noticias_cadastradas"));

            if (prop.getProperty("ativar_logs").equalsIgnoreCase("S")) {
                GerenciadorLog.getInstance().ativarLogs();
            }
            GerenciadorLog.getInstance().printLogSucesso("Configurações carregadas com sucesso!");
            GerenciadorLog.getInstance().printLogSucesso("Logs habilitados com sucesso!");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(0);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        }
    }
}
