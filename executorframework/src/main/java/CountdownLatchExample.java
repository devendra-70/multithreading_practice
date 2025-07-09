import java.util.concurrent.*;

public class CountdownLatchExample {
    public static void main(String[] args) throws ExecutionException,InterruptedException {
        //use countdownlatch to wait main thread for multiple threads

        int numberOfServices =3;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfServices);

        //no. of operations threads
        CountDownLatch latch = new CountDownLatch(numberOfServices);

        //call actually
        executorService.submit(new DependentService(latch));
        executorService.submit(new DependentService(latch));
        executorService.submit(new DependentService(latch));

        latch.await();

        System.out.println("Main");
        executorService.shutdown();

    }


}

class DependentService implements Callable<String>{

    //implemet latch
    private final CountDownLatch latch;

    public DependentService(CountDownLatch latch){
        this.latch=latch;
    }

    //actual work
    @Override
    public String call() throws Exception{
        try {
            System.out.println(Thread.currentThread().getName() + "service started.");
            Thread.sleep(2000);
        }finally {

            //decrease countdown
            latch.countDown();
        }
        return "ok";
    }
}
