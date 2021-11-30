package searcher;

public class SearcherFactory {
    public static Searcher createSearcher() {
        return new SearcherImpl();
    }
}
