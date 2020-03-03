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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;


import models.OperationModel;
import org.jfree.data.category.DefaultCategoryDataset;

import Utils.Session;

public class Graphique extends JPanel {
    
    private final JComboBox granularite;
    private JComboBox periode;
    private final JComboBox typeOperation;
    private JComboBox categories;
    private ChartPanel graphique;
    private int iduser;
    
    public final static int TO_ALL = 0;
    public final static int TO_REVENUE = 1;
    public final static int TO_DEPENSE = 2;
    
    public Graphique() {
        super();
        setLayout(new BorderLayout());
        JPanel p = new JPanel(new FlowLayout());
        
        Session sess = Session.getInstance();
        iduser = sess.us.getId();
        OperationModel OM = new OperationModel();
        
        String[] gra = {"Intégralité", "Annuelle"};
        granularite = new JComboBox(gra); 
        granularite.setSelectedIndex(1);
        
        granularite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (granularite.getSelectedIndex() == 0) {
                    periode.setEnabled(false);
                } else {
                    periode.setEnabled(true);
                }
            }
        });
        
        String[] per = OM.toutesLesAnneeDuUser(iduser);
        periode = new JComboBox(per);
        
        String[] typ = {"Toutes les opérations", "Revenues", "Dépenses"};
        typeOperation = new JComboBox(typ); 
        
        typeOperation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] cat;
                if (typeOperation.getSelectedIndex() == 0) {
                    cat = OM.toutesLesCategorieDuUser(iduser);
                } else if (typeOperation.getSelectedIndex() == 1) {
                    cat = OM.toutesLesCategorieRevenueDuUser(iduser);
                } else {
                    cat = OM.toutesLesCategorieDepenseDuUser(iduser);
                }
                DefaultComboBoxModel model = new DefaultComboBoxModel( cat );
                categories.setModel(model);
            }
        });
        
        String[] cat = OM.toutesLesCategorieDuUser(iduser);
        categories = new JComboBox(cat);
        
        JButton b = new JButton("Filtrer");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFreeChart nG;
                if (granularite.getSelectedIndex() == 0) {
                    if (categories.getSelectedIndex() == 0) {
                        nG = getGraphByYearAllCategories(typeOperation.getSelectedIndex());
                    } else {
                        nG = getGraphByYearWithCategorie(categories.getSelectedItem().toString(), typeOperation.getSelectedIndex());
                    }
                } else {
                    if (categories.getSelectedIndex() == 0) {
                        nG = getGraphByMonthAllCategories(periode.getSelectedItem().toString(), typeOperation.getSelectedIndex());
                    } else {
                        nG = getGraphByMonthWithCategorie(periode.getSelectedItem().toString(), categories.getSelectedItem().toString(),typeOperation.getSelectedIndex());
                    }
                }
                graphique = new ChartPanel(nG);
                BorderLayout layout = (BorderLayout)getLayout();
                remove(layout.getLayoutComponent(BorderLayout.CENTER));
                add(graphique, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });
        
        p.add(new JLabel("Granularité : "));
        p.add(granularite);
        p.add(new JLabel("Période : "));
        p.add(periode);
        p.add(new JLabel("Type d'opération : "));
        p.add(typeOperation);
        p.add(new JLabel("Catégories : "));
        p.add(categories);
        p.add(b);
        add(p, BorderLayout.NORTH);
       
        graphique = new ChartPanel(  getGraphByMonthAllCategories(periode.getSelectedItem().toString(),   TO_ALL)    );
        add(graphique, BorderLayout.CENTER);
    }
    
    private JFreeChart getGraphByMonthAllCategories(String date,int typeOperation) {
        DefaultCategoryDataset  dataset = new DefaultCategoryDataset ();
        OperationModel OM = new OperationModel();
        LinkedHashMap<Integer,Double> data;
        String serie,charte;
        switch (typeOperation) {
            case TO_REVENUE:
                data = OM.sommeMontantRevenueByMonthAndUser(date,iduser);
                serie = "Somme par mois de tous les revenues de l'année " + date;
                charte = "Somme des Revenues";
                break;
            case TO_DEPENSE:
                data = OM.sommeMontantDepenseByMonthAndUser(date,iduser);
                serie = "Somme par mois de toutes les dépenses de l'année " + date;
                charte = "Somme des Dépenses";
                break;
            default:
                data = OM.sommeMontantByMonthAndUser(date,iduser);
                serie = "Somme par mois de toutes les opérations de l'année " + date;
                charte = "Somme des Opérations";
                break;
        }
        
        for(Map.Entry m:data.entrySet()){ 
            String a = m.getKey().toString();
            double b = Double.parseDouble(m.getValue().toString());
            dataset.addValue(b, serie ,a);
        }

        JFreeChart chart = ChartFactory.createLineChart(
            charte,
            "Mois de l'année " + date,
            "Somme en €",
            dataset);
        return chart;
    }
    
    private JFreeChart getGraphByMonthWithCategorie(String date, String cat, int typeOperation) {
        DefaultCategoryDataset  dataset = new DefaultCategoryDataset ();
        OperationModel OM = new OperationModel();
        LinkedHashMap<Integer,Double> data;
        String serie,charte;
        switch (typeOperation) {
            case TO_REVENUE:
                data = OM.sommeMontantRevenueByMonthAndCategorieAndUser(date, cat, iduser);
                serie = "Somme par mois des revenues \""+ cat + "\" de l'année " + date;
                charte = "Somme des Revenues \""+ cat + "\"";
                break;
            case TO_DEPENSE:
                data = OM.sommeMontantDepenseByMonthAndCategorieAndUser(date, cat, iduser);
                serie = "Somme par mois des dépenses \""+ cat + "\" de l'année " + date;
                charte = "Somme des Dépenses \""+ cat + "\"";
                break;
            default:
                data = OM.sommeMontantByMonthAndCategorieAndUser(date, cat, iduser);
                serie = "Somme par mois des opérations \""+ cat + "\" de l'année " + date;
                charte = "Somme des Opérations \""+ cat + "\"";
                break;
        }
        
        for(Map.Entry m:data.entrySet()){ 
            String a = m.getKey().toString();
            double b = Double.parseDouble(m.getValue().toString());
            dataset.addValue(b, serie ,a);
        }

        JFreeChart chart = ChartFactory.createLineChart(
            charte,
            "Mois de l'année " + date,
            "Somme en €",
            dataset);
        return chart;
    }
    
    private JFreeChart getGraphByYearAllCategories(int typeOperation) {
        DefaultCategoryDataset  dataset = new DefaultCategoryDataset ();
        OperationModel OM = new OperationModel();
        LinkedHashMap<Integer,Double> data;
        String serie,charte;
        switch (typeOperation) {
            case TO_REVENUE:
                data = OM.sommeMontantRevenueByYearAndUser(iduser);
                serie = "Somme par année de tous les revenues";
                charte = "Somme des Revenues";
                break;
            case TO_DEPENSE:
                data = OM.sommeMontantDepenseByYearAndUser(iduser);
                serie = "Somme par année de toutes les dépenses";
                charte = "Somme des Dépenses";
                break;
            default:
                data = OM.sommeMontantByYearAndUser(iduser);
                serie = "Somme par année de toutes les opérations";
                charte = "Somme des Opérations";
                break;
        }
        for(Map.Entry m:data.entrySet()){ 
            String a = m.getKey().toString();
            double b = Double.parseDouble(m.getValue().toString());
            dataset.addValue(b, serie ,a);
        }

        JFreeChart chart = ChartFactory.createLineChart(
            charte,
            "Année",
            "Somme en €",
            dataset);
        return chart;
    }
    
    private JFreeChart getGraphByYearWithCategorie(String cat, int typeOperation) {
        DefaultCategoryDataset  dataset = new DefaultCategoryDataset ();
        OperationModel OM = new OperationModel();
        LinkedHashMap<Integer,Double> data;
        String serie,charte;
        switch (typeOperation) {
            case TO_REVENUE:
                data = OM.sommeMontantRevenueByYearAndCategorieAndUser(cat, iduser);
                serie = "Somme par année des revenues \""+ cat + "\"";
                charte = "Somme des Revenues \""+ cat + "\"";
                break;
            case TO_DEPENSE:
                data = OM.sommeMontantDepenseByYearAndCategorieAndUser(cat, iduser);
                serie = "Somme par année des dépenses \""+ cat + "\"";
                charte = "Somme des Dépenses \""+ cat + "\"";
                break;
            default:
                data = OM.sommeMontantByYearAndCategorieAndUser(cat, iduser);
                serie = "Somme par année des opérations \""+ cat + "\"";
                charte = "Somme des Opérations \""+ cat + "\"";
                break;
        }
        for(Map.Entry m:data.entrySet()){ 
            String a = m.getKey().toString();
            double b = Double.parseDouble(m.getValue().toString());
            dataset.addValue(b, serie ,a);
        }

        JFreeChart chart = ChartFactory.createLineChart(
            charte,
            "Année",
            "Somme en €",
            dataset);
        return chart;
    }
    
    
}
