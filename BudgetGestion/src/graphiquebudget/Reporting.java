package graphiquebudget;

import javax.swing.JTabbedPane;


public class Reporting extends JTabbedPane{

    public Reporting() {
        super();
        
        Graphique p1 = new Graphique();
        Moyenne p2 = new Moyenne();
        Distribution p3 = new Distribution();
        
        addTab("Graphique", p1);
        addTab("Moyenne", p2);
        addTab("Distribution", p3);
    }
    
}
