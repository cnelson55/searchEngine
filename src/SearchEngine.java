import java.util.*;

public class SearchEngine {

    private Map<Integer, Double> scores = new HashMap<>();
    private ArrayList<Result> results = new ArrayList<>();

    public static void main(String[] args){
        StopWords stopWords = new StopWords();
        SearchEngine se = new SearchEngine();
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.scanDocuments();
        Scanner scan = new Scanner(System.in);
        while(true) {
            String query = scan.nextLine();
            query = query.toLowerCase();
            Scanner scan2 = new Scanner(query);
            while (scan2.hasNext()) {
                String queryWord = scan2.next();
                if(!stopWords.contains(queryWord)) {
                    PorterStemmer stemmer = new PorterStemmer();
                    queryWord = stemmer.stem(queryWord);
                    se.generateScores(queryWord);
                }
            }
            se.sortScores();
            se.scores.clear();
            se.results.clear();
        }
    }

    public void generateScores(String queryWord){
        Map<Integer, Integer> termDocs = InverseIndex.getInstance().getTermDocs(queryWord);
        if(termDocs == null){
            return;
        }
        int numberOfDocs = InverseIndex.getInstance().getDocList().size();
        int docsWithQuery = termDocs.size();
        double IDF = (Math.log(numberOfDocs/docsWithQuery) / Math.log(2));


        for (Map.Entry<Integer, Integer> doc : termDocs.entrySet()){
            double TF = (double)doc.getValue()/(double)InverseIndex.getInstance().getHighFreq(doc.getKey());
            double score = TF * IDF;
            insertScore(doc.getKey(), score);
        }

    }

    private void insertScore(int docNum, double score){
        if(scores.containsKey(docNum)){
            scores.put(docNum, scores.get(docNum) + score);
        }else{
            scores.put(docNum, score);
        }
    }

    private void sortScores(){
        for(Map.Entry<Integer, Double> score : scores.entrySet()){
            String snippet = InverseIndex.getInstance().getDocList().get(score.getKey());
            snippet = snippet.substring(0, snippet.indexOf('.') + 1);
            Result result = new Result(score.getKey(), score.getValue(), snippet);
            results.add(result);
        }
            results.sort(new ResultComparator());
            results.subList(15, results.size()).clear();
            for(Result result : results){
                System.out.print(result.toString());
            }
    }

    public class ResultComparator implements Comparator<Result>{

        @Override
        public int compare(Result o1, Result o2) {
            if(o1.getScore() > o2.getScore()){
                return -1;
            }
            if(o1.getScore() < o2.getScore()){
                return 1;
            }
            else{
                return 0;
            }
        }
    }

}
