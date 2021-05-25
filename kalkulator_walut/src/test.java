import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class test {
    @Test
    public void listShouldHaveElements(){
        ArrayList<String> currency = new ArrayList<>();
        ArrayList<String> rate = new ArrayList<>();
        Reader reader = new Reader();
        reader.getxml(currency, rate);
        Assert.assertTrue(currency.size() > 0);

    }
}
