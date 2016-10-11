import groovy.transform.CompileStatic
import spock.lang.Specification

import java.util.concurrent.TimeUnit
/**
 * Created by ruslan on 08.10.16.
 */
@CompileStatic
class PersonTest extends Specification {

    def "Test person move"() {
        setup:
        def person = new Person( "0", 200, 200 );
        when:
        person.move();
        def x = person.x;
        def y = person.y;
        then:
        x == 201D;
        y == 201D;
        when:
        person.dx = 5;
        person.dy = 10;
        person.move();
        x = person.x;
        y = person.y;
        then:
        x == 206D;
        y == 211D;
    }

    def "Test person equals"() {
        when:
        def person = new Person( "0", 200, 200 );
        def person2 = new Person( "1", 200, 200 );
        then:
        person != person2
        when:
        person = new Person( "10", 200, 200 );
        person2 = new Person( "10", 250, 200 );
        then:
        person == person2
    }

    def "Test person hashCode is equal"() {
        when:
        def person = new Person( "300", 200, 200 );
        def person2 = new Person( "300", 200, 200 );
        def hashCode = person.hashCode()
        def hashCode2 = person2.hashCode()
        then:
        hashCode == hashCode2
    }

    def "Test person hashCode is differ"() {
        when:
        def person = new Person( "300", 200, 200 );
        def person2 = new Person( "500", 200, 200 );
        def hashCode = person.hashCode()
        def hashCode2 = person2.hashCode()
        then:
        hashCode != hashCode2
    }

    def "Test person collide"() {
        setup:
        def person1 = new Person( "0", 200, 200 );
        def person2 = new Person( "1", 214, 214 );
        when:
        person1.dx = -1D;
        person1.dy = -1D;
        def dx1 = Math.round( person1.getDx() )
        def dx2 = Math.round( person2.getDx() )
        then:
        dx1 == -1L;
        dx2 == 1L;
        when:
        MovableActions.collide( [ person1, person2 ] as Set );
        dx1 = Math.round( person1.getDx() )
        dx2 = Math.round( person2.getDx() )
        then:
        dx1 == 1L
        dx2 == -1L

        when:
        person2 = new Person( "1", 500, 500 );
        MovableActions.collide( [ person1, person2 ] as Set );
        dx1 = Math.round( person1.getDx() )
        dx2 = Math.round( person2.getDx() )
        then:
        dx1 == 1L
        dx2 == 1L
    }

    def "Test person inactive"() {
        when:
        def person1 = new Person( "0", 200, 200 );
        def person2 = new Person( "1", 214, 214 );
        person1.dx = -1D;
        person1.dy = -1D;
        person2.setActive( false );
        MovableActions.collide( [ person1, person2 ] as Set );
        def dx1 = Math.round( person1.getDx() )
        def dx2 = Math.round( person2.getDx() )
        then:
        dx1 == -1L
        dx2 == 1L

    }

    def "Test person collide border"() {
        when:
        def person1 = new Person( "0", Area.PREF_AREA_WIDTH, 500 );
        def dx1 = person1.getDx()
        then:
        dx1 == 1D;
        when:
        MovableActions.collideBorder( [ person1 ] as Set, Area.PREF_AREA_HEIGHT, Area.PREF_AREA_WIDTH );
        dx1 = person1.getDx()
        then:
        dx1 == -1D
    }

    def "Test find user by id"() {
        when:
        def person1 = new Person( "0" , 100, 100 );
        Area.getInstance().addUnit( person1 );
        Optional<MovableUnit> unit = Area.getInstance().getUnitByID( "0" );
        def isPresent = unit.isPresent();
        then:
        isPresent
        when:
        def id = unit.get().getID();
        then:
        id == "0";
    }

    def "Test remove user by id"() {
        when:
        def person1 = new Person( "0" , 100, 100 );
        Area.getInstance().addUnit( person1 );
        Area.getInstance().removeUnitByID( "0" );
        def isPresent = Area.getInstance().getUnitByID( "0" ).isPresent();
        then:
        !isPresent
    }

    def "Test lighthouse"() {
        when:
        def person = new Person( "0" , 100, 100 );
        Area.getInstance().addUnit( person );
        Thread.start( Area.getInstance().&run );
        def lighthouse = new LighthouseImpl( "0", 120, 120, 10, { return "It's ok" }, 10 );
        Area.getInstance().addLighhouse( lighthouse );
        def message = lighthouse.get( 10, TimeUnit.SECONDS )
        then:
        message == "It's ok"
    }

    def "Test lighthouse different ids"() {
        setup:
        def person = new Person( "0" , 100, 100 );
        Area.getInstance().addUnit( person );
        Thread.start( Area.getInstance().&run );
        def lighthouse = new LighthouseImpl( "1", 120, 120, 10, { return "It's ok" }, 10 );
        Area.getInstance().addLighhouse( lighthouse );
        when:
        def result = lighthouse.get();
        then:
        !result
    }
}