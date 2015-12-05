package imageviewer;

import imageviewer.model.Image;
import imageviewer.ui.ImageDisplay;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class SwingImagePanel extends JPanel implements ImageDisplay {
    
    private Image image;
    private int imageHeight = 0;
    private int imageWidth = 0;
    private int originalImageHeight;
    private int originalImageWidth;
    private double zoom;

    public SwingImagePanel(Image image) {
        super();
        this.image = image;
        this.getDefaultSizes();
        this.repaint();
    }
    
    private void getDefaultSizes(){
        BufferedImage img = (BufferedImage) this.image.getBitmap();
        originalImageHeight=img.getHeight();
        originalImageWidth=img.getWidth();
        this.zoom = 1.0;
    }
    
    @Override
    public void resize(){
        int difHeight = originalImageHeight - this.getHeight();
        int difWidth = originalImageWidth - this.getWidth();
        double aspectRatio = (double)originalImageHeight/originalImageWidth;
        if (difHeight <= 0 && difWidth <= 0){
            this.imageHeight = originalImageHeight;
            this.imageWidth = originalImageWidth;
        }else{
            this.imageHeight = this.getHeight();
            this.imageWidth = (int) (this.imageHeight/aspectRatio);
            if (imageWidth > this.getWidth()){
                this.imageWidth =  this.getWidth();
                this.imageHeight = (int) (this.imageWidth * aspectRatio); 
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        rezise();
//        int centerHeight = this.getHeight()/2 - imageHeight/2; 
//        int centerWidth = this.getWidth()/2 - imageWidth/2; 
//        g.drawImage((BufferedImage) image.getBitmap(), centerWidth, centerHeight, imageWidth, imageHeight, this);
        if(imageHeight == 0 || imageWidth == 0){
            this.resize();
        }
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        int centerHeight = this.getHeight()/2 - imageHeight/2; 
        int centerWidth = this.getWidth()/2 - imageWidth/2; 
        g2.translate(this.getWidth()/2, this.getHeight()/2);
        g2.scale(zoom, zoom);
        g2.translate(-(this.getWidth()/2), -(this.getHeight()/2));
        g2.drawImage((BufferedImage) image.getBitmap(), centerWidth, centerHeight, imageWidth, imageHeight, this);

    }
    
    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public void nextImage() {
        this.image = image.getNext();
        this.getDefaultSizes();
        this.resize();
        this.repaint();
    }

    @Override
    public void prevImage() {
        this.image = image.getPrev();
        this.getDefaultSizes();
        this.resize();
        this.repaint();
    }

    @Override
    public void show(Image image) {
        this.image = image;
        this.getDefaultSizes();
        this.resize();
        this.repaint();
    }

    @Override
    public void zoomIn() {
        if (zoom < 5.0) {
            this.zoom = zoom + 0.2;
            this.repaint();
        }
    }

    @Override
    public void zoomOut() {
        if (zoom > 1.0) {
            this.zoom = zoom - 0.2;
            this.repaint();
        }
    }
}
