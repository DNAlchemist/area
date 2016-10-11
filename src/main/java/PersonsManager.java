import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.lang.invoke.MethodHandles;
import java.util.Objects;

@Path( "manage/persons" )
public class PersonsManager extends JFrame {

    private static Logger logger = LoggerFactory.getLogger( MethodHandles.lookup().lookupClass() );

    @GET
    @Path( "put" )
    @Produces( "text/plain" )
    public String put( @QueryParam( "id" ) String id, @QueryParam( "x" ) String x, @QueryParam( "y" ) String y ) {
        Objects.requireNonNull( id );
        if( ( x != null && y == null ) )
            throw new NullPointerException();
        else if( ( y != null && x == null ) )
            throw new NullPointerException();

        int intX = x != null ? Integer.valueOf( x ) : Rand.uniform( Area.PREF_AREA_WIDTH );
        int intY = y != null ? Integer.valueOf( y ) : Rand.uniform( Area.PREF_AREA_HEIGHT );
        Person person = new Person( id, intX, intY );
        Area.getInstance().addUnit( person );
        return person.toString();
    }

    @GET
    @Path( "delete" )
    @Produces( "text/plain" )
    public String delete( @QueryParam( "id" ) String id ) {
        Objects.requireNonNull( id );
        Area.getInstance().removeUnitByID( id );
        logger.info( "Found GET request on manage/persons/delete" );
        return "Person( id = " + id + " )";
    }
}
