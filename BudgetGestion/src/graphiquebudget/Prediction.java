package graphiquebudget;

import java.awt.BorderLayout;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import javax.swing.JPanel;
import models.OperationModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;


public class Prediction extends JPanel{

    public Prediction() {
        super();
        setLayout(new BorderLayout());
        DefaultCategoryDataset  dataset = new DefaultCategoryDataset ();
        OperationModel OM = new OperationModel();
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        LinkedHashMap<Integer,Double> prediction = new LinkedHashMap<>();
        for (int i = 0 ;i<12; i++) {
            ArrayList<Double> arr = OM.getAllYearByMonthByUser(i+1,1);
            if(arr.size() > 1 && (i != month-1 || arr.size() > 2)) {
                if (i == month-1) {
                    arr.remove(arr.size()-1);
                }
                double last = arr.get(0);
                double sum = 0;
                int j=1;
                for (j = 1 ; j<arr.size(); j++) {
                    sum += arr.get(j)/last;
                    last = arr.get(j);
                }
                prediction.put(i,(sum /((j-1)*1.0))*last);
            }
        }
        for (int i = 0 ; i<12;i++) {
            int curr = ((month - 1)+i)%12;
            if (prediction.get(curr) != null) {
                dataset.addValue(prediction.get(curr), "Prévision du mois" ,String.valueOf(curr+1));
            }
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
            "Prévisions des 12 prochains mois",
            "Mois",
            "Somme en €",
            dataset);
        
        ChartPanel graphique = new ChartPanel(chart);
        add(graphique, BorderLayout.CENTER);
    }
    
    
}
