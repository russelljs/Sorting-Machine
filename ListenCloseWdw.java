import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class ListenCloseWdw extends WindowAdapter{
	public void windowClosing(WindowEvent e){
		System.exit(0);         
	}
}
	  