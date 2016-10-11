import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Created by ruslan on 11.10.16.
 */
public class LighthouseImpl implements Lighthouse {

    private static Logger logger = LoggerFactory.getLogger( MethodHandles.lookup().lookupClass() );

    private final String id;
    private final Runnable callback;
    private int ticks;
    private double r, x, y;

    public LighthouseImpl( String id, double x, double y, double radius, Runnable callback, int ticks ) {
        this.id = id;
        this.r = radius;
        this.x = x;
        this.ticks = ticks;
        this.y = y;
        this.callback = callback;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getR() {
        return r;
    }

    @Override
    public void setX( double x ) {
        this.x = x;
    }

    @Override
    public void setY( double y ) {
        this.y = y;
    }

    @Override
    public void setR( double r ) {
        this.r = r;
    }

    @Override
    public void run() {
        logger.info( "Invoking callback [ID = " + id + "]" );
        callback.run();
    }

    @Override
    public String toString() {
        return "Lighthouse( id = " + id + ", x = " + x + ", y = " + y + " )";
    }

    @Override
    public void decrement() {
        if( ticks < 0 ) throw new IllegalStateException( String.valueOf( ticks ) );
        ticks--;
    }

    @Override
    public boolean isTimedOut() {
        return ticks == 0;
    }
}
