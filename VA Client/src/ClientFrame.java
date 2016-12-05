import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Handles the Client Frame
 * @author Vencillio
 *
 */
public final class ClientFrame extends JFrame {

	private final ClientEngine applet;
	public Toolkit toolkit = Toolkit.getDefaultToolkit();
	public Dimension screenSize = toolkit.getScreenSize();
	public int screenWidth = (int) screenSize.getWidth();
	public int screenHeight = (int) screenSize.getHeight();
	protected final Insets insets;
	private static final long serialVersionUID = 1L;

	public ClientFrame(ClientEngine applet, int width, int height, boolean resizable, boolean fullscreen) {
		this.applet = applet;
		
		//setTheme();
		setLogo();
		
		setTitle((Configuration.economyWorld ? "Vencillio" : "VencillioPk") + " - Who needs a slogan?");
		setResizable(resizable);
		setUndecorated(fullscreen);
		setVisible(true); 
		insets = getInsets();
		if (resizable) {
			setMinimumSize(new Dimension(766 + insets.left + insets.right, 555 + insets.top + insets.bottom));
		}
		
		isFocused();
		setFocusTraversalKeysEnabled(false);
		setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
		setLocationRelativeTo(null);
		setBackground(Color.BLACK);
		requestFocus();
		toFront();
	}
	
	private void setLogo() {		
		URL url2 = null;
        boolean update = true;
        try {
            url2 = new URL("http://www.vencillio.com/Media/icon.png");
        } catch (MalformedURLException ex) {
            update = false;
            ex.printStackTrace();
        }
        if (update) {
            Image bimg = Toolkit.getDefaultToolkit().getImage(url2);
            this.setIconImage(bimg);
        }		
	}
	
	public void setTheme() {
		try {
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			/*org.jvnet.substance.skin.SubstanceRavenGraphiteLookAndFeel lookAndFeel = new org.jvnet.substance.skin.SubstanceRavenGraphiteLookAndFeel();
			UIManager.setLookAndFeel(lookAndFeel);
			SubstanceLookAndFeel.setCurrentWatermark(new SubstanceNoneWatermark());
			JDialog.setDefaultLookAndFeelDecorated(true);
			System.out.println("22");*/
		} catch (Exception e2) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
		}
	}

	public Graphics getGraphics() {
		final Graphics graphics = super.getGraphics();
		Insets insets = this.getInsets();
		graphics.fillRect(0, 0, getWidth(), getHeight());
		graphics.translate(insets != null ? insets.left : 0, insets != null ? insets.top : 0);
		return graphics;
	}

	public int getFrameWidth() {
		Insets insets = this.getInsets();
		return getWidth() - (insets.left + insets.right);
	}

	public int getFrameHeight() {
		Insets insets = this.getInsets();
		return getHeight() - (insets.top + insets.bottom);
	}

	public void update(Graphics graphics) {
		applet.update(graphics);
	}

	public void paint(Graphics graphics) {
		applet.paint(graphics);
	}
}