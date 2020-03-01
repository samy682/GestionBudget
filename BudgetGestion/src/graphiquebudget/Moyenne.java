package graphiquebudget;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import models.OperationModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


public class Moyenne extends JPanel {
    
    private final JComboBox typeOperation;
    private JComboBox categories;
    private ChartPanel graphique;
    
    public final static int TO_ALL = 0;
    public final static int TO_REVENUE = 1;
    public final static int TO_DEPENSE = 2;
    
    public Moyenne() {
        super();
        setLayout(new BorderLayout());
        JPanel p = new JPanel(new FlowLayout());
        
        OperationModel OM = new OperationModel();
        
        String[] typ = {"Toutes les opérations", "Revenues", "Dépenses"};
        typeOperation = new JComboBox(typ); 
        
        typeOperation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] cat;
                if (typeOperation.getSelectedIndex() == 0) {
                    cat = OM.toutesLesCategorieDuUser(1);
                } else if (typeOperation.getSelectedIndex() == 1) {
                    cat = OM.toutesLesCategorieRevenueDuUser(1);
                } else {
                    cat = OM.toutesLesCategorieDepenseDuUser(1);
                }
                DefaultComboBoxModel model = new DefaultComboBoxModel( cat );
                categories.setModel(model);
            }
        });
        
        String[] cat = OM.toutesLesCategorieDuUser(1);
        categories = new JComboBox(cat);
        
        JButton b = new JButton("Filtrer");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFreeChart nG;
                if (categories.getSelectedIndex() == 0) {
                    nG = getAverageAllCategories(typeOperation.getSelectedIndex());
                } else {
                    nG = getAverageWithCategorie(categories.getSelectedItem().toString(), typeOperation.getSelectedIndex());
                }
                graphique = new ChartPanel(nG);
                BorderLayout layout = (BorderLayout)getLayout();
                remove(layout.getLayoutComponent(BorderLayout.CENTER));
                add(graphique, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });
        
        p.add(new JLabel("Type d'opération : "));
        p.add(typeOperation);
        p.add(new JLabel("Catégories : "));
        p.add(categories);
        p.add(b);
        add(p, BorderLayout.NORTH);
        
        
         
        graphique = new ChartPanel(  getAverageAllCategories(TO_ALL)    );
        add(graphique, BorderLayout.CENTER);
    }
    
    private JFreeChart getAverageAllCategories(int typeOperation) {
        DefaultCategoryDataset  dataset = new DefaultCategoryDataset ();
        OperationModel OM = new OperationModel();
        LinkedHashMap<Integer,Double> data;
        String serie,charte;
        switch (typeOperation) {
            case TO_REVENUE:
                data = OM.moyenneMontantRevenueByUser(1);
                serie = "Moyenne par mois de tous les revenues";
                charte = "Moyenne des Revenues";
                break;
            case TO_DEPENSE:
                data = OM.moyenneMontantDepenseByUser(1);
                serie = "Moyenne par mois de toutes les dépenses";
                charte = "Moyenne des Dépenses";
                break;
            default:
                data = OM.moyenneMontantByUser(1);
                serie = "Moyenne par mois de toutes les opérations";
                charte = "Moyenne des Opérations";
                break;
        }
        
        for(Map.Entry m:data.entrySet()){ 
            String a = m.getKey().toString();
            double b = Double.parseDouble(m.getValue().toString());
            dataset.addValue(b, serie ,a);
        }

        JFreeChart chart = ChartFactory.createBarChart(
            charte,
            "Mois",
            "Moyenne en €",
            dataset);
        return chart;
    }
    
    private JFreeChart getAverageWithCategorie(String cat, int typeOperation) {
        DefaultCategoryDataset  dataset = new DefaultCategoryDataset ();
        OperationModel OM = new OperationModel();
        LinkedHashMap<Integer,Double> data;
        String serie,charte;
        switch (typeOperation) {
            case TO_REVENUE:
                data = OM.moyenneMontantRevenueByCategorieAndUser( cat, 1);
                serie = "Moyenne par mois des revenues \""+ cat + "\"";
                charte = "Moyenne des Revenues \""+ cat + "\"";
                break;
            case TO_DEPENSE:
                data = OM.moyenneMontantDepenseByCategorieAndUser( cat, 1);
                serie = "Moyenne par mois des dépenses \""+ cat + "\"";
                charte = "Moyenne des Dépenses \""+ cat + "\"";
                break;
            default:
                data = OM.moyenneMontantByCategorieAndUser( cat, 1);
                serie = "Moyenne par mois des opérations \""+ cat + "\"";
                charte = "Moyenne des Opérations \""+ cat + "\"";
                break;
        }
        
        for(Map.Entry m:data.entrySet()){ 
            String a = m.getKey().toString();
            double b = Double.parseDouble(m.getValue().toString());
            dataset.addValue(b, serie ,a);
        }

        JFreeChart chart = ChartFactory.createBarChart(
            charte,
            "Mois",
            "Moyenne en €",
            dataset);
        return chart;
    }
    
}
