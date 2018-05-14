import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InverseIndex {

    private Map<String, Term> termList = new HashMap<>();
    private int[] freqList = new int[322];
    private ArrayList<String> docList = new ArrayList<>();


    private static InverseIndex instance;

    private InverseIndex(){
        Arrays.fill(freqList, 1);
    }

    public static InverseIndex getInstance(){

        if(instance == null){
            instance = new InverseIndex();
        }

        return instance;

    }

    public void insert(Term term, int docNum){
        if(termList.containsKey(term.getKeyword())){
            termList.get(term.getKeyword()).increaseFreq(docNum);
        }else{
            termList.put(term.getKeyword(), term);
        }
        if(freqList[docNum] < termList.get(term.getKeyword()).getDocs().get(docNum)){
            freqList[docNum] = termList.get(term.getKeyword()).getDocs().get(docNum);
        }
    }

    public ArrayList<String> getDocList() {
        return docList;
    }

    public Map<Integer, Integer> getTermDocs(String keyword) {
        if(termList.containsKey(keyword)) {
            return termList.get(keyword).getDocs();
        }else{
            return null;
        }
    }

    public int getHighFreq(int docNum){
        return freqList[docNum];
    }
}
