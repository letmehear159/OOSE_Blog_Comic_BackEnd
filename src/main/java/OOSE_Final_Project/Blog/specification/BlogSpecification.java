package OOSE_Final_Project.Blog.specification;

import OOSE_Final_Project.Blog.entity.blog.Blog;
import org.springframework.data.jpa.domain.Specification;

public class BlogSpecification {

    public static Specification<Blog> containKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.trim().isEmpty()) {
                return criteriaBuilder.conjunction(); // always true (không lọc gì nếu không có keyword)
            }

            // Chuyển từ khóa và title/content sang chữ thường
            String pattern = "%" + keyword.toLowerCase() + "%";

            // So sánh title và content không phân biệt hoa thường
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), pattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), pattern)
            );
        };
    }
}
