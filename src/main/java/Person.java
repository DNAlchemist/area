/**
 * Created by ruslan on 08.10.16.
 */
public class Person implements MovableUnit {

    final static int DEFAULT_RADIUS = 10;

    private final String id;
    private double r, x, y, dx = 1, dy = 1;
    private boolean active = true;

    public Person( String id, int x, int y ) {
        this( id, x, y, DEFAULT_RADIUS );
    }

    public Person( String id, double x, double y, double radius ) {
        this.id = id;
        this.r = radius;
        this.x = x;
        this.y = y;
    }

    @Override
    public double getR() {
        return r;
    }

    @Override
    public void setR( double r ) {
        this.r = r;
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
    public void setX( double x ) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setY( double y ) {
        this.y = y;
    }

    @Override
    public MovableUnit move() {
        x += dx;
        y += dy;
        return this;
    }

    @Override
    public double getDx() {
        return dx;
    }

    @Override
    public void setDx( double dx ) {
        this.dx = dx;
    }

    @Override
    public double getDy() {
        return dy;
    }

    @Override
    public void setDy( double dy ) {
        this.dy = dy;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive( boolean isActive ) {
        this.active = isActive;
    }

    @Override
    public String toString() {
        return "Person( id = " + id + ", x = " + x + ", y = " + y + ", dx = " + dx + ", dy = " + dy + ( !active ? ", state: inactive" : "" ) + " )";
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals( Object o ) {
        return o == this || o instanceof Person && id.equals( ( ( Person )o ).getID() );
    }
}
