/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package douyu;

import java.util.Timer;

/**
 *
 * @author yansunkang
 */
public class Main {
    public static void main(String[] args){
        Douyu douyu = new Douyu();
        Timer timer = new Timer();
        timer.schedule(new MyTimerTask(douyu),1000,3000);
        
    }
}
