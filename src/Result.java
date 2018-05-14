public class Result {

    private int docNum;
    private double score;
    private String snippet;

    public Result(int docNum, double score, String snippet) {
        this.docNum = docNum;
        this.score = score;
        this.snippet = snippet;
    }

    public double getScore() {
        return score;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Doc ID: ");
        sb.append(docNum + 1);
        sb.append("\tRank Score: ");
        sb.append(score);
        sb.append("\n");
        sb.append(snippet);
        sb.append("\n\n");
        return sb.toString();
    }
}
