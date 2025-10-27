import java.util.ArrayList;
import java.util.List;

public class University {
    private String name;
    private String location;
    private List<Author> authors = new ArrayList<>();

    public University(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public void addAuthor(Author a){
        authors.add(a);
    }

    public double computeScore(){
        double totalScore = 0.0;
        for(Author a : authors){
            totalScore += a.computeScore();
        }
        return totalScore;
    }
}
