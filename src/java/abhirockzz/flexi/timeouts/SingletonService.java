
package abhirockzz.flexi.timeouts;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.AccessTimeout;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author abhi
 */
@Singleton
@Startup
public class SingletonService {
    private String initTime = null;
    
    @PostConstruct
    public void init(){
        initTime = new Date().toString();
        System.out.println("Init Time saved -- "+ initTime);
    }
    
    @AccessTimeout(value = 5, unit = TimeUnit.SECONDS)
    @Lock(LockType.WRITE)
    
    public String get(){
            try {
                System.out.println(Thread.currentThread().getName() + " will sleep now -- " + new Date());
                Thread.sleep(6000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SingletonService.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
       
        return initTime;     
    }
}
