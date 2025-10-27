import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class Person implements Comparable<Person>, Storable
{
    private String name;
    private LocalDate birthDate;
    private String gender;
    private int height;

    public Person(String name, LocalDate birthDate, String gender, int height)
    {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.height = height;
    }

    @Override
    public int compareTo(Person other)
    {
        if(this == other)
            return 0;
        if(this.name.compareTo(other.name) != 0)
            return this.name.compareTo(other.name);
        if(!this.birthDate.isEqual(other.birthDate))
            return this.birthDate.compareTo(other.birthDate);
        if(this.gender.compareTo(other.gender) != 0)
            return this.gender.compareTo(other.gender);
        if(this.height != other.height)
            return this.height - other.height;
        return 0;
    }

    @Override
    public String toString()
    {
        return this.name + ", " + this.birthDate + ", " + this.gender + ", " + this.height;
    }

    @Override
    public void store(String file) throws FileNotFoundException
    {
        PrintWriter pw = new PrintWriter(file);
        pw.println(this);
        pw.close();
    }
}
