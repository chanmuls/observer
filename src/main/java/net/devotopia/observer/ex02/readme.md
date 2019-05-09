1. reactive streams
기본적인 io 감잡기

2. 리엑티브 api를 몇가지로 구성하면 reactive? 이렇게 생각하면 굉장히 큰 범위이다.

3. 리엑티브는 단순히 옵저버블 패턴으로 두개의 오브젝트가 
한쪽에서는 이벤트를 발생하고 한쪽에서는 받아만 보는 패턴만 리엑티브가 아니고 요즘에 많이 요구가 되는 비동기 적인
동작 환경 프로그래밍 환경에서 어떤의미를 갖는기 살펴보기.

4. 그렇다면 뭐하러 스프링이 기존 시스템을 버리고 리엑티브 웹을 전면에 내세우고 엔진까지 바꿔가면서 다른 방향으로의
접근을 시도하는가 어떤 문제를 플려고 하는가.
- 자바 1.4 ~ 1.5 컨커런트 팩키지 새로운 비동기 프로그래밍 기법들
- 스프링 2.0 자바 엔터프라이즈 환경에서 필요로 하는 비동기 작업을 어떻게 녹여낼 것인가?

```
 * {@code Future}은(는) 비동기적으로 수행하고 난 결과를 나타낸다.
 * - 단순히 설명하면 현재 작업을 수행하고 있는 쓰레드가 아닌 새로운 쓰레드에서 별개의 작업을 하나 수행을 시킨다.
 * - 그리고 그 쓰레드에서 작업한 결과를 가져오는 인터페이스가 Future임
 * 연산이 완료되었는지 확인하고, 완료를 기다리며, 연산의 결과를 회수하는 방법이 제공된다.
 * 결과는 방법을 통해서만 검색할 수 있다.
 * 계산이 완료되면 {@code get}(필요한 경우 준비될 때까지 차단)  
 * 취소는 {@code 취소} 방법으로 수행된다.  
 * 작업이 정상적으로 완료되었는지 또는 취소되었는지 판단하는 추가 방법이 제공된다. 
 * 연산이 완료되면 연산을 취소할 수 없다.
 * 취소 가능성을 위해 {@code Future}을(를) 사용하려면 
 * {@code Future <> 형식의 유형을 선언하십시오.>} 및 기본 작업의 결과로 {@code null}을(를) 반환하십시오.
  
 * A {@code Future} represents the result of an asynchronous
 * computation.  Methods are provided to check if the computation is
 * complete, to wait for its completion, and to retrieve the result of
 * the computation.  The result can only be retrieved using method
 * {@code get} when the computation has completed, blocking if
 * necessary until it is ready.  Cancellation is performed by the
 * {@code cancel} method.  Additional methods are provided to
 * determine if the task completed normally or was cancelled. Once a
 * computation has completed, the computation cannot be cancelled.
 * If you would like to use a {@code Future} for the sake
 * of cancellability but not provide a usable result, you can
 * declare types of the form {@code Future<?>} and
 * return {@code null} as a result of the underlying task.

 * @see FutureTask
 * @see Executor
 * @since 1.5
 * @author Doug Lea
 * @param <V> The result type returned by this Future's {@code get} method
 * Other lang name: Promise, deferred, result
 */
public interface Future<V> {
```

 