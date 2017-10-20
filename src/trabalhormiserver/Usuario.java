/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhormiserver;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author asuspc
 */
public class Usuario implements Serializable{
    
    public String login;
    public String senha;
    public String ip;
    public Integer porta;

    public Usuario(String login, String senha, String ip, Integer porta){
        this.login = login;
        this.senha = senha;
        this.ip    = ip;
        this.porta = porta;
    }
}
