package abhirockzz.flexi.timeouts.client;

import abhirockzz.flexi.timeouts.SingletonService;
import abhirockzz.flexi.timeouts.retry.Retry;
import abhirockzz.flexi.timeouts.retry.RetryPolicy;
import javax.ejb.ConcurrentAccessTimeoutException;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author abhi
 */
@Path("test")
public class RESTClient {
    
    @Inject
    SingletonService ssvc;
    
    @GET
    @Interceptors(Retry.class)
    @RetryPolicy
    public String get(){
        
        String result = null;
        try {
            System.out.println(Thread.currentThread().getName() + " will invoke service");
            result = ssvc.get();
        } catch (ConcurrentAccessTimeoutException cate) {
            throw new WebApplicationException(Response.Status.REQUEST_TIMEOUT);
        }
        catch (Exception e) {
            throw new WebApplicationException(501);
        }
        System.out.println(Thread.currentThread().getName() + " returned result ");
        return result + " by thread " +Thread.currentThread().getName();
    }
}
