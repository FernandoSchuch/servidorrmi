package trabalhormiserver;

import java.io.Serializable;
import java.util.ArrayList;

public class Topico implements Serializable{
    
    public Integer codigo;
    public String nome;
    public Boolean selecionado;

    public Topico(String nome) {
        this.nome = nome;
    }

    public Topico(Integer codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }
    

    
}
