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
public class InOut implements Serializable{
    
    public static class OutLogar implements Serializable{
        public Usuario user;
        public Boolean sucesso;
        public String erro;
    }
    
}
