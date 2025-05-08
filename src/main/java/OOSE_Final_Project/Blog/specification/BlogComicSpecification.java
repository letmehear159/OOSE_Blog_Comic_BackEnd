package OOSE_Final_Project.Blog.specification;

import OOSE_Final_Project.Blog.entity.Category;
import OOSE_Final_Project.Blog.entity.Tag;
import OOSE_Final_Project.Blog.entity.blog.BlogComic;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class BlogComicSpecification {

    public static Specification<BlogComic> hasAllCategories(List<Long> categoryIds) {
        return (root, query, cb) -> {
            // Avoid duplicates in result
            query.distinct(true);

            // Join to categories
            Join<BlogComic, Category> categoryJoin = root.join("categories");

            // Filter by categoryIds
            Predicate categoryPredicate = categoryJoin.get("id")
                                                      .in(categoryIds);

            // Group by blog id
            query.groupBy(root.get("id"));

            // Having COUNT(DISTINCT category_id) = categoryIds.size()
            query.having(cb.equal(cb.countDistinct(categoryJoin.get("id")), categoryIds.size()));

            // Return the final condition
            return cb.and(categoryPredicate);
        };
    }

    public static Specification<BlogComic> hasAllTags(List<Long> tagIds) {
        return (root, query, cb) -> {
            // Avoid duplicates in result
            query.distinct(true);

            // Join to categories
            Join<BlogComic, Tag> tagJoin = root.join("tags");

            // Filter by categoryIds
            Predicate tagPredicate = tagJoin.get("id")
                                            .in(tagIds);

            // Group by blog id
            query.groupBy(root.get("id"));

            // Having COUNT(DISTINCT tag_id) = tagIds.size()
            query.having(cb.equal(cb.countDistinct(tagJoin.get("id")), tagIds.size()));

            // Return the final condition
            return cb.and(tagPredicate);
        };
    }

    public static Specification<BlogComic> hasAllCategoriesAndTags(List<Long> categoryIds, List<Long> tagIds) {
        return (root, query, cb) -> {
            // Tránh trùng kết quả
            query.distinct(true);

            // Join tới categories và tags
            Join<BlogComic, Category> categoryJoin = root.join("categories");
            Join<BlogComic, Tag> tagJoin = root.join("tags");

            // Predicate lọc trong where
            Predicate categoryPredicate = categoryJoin.get("id")
                                                      .in(categoryIds);
            Predicate tagPredicate = tagJoin.get("id")
                                            .in(tagIds);

            // Group by blog ID
            query.groupBy(root.get("id"));

            // Having count đủ cả category và tag (distinct để tránh đếm trùng)
            Predicate havingCategory = cb.equal(cb.countDistinct(categoryJoin.get("id")), categoryIds.size());
            Predicate havingTag = cb.equal(cb.countDistinct(tagJoin.get("id")), tagIds.size());

            query.having(cb.and(havingCategory, havingTag));

            // Kết hợp điều kiện WHERE
            return cb.and(categoryPredicate, tagPredicate);
        };
    }

}
