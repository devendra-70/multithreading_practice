import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample2 {

    public static void main(String[] args) {
        int numberOfSubsystems = 4;

        //when all await will happen , this will be executed
        CyclicBarrier barrier = new CyclicBarrier(numberOfSubsystems, new Runnable() {
            @Override
            public void run() {
                System.out.println("All Subsystems up and running. System startup complete");

            }
        });


        Thread webServerThread = new Thread(new SubSystem("Web Server", 2000, barrier));
        Thread databaseThread = new Thread(new SubSystem("Database", 2000, barrier));
        Thread cacheThread = new Thread(new SubSystem("cache", 2000, barrier));
        Thread messagingServiceThread = new Thread(new SubSystem("messaging service", 2000, barrier));

        webServerThread.start();
        databaseThread.start();
        cacheThread.start();
        messagingServiceThread.start();

    }
}


    class SubSystem implements Runnable{
        private String name;
        private int initializationTime;
        private CyclicBarrier barrier;


        //constructor
        public SubSystem(String name,int initializationTime,CyclicBarrier barrier){
            this.name=name;
            this.initializationTime=initializationTime;
            this.barrier=barrier;
        }

        @Override
        public void run(){
            try{
                System.out.println(name + " initialization time");
                Thread.sleep(initializationTime); //simulate
                System.out.println(name + " initialization complete");

                //await
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e){
                e.printStackTrace();
            }
        }
    }

