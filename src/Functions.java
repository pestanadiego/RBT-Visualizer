import com.csvreader.CsvWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Functions {
    public void writeCsv(RedBlackTree population){
        try{
            CsvWriter csvwriter = new CsvWriter("test/habitantes.csv");
            String[] title = {"nombre","Apellido","CI"};
            csvwriter.writeRecord(title);
            if (!population.isEmpty()) {
                preorderCsv(population, population.root, csvwriter);
            }
            csvwriter.close();
            JOptionPane.showMessageDialog(null, "Success");
        }catch(Exception err){
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
    
    public void preorderCsv(RedBlackTree population, Node root, CsvWriter csvwriter) throws IOException {
        if (root != null) {
            String[] populationCsv = {root.name, root.surname, String.valueOf(root.data)};
            if(root != population.TNULL) {
            csvwriter.writeRecord(populationCsv);
            }
            preorderCsv(population, root.left, csvwriter);
            preorderCsv(population, root.right, csvwriter);
        }
    }

    public RedBlackTree readCsv(){
        RedBlackTree population = new RedBlackTree();
        String line;
        String populationCsv = "";
        String path = "test/habitantes.csv";
        File file = new File(path);
        try{
            if (!file.exists()) {
                file.createNewFile();
            }else{
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                while((line = br.readLine()) != null){
                    if (!line.isEmpty()) {
                        populationCsv += line + "\n";
                    }
                }
                if (!"".equals(populationCsv)) {
                    String[] populationSplit = populationCsv.split("\n");
                    for (int i = 0; i < populationSplit.length; i++) {
                        if (!populationSplit[i].equals("nombre,Apellido,CI")) {
                            String[] habitant = populationSplit[i].split(",");
                            population.insert(habitant[0], habitant[1], Integer.parseInt(habitant[2]));
                        }
                    }
                }
                br.close();
                JOptionPane.showMessageDialog(null, "Success"); 
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Error");
        }
        return population;
    }
}
