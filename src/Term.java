import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Term {

    private String keyword;
    private Map<Integer, Integer> docs = new HashMap<>();

    public Term(String keyword, int docNum){
        this.keyword = keyword;
        increaseFreq(docNum);
    }

    public void increaseFreq(int docName){
        if(docs.containsKey(docName)) {
            docs.put(docName, docs.get(docName) + 1);
        }else{
            docs.put(docName, 1);
        }
    }

    public Map<Integer, Integer> getDocs() {
        return docs;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
