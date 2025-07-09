import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;


public class ExecutorExample {
    public static void main(String[] args){

        //ex1:factorial synchrnouly
        long startTime=System.currentTimeMillis();
        for(int i=1;i<10;i++){
            System.out.println(factorial(i));
        }
        System.out.println("Total time:"+(System.currentTimeMillis()-startTime));




        //ex2: use multithreading , no thread reuseing
        long startTime1=System.currentTimeMillis();
        //array of threads
        Thread[] threads=new Thread[9];
        for(int i=1;i<10;i++){
            int finalI=i;
             threads[i-1] = new Thread(
                    () ->{
                        long result = factorial(finalI);
                        System.out.println(result);
                    }
            );
            threads[i-1].start();
        }

        /*we have to join all threads to main before executing below statement
        to get correct timing.*/

        for(Thread thread:threads){
            try{
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Total time with threads:"+(System.currentTimeMillis()-startTime1));


        //ex3: executerService , bussiness logic in submit method
        long startTime2=System.currentTimeMillis();
        ExecutorService executor= Executors.newFixedThreadPool(9);
        for(int i=1;i<10;i++){
            int finalI=i;
            executor.submit(() ->{
                long result = factorial(finalI);
                System.out.println(result);
            });
        }

        //stop manually with shutdown method
        executor.shutdown();
        try{
            executor.awaitTermination(100, TimeUnit.SECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        System.out.println("Total time with threads:"+(System.currentTimeMillis()-startTime2));




    }

    //factorial method
    private static long factorial(int n){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long result=1;
        for(int i=1;i<=n;i++){
            result*=i;
        }
        return result;
    }
}
