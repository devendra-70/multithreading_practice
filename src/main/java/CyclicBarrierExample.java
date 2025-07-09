import java.util.concurrent.*;

public class CyclicBarrierExample {
    public static void main(String[] args) throws ExecutionException,InterruptedException {
        //use cyclicbarrier to wait the last thread to finish execution

        int numberOfServices =3;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfServices);

        //no. of operations threads
        CyclicBarrier barrier = new CyclicBarrier(numberOfServices);
        //call actually
        executorService.submit(new BarrierService(barrier));
        executorService.submit(new BarrierService(barrier));
        executorService.submit(new BarrierService(barrier));



        //cyclic barrier doesnt wait for Main thread
        System.out.println("Main");
        executorService.shutdown();

        //reset barrier
        barrier.reset();

        barrier.getParties();

    }


}

class BarrierService implements Callable<String>{

    //implemet barrier
    private final CyclicBarrier barrier;

    public BarrierService(CyclicBarrier barrier){
        this.barrier=barrier;
    }

    //actual work
    @Override
    public String call() throws Exception{
            System.out.println(Thread.currentThread().getName() + "service started.");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "is waiting at the barrier");

            //this thread finishes execution
            barrier.await();

        return "ok";
    }
}
