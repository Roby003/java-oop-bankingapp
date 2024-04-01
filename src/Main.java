import main.models.BankingApp;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        var App=new BankingApp();
        App.addCustomer("john doe","0756185860","mail@mail.ro");
        App.start();

        {
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            Runnable task = () -> System.out.println("Task executed at fixed rate");
            executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.DAYS); // Execute the task every second
        }
    }
}