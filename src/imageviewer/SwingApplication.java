package imageviewer;

import imageviewer.control.Command;
import imageviewer.control.NextCommand;
import imageviewer.control.OpenCommand;
import imageviewer.control.PrevCommand;
import imageviewer.persistence.FileImageLoader;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class SwingApplication extends JFrame{
    
    public static void main(String[] args) {
        new SwingApplication();
    }

    private final Map<String,Command> commands= new HashMap<>();
    private final SwingImagePanel swingImagePanel;
    private final FileImageLoader fileImageLoader;
    
    public SwingApplication() {
        this.fileImageLoader = new FileImageLoader();
        this.swingImagePanel = new SwingImagePanel(fileImageLoader.read());
        setCommands();
        deployUI();
    }

    private void setCommands() {
        commands.put("next", new NextCommand(swingImagePanel));
        commands.put("prev", new PrevCommand(swingImagePanel));
        commands.put("open", new OpenCommand(swingImagePanel,fileImageLoader));
    }

    private void deployUI() {
        this.setTitle("Image Viewer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(400, 400));
        this.getContentPane().add(swingImagePanel, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(1000, 800));
        this.pack();
        this.getContentPane().add(bottomMenu(), BorderLayout.SOUTH);
        this.setJMenuBar(menuBar());
        this.setVisible(true);
    }

    private JPanel bottomMenu() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(prevButton());
        panel.add(nextButton());
        return panel;
    }

    private JButton prevButton() {
        JButton button = new JButton("<");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.get("prev").execute();
            }
        });
        return button;
    }
    
    private JButton nextButton() {
        JButton button = new JButton(">");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.get("next").execute();
            }
        });
        return button;
    }

    private JMenuBar menuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu());
        return menuBar;
    }

    private JMenu fileMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(openOperation());
        return fileMenu;
    }

    private JMenuItem openOperation() {
        JMenuItem openItem = new JMenuItem("Open...");
        openItem.addActionListener(execute("open"));
        return openItem;
    }

    private ActionListener execute(String command) {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                commands.get(command).execute();
            }
        };
    }

}
