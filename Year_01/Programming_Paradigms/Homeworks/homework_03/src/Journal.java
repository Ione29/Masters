import java.util.Calendar;

public class Journal extends Publication{
    private String journalName;
    private double impactFactor;

    public Journal(String name, Calendar apparition, int numberOfAuthors, String journalName, double impactFactor) {
        super(name, apparition, numberOfAuthors);
        this.journalName = journalName;
        this.impactFactor = impactFactor;
    }

    @Override
    public double computeScore() {
        return (impactFactor * 0.5) / this.getNumberOfAuthors();
    }
}
