import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by ruslan on 10.10.16.
 */
public class MovableActions {

    private static Logger logger = LoggerFactory.getLogger( MethodHandles.lookup().lookupClass() );

    public static boolean isCollide( Unit m, Unit m2 ) {
        return Math.sqrt( Math.pow( m2.getX() - m.getX(), 2 ) + Math.pow( m2.getY() - m.getY(), 2 ) ) < m.getR() + m2.getR();
    }

    public static void collide( Collection<MovableUnit> movable ) {
        HashSet<MovableUnit> copyMovable = new HashSet<>( movable );
        new HashSet<>( movable ).stream().filter( MovableUnit::isActive ).forEach( m -> {
            copyMovable.stream()
                    .filter( MovableUnit::isActive )
                    .filter( unit -> unit != m )
                    .filter( m::isCollide )
                    .forEach( m2 -> {

                        logger.trace( "Collide " + m + " and " + m2 );
                        double sx = m.getX() - m2.getX();
                        double sy = m.getY() - m2.getY();
                        double sxynorm = Math.sqrt( sx * sx + sy * sy );
                        double sxn = sx / sxynorm;
                        double syn = sy / sxynorm;
                        double pn = ( m.getDx() ) * sxn + ( m.getDy() ) * syn;
                        double px = 2 * sxn * pn;
                        double py = 2 * syn * pn;

                        m.setDx( m.getDx() - px );
                        m.setDy( m.getDy() - py );
            } );
        } );
    }

    public static void collideBorder( Collection<MovableUnit> movable, int height, int width ) {
        movable.stream().filter( MovableUnit::isActive ).forEach( person -> {
            logger.trace( "Collide border: " + movable );
            double x = person.getX();
            double dx = person.getDx();
            double y = person.getY();
            double dy = person.getDy();
            double r = person.getR();

            if( x <= r && dx < 0 ) person.setDx( dx * -1 );
            if( x >= width - r && dx > 0 ) person.setDx( dx * -1 );
            if( y <= r && dy < 0 ) person.setDy( dy * -1 );
            if( y >= height - r && dy > 0 ) person.setDy( dy * -1 );
        } );
    }
}
