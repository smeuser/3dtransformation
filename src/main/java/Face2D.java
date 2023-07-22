import java.awt.Point;

public class Face2D {
	String name;
	Point x1y1;
	Point x1y0;
	Point x0y0;
	Point x0y1;
	public Face2D(){
		x1y1 = new Point();
		x1y0 = new Point();
		x0y0 = new Point();
		x0y1 = new Point();
	}
	public Face2D(Point a, Point b, Point c, Point d){
		x1y1 = (Point)a.clone();
		x1y0 = (Point)b.clone();
		x0y0 = (Point)c.clone();
		x0y1 = (Point)d.clone();
	}
	public void setX1Y1(Point rhs){
		x1y1 = (Point)rhs.clone();
	}
	public void setX1Y0(Point rhs){
		x1y0 = (Point)rhs.clone();
	}
	public void setX0Y0(Point rhs){
		x0y0 = (Point)rhs.clone();
	}
	public void setX0Y1(Point rhs){
		x0y1 = (Point)rhs.clone();
	}
	public Point getX1Y1(){
		return x1y1;
	}
	public Point getX1Y0(){
		return x1y0;
	}
	public Point getX0Y0(){
		return x0y0;
	}
	public Point getX0Y1(){
		return x0y1;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
