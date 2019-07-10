package com.intech.test.react;

import java.time.Duration;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import reactor.core.publisher.Flux;

/**
 * In fact - it is a test for Flux with information about long operation progress status
 * I can't imagine how to use junit assert for this logic 
 * 
 * @author a.palkin
 *
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class StreamClosingTest {

	protected static int percentageDone = 0;
	protected static boolean done = false;
	
	private static class TestThread implements Runnable {

		@Override
		public void run() {
			while (percentageDone < 100) {
				try {
					Thread.currentThread().sleep(2500);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				percentageDone += (int)(Math.random() * 10) + 1;
				if (percentageDone > 100) percentageDone = 100;
			}
			done = true;
		}
	}
	
//	@Test
//	public void testFluxEndedOnEvent() {
	public static void main(String... args) {
		percentageDone = 0;
		done = false;
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.submit(new TestThread());
		Stream<Integer> stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<Integer>() {
		    @Override
		    public boolean hasNext() {
		        return !done;
		    }

		    @Override
		    public Integer next() {
		        return percentageDone;
		    }
		}, Spliterator.IMMUTABLE), false);
		
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        Flux<Integer> data = Flux.fromStream(stream);
        Flux.zip(data, interval, (key, value) -> key).subscribe(System.out::println);
        
        executor.shutdown();
	}
	
		
}
