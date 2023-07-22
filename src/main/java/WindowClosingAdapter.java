import java.awt.event.*;

public class WindowClosingAdapter extends WindowAdapter {
	private boolean exitSystem;
	
	public WindowClosingAdapter(boolean rhs){
		this.exitSystem = rhs;
	}
	public WindowClosingAdapter(){
		this(false);
	}
	public void WindowClosing(WindowEvent e){
		e.getWindow().setVisible(false);
		e.getWindow().dispose();
		if(exitSystem){
			System.exit(0);
		}
	}
}
