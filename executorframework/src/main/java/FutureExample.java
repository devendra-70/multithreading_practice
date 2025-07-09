import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureExample {
    public static void main(String[] args)throws ExecutionException, InterruptedException{
        ExecutorService executorService= Executors.newSingleThreadExecutor();

        //runnable method returns nothing , callable method can return something
        Future<?> future = executorService.submit(()-> "Hello"); //using callable under hood
        System.out.println(future.get()); // get method waits for computatuion
        executorService.shutdown();
    }
}
