import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;
import javax.swing.JFrame;

public class TransformationApp3D extends JFrame implements Runnable, MouseListener, MouseMotionListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int width = 1024;
	private int height = 768;
	private Camera cam;
	private Vector obj;
	private Thread runner;
	private Graphics dbGraphics;
	private Image dbImage;
	private int mode;
	private Point reg;
	private Vector3D originLocal;
	private Vector3D originLookUp;

	public static void main(String[] args) {
		new TransformationApp3D();
	}
	
	@SuppressWarnings("unchecked")
	public TransformationApp3D(){
		super("TransformationApp3D");
		
		mode = 0;
		reg = null;
		originLocal = null;
		originLookUp = null;
		cam = new Camera();
		cam.setLocal(0, 0, -700);
		cam.setLookUp(new Vector3D(0, 0, 0));
		obj = new Vector();
		obj.add((Object3D)new Box());
		obj.add((Object3D)new Box());
		obj.add((Object3D)new Box());
		obj.add((Object3D)new Sphere());
		obj.add((Object3D)new Sphere());
		((Box) obj.get(0)).setDimension(new Vector3D(100, 100, 100));
		((Box) obj.get(0)).move(new Vector3D(-200, 200, 0));
		((Box) obj.get(0)).setLookUp(new Vector3D(0, 0, 0));
		((Box) obj.get(0)).setIColor(Color.RED);
		((Box) obj.get(0)).setOColor(Color.BLUE);
		((Box) obj.get(1)).setDimension(new Vector3D(200, 50, 400));
		((Box) obj.get(1)).move(new Vector3D(200, 0, 150));
		((Box) obj.get(2)).setDimension(new Vector3D(40, 200, 40));
		((Box) obj.get(2)).move(new Vector3D(-50, 0, -100));
		((Box) obj.get(2)).setIColor(Color.YELLOW);
		((Box) obj.get(2)).setOColor(Color.ORANGE);
		((Sphere) obj.get(3)).move(new Vector3D(50, 150, 0));
		((Sphere) obj.get(3)).setRes(10);
		((Sphere) obj.get(3)).setDimension(new Vector3D(150, 100, 150));
		((Sphere) obj.get(3)).setIColor(Color.ORANGE);
		((Sphere) obj.get(3)).setOColor(Color.RED);
		((Sphere) obj.get(4)).move(new Vector3D(0, 0, 0));
		((Sphere) obj.get(4)).setRes(15);
		((Sphere) obj.get(4)).setDimension(new Vector3D(100, 100, 100));
		((Sphere) obj.get(4)).setIColor(Color.GRAY);
		((Sphere) obj.get(4)).setOColor(Color.BLACK);

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
		this.setSize(width, height);
		this.setResizable(false);
		this.setBackground(Color.LIGHT_GRAY);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		runner = null;
		if(runner == null){
			runner = new Thread(this);
			runner.start();
		}
	}
	public void paint(Graphics g){
		this.drawBackground(g);
		for(int i = 0; i < obj.size(); i++){
			this.drawObject(g, (Object3D)obj.get(i));
		}
		this.drawAxis(g);
	}

	private void drawBackground(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);
		g.setColor(getForeground());
	}

	public Face2D [] evalScreenPos(Object3D rhs){
		//	rotate world to cameraLookUp
		Vector3D [] [] verts = rhs.getVerticesToCamera(0);
		//	set world to his origin location
		Point [] [] vertsScreen = new Point[verts.length][4];
		for(int o = 0; o < verts.length; o++){
			for(int i = 0; i < 4; i++){
				vertsScreen[o][i] = evalScreenPos(verts[o][i]);
			}
		}
		
		Face2D [] rv = new Face2D[verts.length];
		for(int i = 0; i < rv.length; i++){
			rv[i] = new Face2D(vertsScreen[i][0], vertsScreen[i][1], vertsScreen[i][2], vertsScreen[i][3]);
		}
		
		return rv;
	}
	public Point evalScreenPos(Vector3D rhs1){
		Vector3D rhs = (Vector3D) rhs1.clone();
		//	set camera as worldorigin of the point
		rhs.x += cam.getLocal().x * -1;
		rhs.y += cam.getLocal().y * -1;
		rhs.z += cam.getLocal().z * -1;
		
		float dfz = Camera.getSz() / rhs.z;
		
		float px = rhs.x * dfz;
		float py = rhs.y * dfz;
		
		return new Point((int)px, (int)py);
	}
	public int [] getArrayX(Face2D rhs){
		int [] rv = new int[4];
		
		rv[0] = rhs.x1y1.x;
		rv[1] = rhs.x1y0.x;
		rv[2] = rhs.x0y0.x;
		rv[3] = rhs.x0y1.x;
		
		return rv;
	}
	public int [] getArrayY(Face2D rhs){
		int [] rv = new int[4];
		
		rv[0] = rhs.x1y1.y;
		rv[1] = rhs.x1y0.y;
		rv[2] = rhs.x0y0.y;
		rv[3] = rhs.x0y1.y;
		
		return rv;
	}
	public Face2D [] mapToScreen(Face2D [] rhs){
		float xf = width / (Camera.getSx() * 2);
		float yf = height / (Camera.getSy() * 2) * -1;
		
		for(int i = 0; i < rhs.length; i++){
			rhs[i].x1y1.x = (int) (rhs[i].x1y1.x * xf + width / 2);
			rhs[i].x1y0.x = (int) (rhs[i].x1y0.x * xf + width / 2);
			rhs[i].x0y0.x = (int) (rhs[i].x0y0.x * xf + width / 2);
			rhs[i].x0y1.x = (int) (rhs[i].x0y1.x * xf + width / 2);
	
			rhs[i].x1y1.y = (int) (rhs[i].x1y1.y * yf + height / 2);
			rhs[i].x1y0.y = (int) (rhs[i].x1y0.y * yf + height / 2);
			rhs[i].x0y0.y = (int) (rhs[i].x0y0.y * yf + height / 2);
			rhs[i].x0y1.y = (int) (rhs[i].x0y1.y * yf + height / 2);
		}
		
		return rhs;
	}
	public Point [] mapToScreen(Point [] rhs){
		float xf = width / (Camera.getSx() * 2);
		float yf = height / (Camera.getSy() * 2) * -1;
		
		for(int i = 0; i < rhs.length; i++){
			rhs[i].x = (int) (rhs[i].x * xf + width / 2);
			rhs[i].y = (int) (rhs[i].y * yf + height / 2);
		}
		
		return rhs;
	}
	public void drawGrid(Face2D rhs, int res, Color c, Graphics g){
		float xf1 = (float)(rhs.x1y1.x - rhs.x0y1.x) / res;
		float xf2 = (float)(rhs.x1y0.x - rhs.x0y0.x) / res;
		float xf3 = (float)(rhs.x0y1.x - rhs.x0y0.x) / res;
		float xf4 = (float)(rhs.x1y0.x - rhs.x1y1.x) / res;
		float yf = (float)(rhs.x1y0.y - rhs.x1y1.y) / res;
		
		g.setColor(c);
		for(int i = 0; i <= res; i++){
			g.drawLine((int) (rhs.x0y1.x - (xf3 * i)), (int) (rhs.x0y1.y + (yf * i)), (int) (rhs.x1y1.x + (xf4 * i)), (int) (rhs.x1y1.y + (yf * i)));
			g.drawLine((int) (rhs.x0y1.x + (xf1 * i)), rhs.x0y1.y, (int) (rhs.x0y0.x + (xf2 * i)), rhs.x0y0.y);
		}
	}
	public void drawObject(Graphics g, Object3D rhs){
		if(rhs.getTypeName() == "Box"){
			Face2D [] toDraw = evalScreenPos(rhs);
			toDraw = mapToScreen(toDraw);
			for(int i = 0; i < toDraw.length; i++){
				g.setColor(rhs.getIColor());
				g.fillPolygon(this.getArrayX(toDraw[i]), this.getArrayY(toDraw[i]), 4);
			}
			for(int i = 0; i < toDraw.length; i++){
				g.setColor(rhs.getOColor());
				g.drawPolygon(this.getArrayX(toDraw[i]), this.getArrayY(toDraw[i]), 4);
			}
//			for(int i = 0; i < toDraw.length; i++){
//				drawGrid(toDraw[i], 1, rhs.getOColor(), g);
//			}
		}
		if(rhs.getTypeName() == "Sphere"){
			Vector3D [][] value = rhs.getVerticesToCamera(0);
			Face2D [] toDraw = new Face2D[(value[0].length - 1) * (value.length - 1)];
			Point [] p = new Point[4];
			for(int i = 0, u = 0; i < value.length - 1; i++){
				for(int o = 0; o < value[0].length - 1; o++){					
					p[0] = evalScreenPos(value[i][o]);
					p[1] = evalScreenPos(value[i][o+1]);
					p[2] = evalScreenPos(value[i+1][o+1]);
					p[3] = evalScreenPos(value[i+1][o]);
					toDraw[u] = new Face2D(p[0], p[1], p[2], p[3]);
					u++;
				}
			}
			toDraw = mapToScreen(toDraw);
			for(int i = 0; i < toDraw.length; i++){
				g.setColor(rhs.getIColor());
				g.fillPolygon(this.getArrayX(toDraw[i]), this.getArrayY(toDraw[i]), 4);
			}
			for(int i = 0; i < toDraw.length; i++){
				g.setColor(rhs.getOColor());
				g.drawPolygon(this.getArrayX(toDraw[i]), this.getArrayY(toDraw[i]), 4);
			}
		}
	}
	public void run() {
		while(true){
//			((Object3D)(obj.get(0))).rotate(new Vector3D(3, 0, 3));
//			((Object3D)(obj.get(1))).rotate(new Vector3D(0, 3, 0));
//			((Object3D)(obj.get(2))).rotate(new Vector3D(0, 0, 3));
			((Object3D)(obj.get(3))).rotate(new Vector3D(2, 3, 4));
			((Object3D)(obj.get(4))).rotate(new Vector3D(9, 1, 0));
			repaint();
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void update(Graphics g){
		if(dbImage == null){
			dbImage = createImage(this.getSize().width,
								  this.getSize().height);
			dbGraphics = dbImage.getGraphics();
		}
		paint(dbGraphics);
		g.drawImage(dbImage, 0, 0, this);
	}
	public void mouseDragged(MouseEvent arg0) {
		float d = Math.abs(500 / cam.getLocal().z);
		int x = arg0.getX();
		int y = arg0.getY();
		x -= width/2;
		y -= height/2;
		y *= -1;
		if(reg == null)
			return;
		if(originLocal == null || originLookUp == null)
			return;
		x -= reg.x;
		y -= reg.y;
		
		switch(mode){
			case 1:		Vector3D temp1 = new Vector3D(reg.x + (float)x / d, reg.y + (float)y / d, 0);
						temp1.add(originLocal);
						cam.setLocal(temp1);
						System.out.println("reg x: "+reg.x+" y: "+reg.y+"    trans x: "+(x/d)+" y: "+(y)/d);
						break;
			case 2:		Vector3D temp2 = new Vector3D(0, 0, reg.x + (float)x / d);
						temp2.add(originLocal);
						System.out.println("reg x: "+reg.x+" y: "+reg.y+"    trans x: "+(x/d)+" y: "+(y)/d);
						cam.setLocal(temp2);
						break;
			case 3:		Vector3D temp3 = new Vector3D(reg.y + (float)y / d, reg.x + (float)x / d,  reg.y + (float)y / d);
						temp3.add(originLookUp);
						System.out.println("reg x: "+reg.x+" y: "+reg.y+"    trans x: "+(x/d)+" y: "+(y)/d);
						cam.setLookUp(temp3);
						break;
			default:	break;
		}
	}
	public void mouseMoved(MouseEvent arg0) {
		
	}
	public void keyTyped(KeyEvent arg0) {
		char k = arg0.getKeyChar();
		if(k == 'h'){
			cam.setLocal(0, 0, Camera.getSz() * -1);
			cam.setLookUp(new Vector3D(0, 0, 0));
		}
	}
	public void keyPressed(KeyEvent arg0) {
		int code = arg0.getKeyCode();
		switch(code){
			case 49:	mode = 1;
						System.out.println("key '1' pressed mode = "+mode);
						break;
			case 50:	mode = 2;
						System.out.println("key '2' pressed mode = "+mode);
						break;
			case 51:	mode = 3;
						System.out.println("key '3' pressed mode = "+mode);
						break;
			default:	mode = 0;
		}
	}
	public void keyReleased(KeyEvent arg0) {
		mode = 0;
		System.out.println("key released mode = "+mode);
	}
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mousePressed(MouseEvent arg0) {
		originLocal = cam.getLocal();
		originLookUp = cam.getLookUp();
		int x = arg0.getX();
		int y = arg0.getY();
		x -= width/2;
		y -= height/2;
		y *= -1;
		
		reg = new Point(x, y);
	}
	public void mouseReleased(MouseEvent arg0) {
		reg = null;
		originLocal = null;
		originLookUp = null;
	}
	public void mouseEntered(MouseEvent arg0) {

	}
	public void mouseExited(MouseEvent arg0) {
		
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
	private void drawAxis(Graphics g){
		float d = Camera.getSz() / 2;
		Vector3D [] regCross = new Vector3D[6];
		regCross[0] = new Vector3D(-d, 0, 0); 
		regCross[1] = new Vector3D(d, 0, 0); 
		regCross[2] = new Vector3D(0, -d, 0); 
		regCross[3] = new Vector3D(0, d, 0); 
		regCross[4] = new Vector3D(0, 0, -d); 
		regCross[5] = new Vector3D(0, 0, d);
		
		for(int i = 0; i < regCross.length; i++){
			regCross[i] = evalVerticeRot(regCross[i], Camera.getCam(0).getLookUp());
		}
		
		Point [] regCrossP = new Point[regCross.length];
		for(int i = 0; i < regCross.length; i++){
			regCrossP[i] = this.evalScreenPos(regCross[i]);
		}
		
		regCrossP = this.mapToScreen(regCrossP);
		
		Color [] c = new Color[3];
		c[0] = Color.RED;
		c[1] = Color.GREEN;
		c[2] = Color.BLUE;
		
		for(int i = 0; i < regCross.length; i += 2){
			g.setColor(c[i / 2]);
			g.drawLine(regCrossP[i].x, regCrossP[i].y ,regCrossP[i+1].x, regCrossP[i+1].y);
		}
	}
}
