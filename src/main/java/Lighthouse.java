import java.util.concurrent.Future;

/**
 * Created by ruslan on 11.10.16.
 */
interface Lighthouse extends Unit, Runnable, Future<String> {

    void decrement();

    boolean isTimedOut();
}
