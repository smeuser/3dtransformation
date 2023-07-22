
public class Vector3D {
	float x;
	float y;
	float z;
	
	public Vector3D(){
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	public Vector3D(float rSize){
		this.x = this.y = this.z = rSize;
	}
	public Vector3D(float rx, float ry, float rz){
		this.x = rx;
		this.y = ry;
		this.z = rz;
	}
	public void add(Vector3D rhs){
		this.x += rhs.x;
		this.y += rhs.y;
		this.z += rhs.z;
	}
	public void sub(Vector3D rhs){
		this.x -= rhs.x;
		this.y -= rhs.y;
		this.z -= rhs.z;
	}
	public void pot(Vector3D rhs){
		this.x *= rhs.x;
		this.y *= rhs.y;
		this.z *= rhs.z;
	}
	public void div(Vector3D rhs){
		this.x /= rhs.x;
		this.y /= rhs.y;
		this.z /= rhs.z;
	}
	public Object clone(){
		Vector3D rv = new Vector3D(this.x, this.y, this.z);
		return rv;
	}
}
