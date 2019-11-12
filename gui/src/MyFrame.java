import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class MyFrame extends JFrame implements ActionListener{
    Pathfinding pathfinding;

    MyFrame(int width, int height, int rows, int cols, Pathfinding pathfinding){
        setSize(width, height);
        setLayout(new GridLayout(rows, cols));
        setVisible(true);
        this.pathfinding = pathfinding;
        createMenuBar();
    }

    void createMenuBar(){
        JMenuBar menubar;
        JMenu menu, submenu;
        JMenuItem menuItem;

        menubar = new JMenuBar();
        menu = new JMenu("Input Mode");
        menu.addSeparator();
        menuItem = new JMenuItem("Start");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem("Wall");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem("End");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menubar.add(menu);

        menu = new JMenu("Algorithm");
        menuItem = new JMenuItem("Run");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem("A*");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem("Dijkstras");
        menuItem.addActionListener(this);
        menu.add(menuItem);
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
            pathfinding.runAlgo();
        }else if(cmd.equals("Dijkstras")){
            pathfinding.algo = "Dijkstras";
        }else if(cmd.equals("A*")){
            pathfinding.algo = "A*";
        }
    }
}

