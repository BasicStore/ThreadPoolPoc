package entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class AppMain {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Runnable runTask = () -> { // lamda preferred to anonymous inner classes 
			try {
				System.out.println("Blar blar sheep");
				TimeUnit.MILLISECONDS.sleep(3000);
			} catch(InterruptedException e) {
				
			}
		};
		
		Callable callTask = () -> { 
			System.out.println("this task callable class doing something once");						
			TimeUnit.MILLISECONDS.sleep(3000);
			return "Task's execution blar blar";
		};
		
		ExecutorService executor = Executors.newFixedThreadPool(10);
//		List<Callable<String>> callTasks = new ArrayList<>();
//		callTasks.add(callTask);
//		callTasks.add(callTask);
//		callTasks.add(callTask);
		
		System.out.println("ready to start......");
		
		// starts the runnable task going, which is never killed. 
		// It returns VOID, so no means of checking what is going on.
		// executor.execute(runTask);
		
		// assign collection of tasks and runs them. Returns the successful result of 1 class
//		String result = executor.invokeAny(callTasks); // used with Callable interface objects only	  	
//		System.out.println("Result = " + result);
		
		// assign collection of tasks and runs them. Returns the successful result of each task as a list of Futures		
//		List<Future<String>> futures = executor.invokeAll(callTasks);
//		System.out.println("Is Done = " + futures.get(0).isDone());
//		System.out.println("String Rep = " + futures.get(0).toString());
//		if (futures.get(0).isDone()) { // checking the 1st callable has completed, had enough so shut down now 
//			shutdownExecutorService(executor);
//		}
		
		// block the code progressing until task is completed with a future. Bad luck if the task does not return or takes a long time to execute
//		Future<String> future = executor.submit(callTask);
//		String result = null;
//		try {
//			result= future.get(); // the code is blocked here until callTask.call() method has returned a class
//			System.out.println("Result from future = " + result);
//			shutdownExecutorService(executor);
//		} catch(InterruptedException | ExecutionException e) {
//			e.printStackTrace();	
//		}
		
		
//		Future<String> future = executor.submit(callTask);
//		String result = null;
//		try {
//			result= future.get(1000, TimeUnit.MILLISECONDS); // give it time to complete. If it does not, just move on. Supply a low figure for testing
//			if (!future.isDone()) {                          // does not seem to be working!! 
//				future.cancel(true);
//			}
//			if (future.isCancelled()) {
//				System.out.println("Future revoked");
//			}
//			
//			System.out.println("Result from future = " + result);
//			shutdownExecutorService(executor);
//		} catch(TimeoutException e) {
//			
//		} catch(InterruptedException | ExecutionException e) {
//			e.printStackTrace();	
//		}
		
		// SCHEDULING
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		
		// run a task after a delay
		Future<String> resultFuture = 
				  executorService.schedule(callTask, 1, TimeUnit.SECONDS);		  
		
		// run repeatedly every 0.45 seconds, after an initial delay of 0.1 second. Not sure if this future is very helpful, as it runs again and again
		ScheduledFuture<String> resultFuture2 = (ScheduledFuture<String>)executorService.scheduleAtFixedRate(runTask, 100, 450, TimeUnit.MILLISECONDS);
		for (int i = 0; i < 1; i++) {
			Thread.sleep(9000);
		}
		shutdownExecutorService(executorService);
		shutdownExecutorService(executor);
		
		System.out.println("Program terminating");
	}
	
	
	
	
	private static void shutdownExecutorService(ExecutorService executor) throws InterruptedException {
		// attempt to shut down executor service (once existing threads have finished their work)
		System.out.println("Executor Service terminating");
		executor.shutdown();
		
		// if it is still faffing around a second on, force it to shutdown
		if (!executor.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
			executor.shutdownNow();
		}
	}
	
	

}
