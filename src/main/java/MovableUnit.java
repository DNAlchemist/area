/**
 * Created by ruslan on 08.10.16.
 */
public interface MovableUnit extends Unit {

    MovableUnit move();

    double getDx();

    double getDy();

    void setDx( double x );

    void setDy( double y );

    boolean isActive();

    void setActive( boolean isActive );

}
