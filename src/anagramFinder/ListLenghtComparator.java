package anagramFinder;

import java.util.Comparator;
import java.util.List;

public class ListLenghtComparator implements Comparator<List<String>> {

    @Override
    public int compare(List<String> list1, List<String> list2) {

        return Integer.compare(list2.size(), list1.size());
    }


}
