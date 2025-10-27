import java.util.Calendar;

public class Main {
    public static void main(String[] args) {
        University univ = new University("UNSTPB", "Romania");

        Author author1 = new Author("Alexandru-Mihail Ionita");
        univ.addAuthor(author1);
        Author author2 = new Author("Tiberiu-Adrian Petre");
        univ.addAuthor(author2);

        Calendar date = Calendar.getInstance();
        date.set(2023, Calendar.JANUARY, 1);

        Publication journal1 = new Journal("Journal11", date, 2, "Computer Science", 5.0);
        Publication journal2 = new Journal("Journal21", date, 3, "Physics", 4.0);
        author1.addPublication(journal1);
        author1.addPublication(journal2);

        Publication conf1 = new ConferenceProceeding("Conference11", date, 2, "Volume 1", true);
        Publication conf2 = new ConferenceProceeding("Conference12", date, 3, "Volume 2", false);
        author1.addPublication(conf1);
        author1.addPublication(conf2);

        Publication journal3 = new Journal("Journal 12", date, 2, "IEEE", 3.0);
        Publication journal4 = new Journal("Journal 22", date, 3, "ACM", 2.5);
        author2.addPublication(journal3);
        author2.addPublication(journal4);

        Publication conf3 = new ConferenceProceeding("Conference 21", date, 2, "Volume3", true);
        Publication conf4 = new ConferenceProceeding("Confeerence 22", date, 3, "Volume4", false);
        author2.addPublication(conf3);
        author2.addPublication(conf4);

        double universityScore = univ.computeScore();
        System.out.println("Computed University Score: " + universityScore);

        double expectedScore =
                (5.0 * 0.5 / 2 + 4.0 * 0.5 / 3 + 0.25 / 2 + 0.2 / 3) +
                        (3.0 * 0.5 / 2 + 2.5 * 0.5 / 3 + 0.25 / 2 + 0.2 / 3);
        System.out.println("Expected University Score: " + expectedScore);

        System.out.println("Scores match: " + (universityScore == expectedScore));
    }
}
