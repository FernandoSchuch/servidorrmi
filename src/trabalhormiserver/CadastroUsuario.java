/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhormiserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Projeto....: TrabalhoRMIServer Criado em..: 25/09/2017, 20:40:14 Arquivo....:
 * CadastroUsuario.java
 *
 * @author Pablo Oliveira (jntpablo)
 */
public class CadastroUsuario {

    private final Map<String, Usuario> usuariosCadastrados = new HashMap();
    private static CadastroUsuario self = null;

    private CadastroUsuario() {
        Usuario leitor = new Leitor("leitor", "leitor", null, 10000);
        Usuario escritor = new Escritor("escritor", "escritor", null, 0);
        this.usuariosCadastrados.put(leitor.login, leitor);
        this.usuariosCadastrados.put(escritor.login, escritor);
        GerenciadorNoticiasLidasRecebidas.getInstance().adicionarUsuarioParaControle(leitor.login);
        GerenciadorNoticiasLidasRecebidas.getInstance().adicionarUsuarioParaControle(escritor.login);
    }

    public static CadastroUsuario getInstance() {
        if (self == null) {
            self = new CadastroUsuario();
        }
        return self;
    }

    public boolean isUsuarioExistente(String login) {
        return this.usuariosCadastrados.keySet().contains(login);
    }

    public boolean cadastrarUsuario(Usuario usuario) {
        if (isUsuarioExistente(usuario.login)) {
            return false;
        }

        this.usuariosCadastrados.put(usuario.login, usuario);
        return true;
    }

    public Usuario getUsuario(String login) {
        return this.usuariosCadastrados.get(login);
    }
    
    public void atualizarEnderecoUsuario(Usuario usuario) {
        if (usuario != null && isUsuarioExistente(usuario.login)) {
            Usuario usuarioCadastrado = getUsuario(usuario.login);
            usuarioCadastrado.ip = usuario.ip;
            usuarioCadastrado.porta = usuario.porta;
        }
    }
}
