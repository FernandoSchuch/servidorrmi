/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhormiserver;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author 0055857
 */
public interface Service extends Remote {

    public String getDataHora() throws RemoteException;

    public String invertString(String _valor) throws RemoteException;

    public InOut.OutLogar logar(Usuario user) throws RemoteException;

    public String criar(Usuario user) throws RemoteException;

    public void publicar(Noticia news) throws RemoteException;

    public ArrayList<trabalhormiserver.Topico> getTopicos() throws RemoteException;

    public ArrayList<trabalhormiserver.Topico> getTopicosNaoInscritos(String userLogin) throws RemoteException;

    public ArrayList<trabalhormiserver.Topico> getTopicosInscritos(String userLogin) throws RemoteException;

    public void inscreverUsuario(String userLogin, Integer codTopico) throws RemoteException;

    public void desinscreverUsuario(String userLogin, Integer codTopico) throws RemoteException;

    public ArrayList<Noticia> getNoticiasNaoRecebidas(String userLogin) throws RemoteException;

    public ArrayList<Noticia> getNoticias(String userLogin, trabalhormiserver.Topico topic, String dataIni, String dataFim) throws RemoteException;

    public ArrayList<Noticia> getTodasNoticias(String userLogin) throws RemoteException;

    public Noticia getUltimaNoticia(String userLogin, trabalhormiserver.Topico topic) throws RemoteException;
    
    public void marcarComoLida(Integer codigoNoticia, String userLogin)  throws RemoteException;

}
