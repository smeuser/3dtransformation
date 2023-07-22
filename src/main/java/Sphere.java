import java.awt.Color;

public class Sphere extends Object3D {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector3D local;
	private Vector3D lookUp;
	private Vector3D dimension;
	private int res;

	public Sphere() {
		this.local = new Vector3D();
		this.lookUp = new Vector3D();
		this.dimension = new Vector3D(1);
		this.res = 5;
		this.innerColor = Color.BLACK;
		this.outerColor = Color.WHITE;
		this.type = 1;
		this.typeName = "Sphere";
		vertices = null;
	}
	public Sphere(Vector3D rLocal, Vector3D rLookUp, Vector3D rDimension) {
		this.local = (Vector3D) rLocal.clone();
		this.lookUp = (Vector3D) rLookUp.clone();
		this.dimension = (Vector3D) rDimension.clone();
		this.res = 5;
		this.type = 1;
		this.typeName = "Sphere";
		vertices = null;
	}
	public Sphere(Vector3D rLocal, Vector3D rLookUp, Vector3D rDimension,
			int Res) {
		this.local = (Vector3D) rLocal.clone();
		this.lookUp = (Vector3D) rLookUp.clone();
		this.dimension = (Vector3D) rDimension.clone();
		this.res = Res;
		this.type = 1;
		this.typeName = "Sphere";
		vertices = null;
	}
	public Vector3D getDimension() {
		return dimension;
	}
	public int getRes() {
		return res;
	}
	public void setRes(int res) {
		this.res = res;
		this.vertices = null;
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
	@Override
	public Color getIColor() {
		return this.innerColor;
	}
	@Override
	public void setIColor(Color color) {
		this.innerColor = color;
	}
	@Override
	public void setIColor(int r, int g, int b) {
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
	public void setOColor(int r, int g, int b) {
		this.outerColor = new Color(r, g, b);
	}
	public void move(Vector3D rhs) {
		this.local.add(rhs);
		this.vertices = null;
	}
	public void rotate(Vector3D rhs) {
		this.lookUp.add(rhs);
		this.vertices = null;
	}
	public void scale(Vector3D rhs) {
		this.dimension.pot(rhs);
		this.vertices = null;
	}
	public Object clone() {
		Sphere rv = new Sphere((Vector3D) this.local.clone(),
				(Vector3D) this.lookUp.clone(), (Vector3D) this.dimension
						.clone());
		return rv;
	}
	public Vector3D[][] getVertices() {
		if(vertices == null){
			Vector3D[][] rv = null;
			rv = this.evalVertices(this.res, this.dimension.x);
			
			//	apply Dimension settings
			for(int i = 0; i < rv.length; i++){
				for(int o = 0; o < rv[0].length; o++){
					//	set sphere origin to the world origin
					rv[i][o].sub(this.local);
					if(this.dimension.x != this.dimension.y){
						float f = this.dimension.y / this.dimension.x;
						rv[i][o].y *= f;
					}
					if(this.dimension.x != this.dimension.z){
						float f = this.dimension.z / this.dimension.x;
						rv[i][o].z *= f;
					}
					// set spere to his predefined origin position
					rv[i][o].add(this.local);
				}
			}
			//	set field vertices
			vertices = this.evalVerticesRot(rv, this.lookUp, false);
			
		}
		return vertices;
	}
	private Vector3D evalVerticeXY(float r, float rad){
		double x = r * Math.cos(Math.PI / 180 * rad);
		double y = r * Math.sin(Math.PI / 180 * rad);
		double z = 0;
		return new Vector3D((float)x, (float)y, (float)z);
	}
	private Vector3D evalVerticeXZ(Vector3D rhs, float rad){
		float r = this.getR(rhs);
		if(rad == 0){
			return rhs;
		}
		double x = r * Math.cos(Math.PI / 180 * rad);
		double z = r * Math.sin(Math.PI / 180 * rad);
		return new Vector3D((float)x, rhs.y, (float)z);
	}
	private float getR(Vector3D rhs){
		float rv = rhs.x;
		return rv;
	}
	private Vector3D [][] evalVertices(int res, float r){
		Vector3D [][] rv = new Vector3D[res+1][res+1];
		float rad = 90 / res;
		for(int i = 0; i <= res; i++){
			for(int o = 0; o <= res; o++){
				rv[i][o] = evalVerticeXY(r, i * rad);
				rv[i][o] = evalVerticeXZ(rv[i][o], o * rad);
				//	Transform to world
				rv[i][o].add(this.local);
			}
		}
		rv = addMatrixArrays2DX(rv, mirrorCollection(rv, 0));
		rv = addMatrixArrays2DY(rv, mirrorCollection(rv, 1));
		rv = addMatrixArrays2DZ(rv, mirrorCollection(rv, 2));
//		printVertices(rv);
		return rv;
	}
	private Vector3D [][] mirrorCollection(Vector3D [][] rhs1, int axis){
		Vector3D [][] rhs = null;
		if(axis == 0){
			rhs = new Vector3D[rhs1.length][rhs1[0].length- 1];
			for(int i = 0; i < rhs1.length; i++){
				for(int o = 0; o < rhs1[0].length - 1; o++){
					rhs[i][o] = (Vector3D) rhs1[i][o].clone();
					//	set Sphere local to the world origin
					rhs[i][o].sub(this.local);
					rhs[i][o].x *= -1;
					//	set Sphere back to his local
					rhs[i][o].add(this.local);
				}
			}
		}
		if(axis == 1){
			rhs = new Vector3D[rhs1.length-1][rhs1[0].length];
			for(int i = 0; i < rhs.length; i++){
				for(int o = 0; o < rhs1[0].length; o++){
					rhs[i][o] = (Vector3D) rhs1[i + 1][o].clone();
					//	set Sphere local to the world origin
					rhs[i][o].sub(this.local);
					rhs[i][o].y *= -1;
					//	set Sphere back to his local
					rhs[i][o].add(this.local);
				}
			}
		}
		if(axis == 2){
			rhs = new Vector3D[rhs1.length][rhs1[0].length-1];
			for(int i = 0; i < rhs.length; i++){
				for(int o = 0; o < rhs[0].length; o++){
					rhs[i][o] = (Vector3D) rhs1[i][o].clone();
					//	set Sphere local to the world origin
					rhs[i][o].sub(this.local);
					rhs[i][o].z *= -1;
					//	set Sphere back to his local
					rhs[i][o].add(this.local);
				}
			}
		}
			
		return rhs;
	}
	public void printVertices(Vector3D [][] rhs){
		for(int i = 0; i <= res; i++){
			for(int o = 0; o <= res; o++){
				System.out.println("Object "+i+":"+o+"x: "+rhs[i][o].x+" y: "+rhs[i][o].y+" z: "+rhs[i][o].z);
			}
		}
		System.out.println("end of data");
	}
	public Vector3D [][] addMatrixArrays2DX(Vector3D [][] rhs1, Vector3D [][] rhs2){
		Vector3D [][] rv = new Vector3D[rhs1.length][rhs1.length + rhs2[0].length];
		rhs1 = sortReverseX(rhs1);
		for(int i = 0; i < rhs1.length; i++){
			for(int o = 0; o < rhs1.length + rhs2[0].length; o++){
				if(o < rhs2[0].length)
					rv[i][o] = (Vector3D) rhs2[i][o].clone();
				else
					rv[i][o] = (Vector3D) rhs1[i][o - rhs2[0].length].clone();
			}
		}
		
		return rv;
	}
	public Vector3D [][] addMatrixArrays2DY(Vector3D [][] rhs1, Vector3D [][] rhs2){
		Vector3D [][] rv = new Vector3D[rhs1.length + rhs2.length][rhs1[0].length];
		rhs1 = sortReverseY(rhs1);
		for(int i = 0; i < rhs1.length + rhs2.length; i++){
			for(int o = 0; o < rhs1[0].length; o++){
				if(i < rhs1.length)
					rv[i][o] = (Vector3D) rhs1[i][o].clone();
				else
					rv[i][o] = (Vector3D) rhs2[i - rhs1.length][o].clone();
			}
		}
		
		return rv;
	}
	public Vector3D [][] addMatrixArrays2DZ(Vector3D [][] rhs1, Vector3D [][] rhs2){
		Vector3D [][] rv = new Vector3D[rhs1.length][rhs1[0].length + rhs2[0].length];
		rhs2 = sortReverseX(rhs2);
		for(int i = 0; i < rv.length; i++){
			for(int o = 0; o < rv[0].length; o++){
				if(o < rhs1[0].length)
					rv[i][o] = (Vector3D) rhs1[i][o].clone();
				else
					rv[i][o] = (Vector3D) rhs2[i][o - rhs1[0].length].clone();
			}
		}
		
		return rv;
	}
	public Vector3D [][] sortReverseX(Vector3D [][] rhs1){
		Vector3D [][] rhs = new Vector3D[rhs1.length][rhs1[0].length];
		for(int i = 0; i < rhs.length; i++){
			for(int o = 0; o < rhs[0].length; o++){
				rhs[i][o] = (Vector3D) rhs1[i][rhs[0].length - (o + 1)].clone();
			}
		}
		return rhs;
	}
	public Vector3D [][] sortReverseY(Vector3D [][] rhs1){
		Vector3D [][] rhs = new Vector3D[rhs1.length][rhs1[0].length];
		for(int i = 0; i < rhs.length; i++){
			for(int o = 0; o < rhs[0].length; o++){
				rhs[i][o] = (Vector3D) rhs1[rhs.length - (i + 1)][o].clone();
			}
		}
		return rhs;
	}
	private Vector3D [][] evalVerticesRot(Vector3D [][] rhs, Vector3D rot, boolean toWorld){
		Vector3D [][] rv = (Vector3D[][]) rhs.clone();
		
		for(int i = 0; i < rv.length; i++){
			for(int o = 0; o < rv[0].length; o++){
				//	set the object origin to the world origin
				if(!toWorld)
					rv[i][o].sub(this.local);
				rv[i][o] = evalVerticeRot(rv[i][o], rot);
				//	set the object origin back to his local position
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
	public Vector3D [][] invertZAxis(Vector3D [][] rhs1){
		Vector3D [][] rhs = new Vector3D [rhs1.length][rhs1[0].length];
		for(int i = 0; i < rhs.length; i++){
			for(int o = 0; o < rhs1[0].length; o++){
				rhs[i][o] = (Vector3D) rhs1[i][o].clone();
				rhs[i][o].z *= -1;
			}
		}
		return rhs;
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
			this.evalVertices(this.res, this.dimension.x);
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
