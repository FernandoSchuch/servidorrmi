/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

/**
 * Projeto....: TrabalhoRMIServer Criado em..: 12/10/2017, 12:33:51 Arquivo....:
 * TesteThread.java
 *
 * @author Pablo Oliveira (jntpablo)
 */
public class TesteThread implements Runnable {

    @Override
    public void run() {
        System.out.println("Rodou o teste");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new TesteThread());
        System.out.println(t.isAlive());
        t.start();
        System.out.println(t.isAlive());
        Thread.sleep(1000);
                System.out.println(t.isAlive());

    }

}
