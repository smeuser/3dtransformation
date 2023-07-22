public class Face3D {
	String name;
	Vector3D x1y1;
	Vector3D x1y0;
	Vector3D x0y0;
	Vector3D x0y1;
	Vector3D normal;
	public Face3D(){
		x1y1 = new Vector3D();
		x1y0 = new Vector3D();
		x0y0 = new Vector3D();
		x0y1 = new Vector3D();
		normal = null;
	}
	public Face3D(Vector3D a, Vector3D b, Vector3D c, Vector3D d){
		x1y1 = (Vector3D)a.clone();
		x1y0 = (Vector3D)b.clone();
		x0y0 = (Vector3D)c.clone();
		x0y1 = (Vector3D)d.clone();
		normal = null;
	}
	public Face3D(Face3D rhs){
		x1y1 = (Vector3D)rhs.getX1Y1().clone();
		x1y0 = (Vector3D)rhs.getX1Y0().clone();
		x0y0 = (Vector3D)rhs.getX0Y0().clone();
		x0y1 = (Vector3D)rhs.getX0Y1().clone();
		normal = null;
	}
	public void setX1Y1(Vector3D rhs){
		x1y1 = (Vector3D)rhs.clone();
	}
	public void setX1Y0(Vector3D rhs){
		x1y0 = (Vector3D)rhs.clone();
	}
	public void setX0Y0(Vector3D rhs){
		x0y0 = (Vector3D)rhs.clone();
	}
	public void setX0Y1(Vector3D rhs){
		x0y1 = (Vector3D)rhs.clone();
	}
	public Vector3D getX1Y1(){
		return x1y1;
	}
	public Vector3D getX1Y0(){
		return x1y0;
	}
	public Vector3D getX0Y0(){
		return x0y0;
	}
	public Vector3D getX0Y1(){
		return x0y1;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Vector3D getNormal() {
		if(normal == null){
			this.setNormal();
		}
		return normal;
	}
	private void setNormal() {
		this.normal = null;
	}
}
