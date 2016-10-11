import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.lang.invoke.MethodHandles;
import java.util.Objects;
import java.util.Optional;

@Path( "manage/persons" )
public class PersonsManager {

    private static Logger logger = LoggerFactory.getLogger( MethodHandles.lookup().lookupClass() );

    @GET
    @Path( "put" )
    @Produces( "text/plain" )
    public String put( @QueryParam( "id" ) String id, @QueryParam( "x" ) Integer x, @QueryParam( "y" ) Integer y ) {
        Objects.requireNonNull( id );
        if( ( x != null && y == null ) )
            throw new NullPointerException();
        else if( ( y != null && x == null ) )
            throw new NullPointerException();

        Person person = new Person( id, x != null ? x : Rand.uniform( Area.PREF_AREA_WIDTH ), y != null ? y : Rand.uniform( Area.PREF_AREA_HEIGHT ) );
        if( logger.isWarnEnabled() ) {
            Area.getInstance()
                    .getUnitByID( id )
                    .ifPresent( unit -> logger.warn( "Person with id " + id + " already exists and will be overwriten.\n" + unit ) );
        }
        Area.getInstance().addUnit( person );
        return person.toString();
    }

    @GET
    @Path( "delete" )
    @Produces( "text/plain" )
    public String delete( @QueryParam( "id" ) String id ) {
        logger.info( "Found GET request on manage/persons/delete" );
        Objects.requireNonNull( id );
        Optional<MovableUnit> optional = Area.getInstance().removeUnitByID( id );
        return optional.isPresent() ? optional.get().toString() : "null";
    }

    @GET
    @Path( "get" )
    @Produces( "text/plain" )
    public String get( @QueryParam( "id" ) String id ) {
        logger.info( "Found GET request on manage/persons/get" );
        Objects.requireNonNull( id );
        Optional<MovableUnit> optional = Area.getInstance().getUnitByID( id );
        return optional.isPresent() ? optional.get().toString() : "null";
    }
}
