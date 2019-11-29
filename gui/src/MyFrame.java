import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

class MyFrame extends JFrame implements ActionListener{
    Pathfinding pathfinding;
    Timer timer;
    MyFrame(int width, int height, int rows, int cols, Pathfinding pathfinding){
        setSize(width, height);
        setLayout(new GridLayout(rows, cols));
        setVisible(true);
        this.pathfinding = pathfinding;
        createMenuBar();

        // timer = new Timer(500, this);
    }

    void createMenuBar(){
        JMenuBar menubar;
        JMenu menu, submenu;
        JMenuItem menuItem;

        String[] firstMenuItems = {"Start", "Wall", "End"};
        String[] secondMenuItems = {"Run", "A*", "Dijkstras"};

        menubar = new JMenuBar();
        menu = new JMenu("Input Mode");
        for (String item : firstMenuItems){
            menuItem = new JMenuItem(item);
            menuItem.addActionListener(this);
            menu.add(menuItem);
            menu.addSeparator();
        }
        menubar.add(menu);

        menu = new JMenu("Algorithm");
        for(String item : secondMenuItems){
            menuItem = new JMenuItem(item);
            menuItem.addActionListener(this);
            menu.add(menuItem);
            menu.addSeparator();
        }
        menubar.add(menu);

        this.setJMenuBar(menubar);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if(cmd.equals("Start")){
            pathfinding.inputMode = 1;
        }else if(cmd.equals("Wall")){
            pathfinding.inputMode = 2;
        }else if(cmd.equals("End")){
            pathfinding.inputMode = 3;
        }else if(cmd.equals("Run")){
            // timer.setInitialDelay(500);
            // timer.start();
            pathfinding.runAlgo();
        }else if(cmd.equals("Dijkstras")){
            pathfinding.algo = "Dijkstras";
        }else if(cmd.equals("A*")){
            pathfinding.algo = "A*";
        }
    }
}

