/**
 * Created by ruslan on 11.10.16.
 */
interface Lighthouse extends Unit, Runnable {

    void decrement();

    boolean isTimedOut();
}
