/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhormiserver;

import java.io.Serializable;

/**
 *
 * @author asuspc
 */
public class Leitor extends Usuario implements Serializable{
    
    public Leitor(String login, String senha, String ip, Integer porta){
        super(login, senha, ip, porta);        
    }
    
}
