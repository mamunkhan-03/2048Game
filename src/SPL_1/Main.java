package SPL_1;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame ();
        frame.setTitle("2048 made by mamaun");
        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null);
        
        ImageIcon image=new ImageIcon("project.png");
        frame.setIconImage(image.getImage());
        
        frame.add(new Game());
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
        
         
    }
    
}
