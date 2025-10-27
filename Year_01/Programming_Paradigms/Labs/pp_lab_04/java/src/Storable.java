import java.io.FileNotFoundException;

public interface Storable
{
    void store(String file) throws FileNotFoundException;
}
