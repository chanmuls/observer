package net.devotopia.observer.ex02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureEx4 {

    private static final Logger logger = LoggerFactory.getLogger(FutureEx4.class.getName());

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 내가 원하는 쓰레드풀이 있다면 쓰레드를 새로 만들어서 사용을 하면되는데
        // 쓰레드를 새로 만들고 폐기하는 과정이 큰 비용이다. 큰 비용이라는 것은 cpu를 많이 차지 한다는 것이다. 메모리도 사용한다.
        // 만약 내가 쓰레드를 1000번을 사용할때 동시에 쓰레드를 10개 미만으로 사용한다고 하면 10개 짜리 쓰레드풀을 만들어 놓고
        // 10개 짜리 쓰레드를 100번 사용하고 사용이 끝나면 그 쓰레드를 날리지 않고 반납을 한다. 오브젝트 그대로. 그다음에
        // 작업이 이어받아 쓰레드를 사용한다. 이런식으로 쓰레드를 재활용해서 비용이 많이 들어가는 작업들을 최소화 한다.
        // newCachedThreadPool은 기본적으로 max제한이 없고 쓰레드를 미리 만들지도 않는다. 요청이 들어 올때 생성을 하고
        // 반납하면 캐싱되어 있던 쓰레드를 다음 작업이 재활용한다.

        ExecutorService executorService = Executors.newCachedThreadPool();

        // submit은 Runnable의 return 값을 설정하거나 Callable을 받는다
        // Runnable, Callable은 자바에서 가장 기본이 되는 인터페이스이다.
        // Runnable과 Callable 차이점
        // return X   return O
        // exeption X exeption O
        // Callable은 예외가 발생했을때 내부에서 처리하지 않아도 된다.


//        executorService.submit(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                Thread.sleep(2000);
//                logger.debug("Async");
//                return "hello";
//            }
//        });

        FutureTask<String> task = new FutureTask<String>(() -> {
            Thread.sleep(2000);
            logger.debug("Async");
            return "hello";
        }){
            @Override
            protected void done() {
                try {
                    logger.debug(get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };

        executorService.execute(task);
        executorService.shutdown();
        logger.debug("Exit");
    }
}
