package OOSE_Final_Project.Blog.dto.res.user;

import lombok.Getter;

@Getter
public class AuthorBlogRes {

    private Long userId;

    private String displayName;

    private double level;

    private String avatar;

    private String email;

    private AuthorBlogRes(Builder builder) {
        this.userId = builder.userId;
        this.displayName = builder.displayName;
        this.level = builder.level;
        this.avatar = builder.avatar;
        this.email = builder.email;
    }

    public static class Builder {

        private Long userId;

        private String displayName;

        private double level;

        private String avatar;

        private String email;

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder level(double level) {
            this.level = level;
            return this;
        }

        public Builder avatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public AuthorBlogRes build() {
            return new AuthorBlogRes(this);
        }
    }


}
