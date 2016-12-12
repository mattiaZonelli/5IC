/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emsrescue;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 * @author matteo
 */
class TaskExecutionServer {
    private static final int NTHREADS = 100;
    /* use the factory method
     * 
     * Creates a thread pool that reuses a fixed number of threads 
     * operating off a shared unbounded queue
     */ 
    private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(9999);
        while (true) {
            final Socket connection = socket.accept();
            // task is a runnable object 
            Runnable task = new Runnable() {
                public void run() {
                    //handleRequest(connection);
                }
            };
            exec.execute(task);
        }
    }
}

// in order to obtai one thread for one task

/*public class ThreadPerTaskExecutor implements Executor {
    public void execute(Runnable r) {
        new Thread(r).start();
    };
}
*/