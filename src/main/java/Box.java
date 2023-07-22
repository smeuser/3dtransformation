import java.awt.Color;

public class Box extends Object3D {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector3D local;
	private Vector3D lookUp;
	private Vector3D dimension;
	
	public Box(){
		this.local = new Vector3D();
		this.lookUp = new Vector3D();
		this.dimension = new Vector3D(1);
		this.innerColor = Color.BLACK;
		this.outerColor = Color.WHITE;
		this.type = 0;
		this.typeName = "Box";
		this.vertices = null;
	}
	public Box(Vector3D rLocal, Vector3D rLookUp, Vector3D rDimension){
		this.local = (Vector3D)rLocal.clone();
		this.lookUp = (Vector3D)rLookUp.clone();
		this.dimension = (Vector3D)rDimension.clone();
		this.innerColor = Color.BLACK;
		this.outerColor = Color.WHITE;
		this.type = 0;
		this.typeName = "Box";
		this.vertices = null;
	}
	public Vector3D getDimension() {
		return dimension;
	}
	public void setDimension(Vector3D dimension) {
		this.dimension = dimension;
		this.vertices = null;
	}
	public Vector3D getLocal() {
		return local;
	}
	public void setLocal(Vector3D local) {
		this.local = local;
		this.vertices = null;
	}
	public Vector3D getLookUp() {
		return lookUp;
	}
	public void setLookUp(Vector3D lookUp) {
		this.lookUp = lookUp;
		this.vertices = null;
	}
	public Color getIColor() {
		return this.innerColor;
	}
	@Override
	public void setIColor(Color color) {
		this.innerColor = color;
	}
	@Override
	public void setIColor(int r, int g, int b){
		this.innerColor = new Color(r, g, b);
	}
	@Override
	public Color getOColor() {
		return this.outerColor;
	}
	@Override
	public void setOColor(Color color) {
		this.outerColor = color;
	}
	@Override
	public void setOColor(int r, int g, int b){
		this.outerColor = new Color(r, g, b);
	}
	public void move(Vector3D rhs){
		this.local.add(rhs);
		this.vertices = null;
	}
	public void rotate(Vector3D rhs){
		this.lookUp.add(rhs);
		this.vertices = null;
	}
	public void scale(Vector3D rhs){
		this.dimension.pot(rhs);
		this.vertices = null;
	}
	public Object clone(){
		Box rv = new Box((Vector3D)this.local.clone(), (Vector3D)this.lookUp.clone(), (Vector3D)this.dimension.clone());
		return rv;
	}
	public Vector3D [][] getVertices(){
		if(this.vertices == null){
			int dimCount = 0;
			if(this.getDimension().x == 0)
				dimCount++;
			if(this.getDimension().y == 0)
				dimCount++;
			if(this.getDimension().z == 0)
				dimCount++;
			
			Vector3D [] [] rv = null;
			if(dimCount > 1)
				return null;
			else if(dimCount != 0){
				rv = new Vector3D[1][4];
				
				rv[0][0] = new Vector3D(this.getLocal().x + this.getDimension().x / 2,
						 this.getLocal().y + this.getDimension().y / 2,
						 this.getLocal().z + this.getDimension().z / 2);
				rv[0][1] = new Vector3D(this.getLocal().x + this.getDimension().x / 2,
						 this.getLocal().y + this.getDimension().y / 2,
						 this.getLocal().z - this.getDimension().z / 2);
				rv[0][2] = new Vector3D(this.getLocal().x - this.getDimension().x / 2,
						 this.getLocal().y + this.getDimension().y / 2,
						 this.getLocal().z - this.getDimension().z / 2);
				rv[0][3] = new Vector3D(this.getLocal().x - this.getDimension().x / 2,
						 this.getLocal().y + this.getDimension().y / 2,
						 this.getLocal().z + this.getDimension().z / 2);
			}
			else{
				rv = new Vector3D[6][4];
				
				// Plane Top
				rv[0][0] = new Vector3D(this.getLocal().x + this.getDimension().x / 2,
						 this.getLocal().y + this.getDimension().y / 2,
						 this.getLocal().z + this.getDimension().z / 2);
				rv[0][1] = new Vector3D(this.getLocal().x + this.getDimension().x / 2,
						 this.getLocal().y + this.getDimension().y / 2,
						 this.getLocal().z - this.getDimension().z / 2);
				rv[0][2] = new Vector3D(this.getLocal().x - this.getDimension().x / 2,
						 this.getLocal().y + this.getDimension().y / 2,
						 this.getLocal().z - this.getDimension().z / 2);
				rv[0][3] = new Vector3D(this.getLocal().x - this.getDimension().x / 2,
						 this.getLocal().y + this.getDimension().y / 2,
						 this.getLocal().z + this.getDimension().z / 2);
				// Plane Right
				rv[1][0] = new Vector3D(this.getLocal().x + this.getDimension().x / 2,
						 this.getLocal().y - this.getDimension().y / 2,
						 this.getLocal().z + this.getDimension().z / 2);
				rv[1][1] = new Vector3D(this.getLocal().x + this.getDimension().x / 2,
						 this.getLocal().y - this.getDimension().y / 2,
						 this.getLocal().z - this.getDimension().z / 2);
				rv[1][2] = new Vector3D(this.getLocal().x + this.getDimension().x / 2,
						 this.getLocal().y + this.getDimension().y / 2,
						 this.getLocal().z - this.getDimension().z / 2);
				rv[1][3] = new Vector3D(this.getLocal().x + this.getDimension().x / 2,
						 this.getLocal().y + this.getDimension().y / 2,
						 this.getLocal().z + this.getDimension().z / 2);
				// Plane Back
				rv[2][0] = new Vector3D(this.getLocal().x + this.getDimension().x / 2,
						 this.getLocal().y + this.getDimension().y / 2,
						 this.getLocal().z + this.getDimension().z / 2);
				rv[2][1] = new Vector3D(this.getLocal().x + this.getDimension().x / 2,
						 this.getLocal().y - this.getDimension().y / 2,
						 this.getLocal().z + this.getDimension().z / 2);
				rv[2][2] = new Vector3D(this.getLocal().x - this.getDimension().x / 2,
						 this.getLocal().y - this.getDimension().y / 2,
						 this.getLocal().z + this.getDimension().z / 2);
				rv[2][3] = new Vector3D(this.getLocal().x - this.getDimension().x / 2,
						 this.getLocal().y + this.getDimension().y / 2,
						 this.getLocal().z + this.getDimension().z / 2);
				// Plane Left
				rv[3][0] = new Vector3D(this.getLocal().x - this.getDimension().x / 2,
						 this.getLocal().y - this.getDimension().y / 2,
						 this.getLocal().z + this.getDimension().z / 2);
				rv[3][1] = new Vector3D(this.getLocal().x - this.getDimension().x / 2,
						 this.getLocal().y - this.getDimension().y / 2,
						 this.getLocal().z - this.getDimension().z / 2);
				rv[3][2] = new Vector3D(this.getLocal().x - this.getDimension().x / 2,
						 this.getLocal().y + this.getDimension().y / 2,
						 this.getLocal().z - this.getDimension().z / 2);
				rv[3][3] = new Vector3D(this.getLocal().x - this.getDimension().x / 2,
						 this.getLocal().y + this.getDimension().y / 2,
						 this.getLocal().z + this.getDimension().z / 2);
				// Plane Front
				rv[4][0] = new Vector3D(this.getLocal().x + this.getDimension().x / 2,
						 this.getLocal().y + this.getDimension().y / 2,
						 this.getLocal().z - this.getDimension().z / 2);
				rv[4][1] = new Vector3D(this.getLocal().x + this.getDimension().x / 2,
						 this.getLocal().y - this.getDimension().y / 2,
						 this.getLocal().z - this.getDimension().z / 2);
				rv[4][2] = new Vector3D(this.getLocal().x - this.getDimension().x / 2,
						 this.getLocal().y - this.getDimension().y / 2,
						 this.getLocal().z - this.getDimension().z / 2);
				rv[4][3] = new Vector3D(this.getLocal().x - this.getDimension().x / 2,
						 this.getLocal().y + this.getDimension().y / 2,
						 this.getLocal().z - this.getDimension().z / 2);
				// Plane Bottom
				rv[5][0] = new Vector3D(this.getLocal().x + this.getDimension().x / 2,
						 this.getLocal().y - this.getDimension().y / 2,
						 this.getLocal().z + this.getDimension().z / 2);
				rv[5][1] = new Vector3D(this.getLocal().x + this.getDimension().x / 2,
						 this.getLocal().y - this.getDimension().y / 2,
						 this.getLocal().z - this.getDimension().z / 2);
				rv[5][2] = new Vector3D(this.getLocal().x - this.getDimension().x / 2,
						 this.getLocal().y - this.getDimension().y / 2,
						 this.getLocal().z - this.getDimension().z / 2);
				rv[5][3] = new Vector3D(this.getLocal().x - this.getDimension().x / 2,
						 this.getLocal().y - this.getDimension().y / 2,
						 this.getLocal().z + this.getDimension().z / 2);
			}
			this.vertices = rv;
			rv = this.evalVerticesRot(rv, this.lookUp, false);
		}
		
		return this.vertices;
	}
	private Vector3D [][] evalVerticesRot(Vector3D [][] rhs, Vector3D rot, boolean toWorld){
		Vector3D [][] rv = (Vector3D[][]) rhs.clone();
		rot = new Vector3D(rot.x % 360, rot.y % 360, rot.z % 360);
		
		for(int i = 0; i < rv.length; i++){
			for(int o = 0; o < rv[0].length; o++){
				//	set the object origin to the world origin
				if(!toWorld)
					rv[i][o].sub(this.local);
				rv[i][o] = evalVerticeRot(rv[i][o], rot);
				// set the object origin back to his local position
				if(!toWorld)
					rv[i][o].add(this.local);
			}
		}
		
		return rv;
	}
	private Vector3D evalVerticeRot(Vector3D rhs, Vector3D rot){
		Vector3D rv = (Vector3D) rhs.clone();
			
		float xnew = (float) (rv.x * Angle.cos(rot.z) - rv.y * Angle.sin(rot.z));
		float ynew = (float) (rv.x * Angle.sin(rot.z) + rv.y * Angle.cos(rot.z));
		float znew = rv.z;
			
		float x2 = xnew;
		float y2 = (float) (ynew * Angle.cos(rot.x) - znew * Angle.sin(rot.x));
		float z2 = (float) (ynew * Angle.sin(rot.x) + znew * Angle.cos(rot.x));
			
		xnew = (float) (x2 * Angle.cos(rot.y) - z2 * Angle.sin(rot.y));
		ynew = y2;
		znew = (float) (x2 * Angle.sin(rot.y) + z2 * Angle.cos(rot.y));
			
		rv = new Vector3D(xnew, ynew, znew);
		
		return rv;
	}
	@Override
	public int getType() {
		return type;
	}
	@Override
	public String getTypeName() {
		return typeName;
	}
	@Override
	protected void setVertices() {
		if(vertices == null){
			this.getVertices();
		}
	}
	@Override
	public Vector3D[][] getVerticesToCamera(int rhs) {
		if(this.vertices == null)
			this.vertices = this.getVertices();
		Vector3D [][] rv = this.evalVerticesRot(this.vertices, Camera.getCam(rhs).getLookUp(), true);
		this.vertices = null;
		return rv;
	}
}
