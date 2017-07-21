/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package douyu;

import java.util.Date;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yansunkang
 */
public class MyTimerTask extends TimerTask {

    private Douyu douyu;

    public MyTimerTask(Douyu douyu) {
        this.douyu = douyu;
    }

    @Override
    public void run() {
        try {
            Date date = new Date(this.scheduledExecutionTime());
            douyu.run();
            System.out.println("执行时间" + date);
        } catch (Exception ex) {
            Logger.getLogger(MyTimerTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
