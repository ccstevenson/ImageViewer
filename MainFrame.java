import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame implements ActionListener {
	JPanel buttonPanel = new JPanel();
	JButton open, zoomin, zoomout, full, quit;
	JFileChooser chooser = new JFileChooser();
	MainFrame.ImageComponent component;
	BufferedImage image;
	
	double zoom = 1.0;
	double zoomfactor = .25;
	double original = 1.0;
	
	public MainFrame() {
		setTitle("Java Image Viewer");
		setSize(800, 800);
		
		open = new JButton("Open");
		zoomin = new JButton("Zoom In");
		zoomout = new JButton("Zoom Out");
		full = new JButton("100%");
		quit = new JButton("Quit");
		
		open.addActionListener(this);
		zoomin.addActionListener(this);
		zoomout.addActionListener(this);
		full.addActionListener(this);
		quit.addActionListener(this);
		
		buttonPanel.add(open);
		buttonPanel.add(zoomin);
		buttonPanel.add(zoomout);
		buttonPanel.add(full);
		buttonPanel.add(quit);
		add(buttonPanel, BorderLayout.PAGE_END);		
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == open) {
			image = null;
			chooser.setCurrentDirectory(new File("."));
			int result = chooser.showOpenDialog(MainFrame.this);
			String filename = chooser.getSelectedFile().getPath();
			component = this.new ImageComponent(filename);
			add(component);
			this.revalidate();
			this.repaint();
		}
		if (e.getSource() == zoomin) {
			zoom += zoomfactor;
			this.revalidate();
			this.repaint();
		}
		if (e.getSource() == zoomout) {
			zoom -= zoomfactor;
			this.revalidate();
			this.repaint();
		}
		if (e.getSource() == full) {
			zoom = original;
			this.revalidate();
			this.repaint();
		}
		if (e.getSource() == quit) {
			System.exit(0);
		}
	}
	
	private class ImageComponent extends JPanel { // Inner class for drawing image.
		int width; // original image dimensions.
		int height;
		int w; // modified image dimensions.
		int h;
		
		public ImageComponent(String filename) {
			try {
				image = ImageIO.read(new File(filename));
			}
			catch (IOException e)
			{
				System.out.println("Not a valid image file");
			}
		}
		public void paintComponent(Graphics g) {
			if (image == null) return;
			//super.paintComponent(g);
			
			width = image.getWidth(this);
			height = image.getHeight(this);
			
			w = (int) (width * zoom);
			h = (int) (height * zoom);
			
			g.drawImage(image, 0,  0, w, h, this);
			
		}
	}
}


