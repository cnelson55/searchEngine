import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


public class Tokenizer {

    private StopWords stopwords = new StopWords();


    public void scanDocuments() {
        File wiki = new File("wiki");
        int docNum = 0;
        File[] files = wiki.listFiles();
        Arrays.sort(files, new FileComparator());
        for (File file : files) {

            try{
                byte[] fileToBytes = Files.readAllBytes(Paths.get(file.getPath()));
                String fileToString = new String(fileToBytes);
                InverseIndex.getInstance().getDocList().add(fileToString);
                fileToString = fileToString.replaceAll("-", " ").toLowerCase();
                fileToString = fileToString.replaceAll("\\p{Punct}", "");
                Scanner scan = new Scanner(fileToString);
                tokenizeDocument(scan, docNum);
                scan.close();
            }catch (FileNotFoundException e){
                System.out.println("File not Found");
            }catch (IOException e){
                System.out.println("Bad Path");
            }
            docNum++;

        }
    }

    public void tokenizeDocument(Scanner scan, int docNum){
        PorterStemmer stemmer = new PorterStemmer();
        while(scan.hasNext()){
            String token = scan.next();
            if(!stopwords.contains(token)){
                token = stemmer.stem(token);
                Term term = new Term(token, docNum);

                InverseIndex.getInstance().insert(term, docNum);
            }
        }
    }

    public class FileComparator implements Comparator<File> {
        @Override
        public int compare(File o1, File o2) {
            return Integer.parseInt(o1.getPath().replaceAll("[^0-9]", "")) - Integer.parseInt(o2.getPath().replaceAll("[^0-9]", ""));

        }
    }
}
