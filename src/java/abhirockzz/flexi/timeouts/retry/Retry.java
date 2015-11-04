
package abhirockzz.flexi.timeouts.retry;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 *
 * @author abhi
 */
public class Retry {

    @AroundInvoke
    public Object interceptAndRetryIfNeeded(final InvocationContext ic) {
        
        Object result = null;
        int retryThreshold;
        RetryPolicy policy = ic.getMethod().getAnnotation(RetryPolicy.class);
        if(policy != null){
            retryThreshold = policy.threshold();
        }else{
            throw new IllegalStateException(String.format("Method '%s' from class '%s' should be annotated with @RetryPolicy",
                            ic.getMethod().getName(), ic.getTarget().getClass().getName()));
        }
        boolean failed = true;
        int retryCount = 0;
        
        do {
                        
            try {
                if(retryCount > 0){
                    System.out.println(String.format("Method '%s' from class '%s' is being retried by thread %s",
                            ic.getMethod().getName(), ic.getTarget().getClass().getName() ,Thread.currentThread().getName()));
                }
                result = ic.proceed();
                failed = false;
            } catch (Exception e) {
                System.err.println("exception encountered -- " + e.getMessage());
                retryCount++;
            }

        } while (failed && retryCount <= retryThreshold);

        return result;
    }
}
