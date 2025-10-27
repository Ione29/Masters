import java.util.ArrayList;
import java.util.List;

public class Author {
    private String name;
    private List<Publication> publications = new ArrayList<>();

    public Author(String name) {
        this.name = name;
    }

    public void addPublication(Publication p){
        publications.add(p);
    }

    public double computeScore(){
        double totalScore = 0.0;
        for(Publication p : publications){
            totalScore += p.computeScore();
        }
        return totalScore;
    }
}
