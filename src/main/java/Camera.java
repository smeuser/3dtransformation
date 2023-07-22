import java.util.Vector;

public class Camera {
	private static final float arcY = (float) 20.556;
	private static final float arcX = (float) 26.565;
	private static final float sx = 1000;
	private static final float sy = 750;
	private static final float sz = 2000;
	private static Vector cams = new Vector();
	private Vector3D local;
	private Vector3D lookUp;
	
	@SuppressWarnings("unchecked")
	public Camera(){
		this.local = new Vector3D();
		this.lookUp = new Vector3D();
		cams.add(this);
	}
	@SuppressWarnings("unchecked")
	public Camera(Vector3D rLookUp){
		this.local = new Vector3D();
		this.lookUp = (Vector3D)rLookUp.clone();
		cams.add(this);
	}
	@SuppressWarnings("unchecked")
	public Camera(Vector3D rLocal, Vector3D rLookUp){
		this.local = (Vector3D)rLocal.clone();
		this.lookUp = (Vector3D)rLookUp.clone();
		cams.add(this);
	}
	public Vector3D getLocal() {
		return local;
	}
	public void setLocal(Vector3D local) {
		this.local = local;
	}
	public Vector3D getLookUp() {
		return lookUp;
	}
	public void setLookUp(Vector3D lookUp) {
		this.lookUp = lookUp;
	}
	public void move(Vector3D rhs){
		this.local.add(rhs);
	}
	public void move(float rx, float ry, float rz){
		this.local.add(new Vector3D(rx, ry, rz));
	}
	public void rotate(Vector3D rhs){
		this.lookUp.add(rhs);
	}
	public float getArcY(){
		return Camera.arcY;
	}
	public float getArcX(){
		return Camera.arcX;
	}
	public static float getSx() {
		return sx;
	}
	public static float getSy() {
		return sy;
	}
	public static float getSz() {
		return sz;
	}
	public void setLocal(float f, float g, float h) {
		this.local.x = f;
		this.local.y = g;
		this.local.z = h;
	}
	public static Camera getCam(int rhs){
		if(rhs >= 0 && rhs < cams.size())
			return (Camera) cams.get(rhs);
		return null;
	}
}
