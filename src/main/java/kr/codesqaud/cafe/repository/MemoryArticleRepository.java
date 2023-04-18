package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private static Long id = 1L;
    private final Map<Long, Article> repository = new ConcurrentHashMap();

    @Override
    public void save(Article input) {
        Article article = Article.builder()
                .id(id)
                .writer(input.getWriter())
                .title(input.getTitle())
                .contents(input.getContents())
                .writtenTime(LocalDateTime.now())
                .build();

        id += 1;
        repository.put(article.getId(), article);
    }

    @Override
    public List<Article> findAll() {
        List<Article> articles = new ArrayList<>(repository.values());

        Collections.reverse(articles);
        return Collections.unmodifiableList(articles);
    }

    @Override
    public Article findById(long id) {
        return repository.get(id);
    }
}
