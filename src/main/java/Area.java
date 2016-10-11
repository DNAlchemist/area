import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by ruslan on 10.10.16.
 */
public class Area implements Runnable {

    public static final int PREF_AREA_WIDTH = 700;
    public static final int PREF_AREA_HEIGHT = 700;

    private static Logger logger = LoggerFactory.getLogger( MethodHandles.lookup().lookupClass() );

    private Area() { }

    private CopyOnWriteArraySet<MovableUnit> units = new CopyOnWriteArraySet<>();
    private List<Consumer<Set<MovableUnit>>> listeners = new CopyOnWriteArrayList<>();
    private CopyOnWriteArraySet<Lighthouse> lighthouses = new CopyOnWriteArraySet<>();

    @Override
    public void run() {
        logger.info( "Area has been runned." );
        while( !Thread.interrupted() ) {
            try {
                TimeUnit.MILLISECONDS.sleep( 50 );
            } catch( InterruptedException e ) {
                logger.info( "Area has been interrupted." );
            }
            units.parallelStream().filter( MovableUnit::isActive ).forEach( MovableUnit::move );
            units.parallelStream().filter( unit -> !unit.isActive() ).forEach( unit -> {
                if( units.parallelStream().filter( MovableUnit::isActive ).noneMatch( unit2 -> MovableActions.isCollide( unit, unit2 ) ) ) {
                    unit.setActive( true );
                }
            } );
            listeners.forEach( l -> l.accept( units ) );
            lighthouses.forEach( lh -> {
                units.stream()
                        .filter( MovableUnit::isActive )
                        .filter( unit -> lh.getID().equals( unit.getID() ) )
                        .filter( unit -> {
                            logger.debug( lh + " - " + unit );
                            return MovableActions.isCollide( unit, lh );
                        } )
                        .findAny()
                        .ifPresent( unit -> {
                            lh.run();
                            lighthouses.remove( lh );
                        } );
                lh.decrement();
                if( lh.isTimedOut() ) {
                    lighthouses.remove( lh );
                }
            } );
            if( logger.isTraceEnabled() ) {
                logger.trace( units.parallelStream().map( Object::toString ).collect( Collectors.joining( "\n" ) ) );
            }
            MovableActions.collide( units );
            MovableActions.collideBorder( units, PREF_AREA_HEIGHT, PREF_AREA_WIDTH );
        }
        logger.info( "Area has been interrupted." );
    }

    private static class AreaHolder {
        private static Area instance = new Area();
        static {
            new Thread( instance ).start();
        }
    }

    public static Area getInstance() {
        return AreaHolder.instance;
    }

    public void addLighhouse( Lighthouse lighthouse ) {
        lighthouses.add( lighthouse );
    }

    public void addUnit( MovableUnit unit ) {
        logger.info( "Add unit: " + unit );
        if( units.parallelStream().filter( MovableUnit::isActive ).anyMatch( unit2 -> MovableActions.isCollide( unit, unit2 ) ) ) {
            logger.info( "Unit is collide with another unit. Set unit inactive." );
            unit.setActive( false );
        }
        units.add( unit );
    }

    public void addListener( Consumer<Set<MovableUnit>> units ) {
        listeners.add( units );
    }

    public Optional<MovableUnit> getUnitByID( String id ) {
        if( id == null ) {
            return Optional.empty();
        }
        return units.parallelStream().filter( unit -> unit.getID().equals( id ) ).findAny();
    }

    public void removeUnitByID( String id ) {
        getUnitByID( id ).ifPresent( units::remove );
    }
}
