import java.util.Random;

/**
 * Created by JLx_F on 19.12.2016.
 */
public class   PulseDummy {

    public int randNumb() {

        Random r = new Random();
        int i1 = r.nextInt(80 - 65) + 65;

        return i1;
    }
}
