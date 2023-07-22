import java.awt.Point;

public class Vector2D {
	float x;
	float y;
	
	public Vector2D(){
		this.x = 0;
		this.y = 0;
	}
	public Vector2D(float rSize){
		this.x = this.y = rSize;
	}
	public Vector2D(float rx, float ry){
		this.x = rx;
		this.y = ry;
	}
	public void add(Vector2D rhs){
		this.x += rhs.x;
		this.y += rhs.y;
	}
	public void sub(Vector2D rhs){
		this.x -= rhs.x;
		this.y -= rhs.y;
	}
	public void pot(Vector2D rhs){
		this.x *= rhs.x;
		this.y *= rhs.y;
	}
	public void div(Vector2D rhs){
		this.x /= rhs.x;
		this.y /= rhs.y;
	}
	public Object clone(){
		Vector2D rv = new Vector2D(this.x, this.y);
		return rv;
	}
	public Point toPoint(){
		return new Point((int)this.x, (int)this.y);
	}
}
