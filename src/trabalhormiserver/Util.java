/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhormiserver;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author asuspc
 */
public class Util {
    
    private static Icon mailIcon;
    private static Icon emptyIcon;
    
    public static Icon getMailIcon(){
        if (mailIcon == null){
            mailIcon = new ImageIcon(Util.class.getResource("/imagens/mail.png"));
        }
        return mailIcon;
    }
    
    public static Icon getEmptyIcon(){
        if (emptyIcon == null){
            emptyIcon = new ImageIcon();
        }
        return emptyIcon;
    }
    
    public static void MensagemErro(Component owner, String msg){
        JOptionPane.showMessageDialog(owner, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
