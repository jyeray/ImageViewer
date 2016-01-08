package imageviewer.persistence;

import imageviewer.model.Image;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileImageLoader implements ImageLoader{

    private File[] files;
    private final static String[] ImageExtensions = {"jpg","jpeg","png"};

    public FileImageLoader() {}
        
    private File selectImage(){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter(null, ImageExtensions));
        chooser.showOpenDialog(null);
        return chooser.getSelectedFile();
    }
    
    private void loadImagesOnFolder(String path){
        File folder= new File(path);
        files = folder.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                for (String extension : ImageExtensions)
                    if (name.toLowerCase().endsWith("." + extension)) return true;
                return false;
            }
        });
    }

    @Override
    public Image read() {
        File image = selectImage();
        loadImagesOnFolder(image.getParent());
        int index = 0;
        while (index < files.length-1 && !image.getAbsolutePath().equals(files[index].getAbsolutePath()))
            index++;
        return image(index);
    }
    
    public Image readThis(String imagePath){
        File image = new File (imagePath);
        loadImagesOnFolder(image.getParent());
        int index = 0;
        while (index < files.length-1 && !image.getAbsolutePath().equals(files[index].getAbsolutePath()))
            index++;
        return image(index);
    }

    private Image image(int index) {
        return new Image() {

            @Override
            public Object getBitmap() {
                try {
                    return ImageIO.read(files[index]);
                } catch (IOException e) {
                    return null;
                }
            }

            @Override
            public Image getPrev() {
                return image(index > 0 ? index-1 : files.length-1);
            }

            @Override
            public Image getNext() {
                return image(index < files.length-1 ? index+1 : 0 );
            }
        };
    }
}
