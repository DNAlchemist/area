import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import java.lang.invoke.MethodHandles;

/**
 * Created by ruslan on 11.10.16.
 */
public class Rand {

    private static Logger logger = LoggerFactory.getLogger( MethodHandles.lookup().lookupClass() );

    private static Random random;
    private static long seed;

    static {
        seed = System.currentTimeMillis();
        logger.info( "Using pseudo-random with seed " + seed );
        random = new Random( seed );
    }

    public static int uniform( int n ) {
        if ( n <= 0 ) throw new IllegalArgumentException( "Parameter N must be positive" );
        return random.nextInt( n );
    }
}
