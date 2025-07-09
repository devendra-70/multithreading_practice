import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class invokeExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);


        //two things in each task
        Callable<Integer> callable1 = ()-> {
            System.out.println("Task 1");
            return 1;
        };
        Callable<Integer> callable2 = ()->{
            System.out.println("Task 2");
            return 2;
        };
        Callable<Integer> callable3 = ()->{
            System.out.println("Task 3");
            return 3;
        };

        List<Callable<Integer>> list = Arrays.asList(callable1,callable2,callable3);

        List<Future<Integer>> futures = executorService.invokeAll(list);

        //get the result of futures with get method
        for(Future<Integer> f: futures){
            System.out.println(f.get());
        }
        executorService.shutdown();


        //executed last as invokeAll waits for execution
        System.out.println("Hello World");
    }
}
