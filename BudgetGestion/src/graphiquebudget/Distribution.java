package graphiquebudget;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import models.OperationModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class Distribution extends JPanel {
    
    private JComboBox granularite;
    private JComboBox periode;
    private JComboBox typeOperation;
    private ChartPanel graphique;
    
    public final static int TO_REVENUE = 0;
    public final static int TO_DEPENSE = 1;
    
    public Distribution() {
        super();
        setLayout(new BorderLayout());
        JPanel p = new JPanel(new FlowLayout());
        
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
        
        String[] per = OM.toutesLesAnneeDuUser(1);
        periode = new JComboBox(per);
        
        String[] typ = {"Revenues", "Dépenses"};
        typeOperation = new JComboBox(typ); 
        
        JButton b = new JButton("Filtrer");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFreeChart nG;
                if (granularite.getSelectedIndex() == 0) {
                    nG = getPieByAll(typeOperation.getSelectedIndex());
                } else {
                    nG = getPieByYear(periode.getSelectedItem().toString(), typeOperation.getSelectedIndex());
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
        p.add(b);
        add(p, BorderLayout.NORTH);
        
        graphique = new ChartPanel(getPieByYear(periode.getSelectedItem().toString(),TO_REVENUE));
        add(graphique, BorderLayout.CENTER);
    }
    
   
    
    private JFreeChart getPieByYear(String date,int typeOperation) {
        DefaultPieDataset  dataset = new DefaultPieDataset ();
        OperationModel OM = new OperationModel();
        LinkedHashMap<String,Double> data;
        String serie,charte;
        switch (typeOperation) {
            case TO_DEPENSE:
                data = OM.distributionMontantDepenseByYearAndUser(date,1);
                charte = "Distribution des Dépenses de l'année " + date + " : ";
                break;
            default:
                data = OM.distributionMontantRevenueByYearAndUser(date,1);
                charte = "Distribution des Revenues de l'année " + date + " : ";
                break;
        }
        double sum = 0;
        for(Map.Entry m:data.entrySet()){ 
            String a = m.getKey().toString();
            double b = Double.parseDouble(m.getValue().toString());
            dataset.setValue(a,b);
            sum += b;
        }

        JFreeChart chart = ChartFactory.createPieChart(
            charte + String.valueOf(sum) + " €",
            dataset,
            true, 
            true,
            false);
        
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
            "{0} : {1} € ({2})", NumberFormat.getInstance(), NumberFormat.getPercentInstance());
        ((PiePlot) chart.getPlot()).setLabelGenerator(labelGenerator);
        
        return chart;
    }
    
    private JFreeChart getPieByAll(int typeOperation) {
        DefaultPieDataset  dataset = new DefaultPieDataset ();
        OperationModel OM = new OperationModel();
        LinkedHashMap<String,Double> data;
        String serie,charte;
        switch (typeOperation) {
            case TO_DEPENSE:
                data = OM.distributionMontantDepenseByUser(1);
                charte = "Distribution des Dépenses : ";
                break;
            default:
                data = OM.distributionMontantRevenueByUser(1);
                charte = "Distribution des Revenues : ";
                break;
        }
        double sum = 0;
        for(Map.Entry m:data.entrySet()){ 
            String a = m.getKey().toString();
            double b = Double.parseDouble(m.getValue().toString());
            dataset.setValue(a,b);
            sum += b;
        }

        JFreeChart chart = ChartFactory.createPieChart(
            charte + String.valueOf(sum) + " €",
            dataset,
            true, 
            true,
            false);
        
        
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
            "{0} : {1} € ({2})", NumberFormat.getInstance(), NumberFormat.getPercentInstance());
        ((PiePlot) chart.getPlot()).setLabelGenerator(labelGenerator);
        
        return chart;
    }
    
    
}
