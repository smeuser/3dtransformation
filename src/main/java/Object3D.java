import java.awt.Color;
import java.awt.Component;

abstract class Object3D extends Component {
	protected enum Type {Box, Spere};
	protected int type;
	protected String typeName;
	protected Color innerColor;
	protected Color outerColor;
	protected Vector3D [][] vertices;
	
	public Object3D(){
	}
	public abstract int getType();
	public abstract String getTypeName();
	public abstract Vector3D [][] getVertices();
	public abstract Vector3D [][] getVerticesToCamera(int rhs);
	public abstract Color getIColor();
	public abstract Color getOColor();
	public abstract void setIColor(Color rhs);
	public abstract void setIColor(int r, int g, int b);
	public abstract void setOColor(Color rhs);
	public abstract void setOColor(int r, int g, int b);
	protected abstract void setVertices();
	public abstract void move(Vector3D rhs);
	public abstract void scale(Vector3D rhs);
	public abstract void rotate(Vector3D rhs);
	public abstract void setLocal(Vector3D rhs);
	public abstract void setLookUp(Vector3D rhs);
	public abstract void setDimension(Vector3D rhs);
}
