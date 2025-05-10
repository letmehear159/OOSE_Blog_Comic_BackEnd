package OOSE_Final_Project.Blog.dto.res.user;

import lombok.Getter;

@Getter
public class UserCommentRes {


    Long userId;

    String displayName;

    double level;

    String avatar;

    private UserCommentRes(Long userId, String displayName, double level, String avatar) {
        this.userId = userId;
        this.displayName = displayName;
        this.level = level;
        this.avatar = avatar;
    }

    public static class Builder {

        private Long userId;

        private String displayName;

        private double level;

        private String avatar;

        public Builder avatar(String avatar) {
            this.avatar = avatar;
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

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public UserCommentRes build() {
            return new UserCommentRes(this.userId, this.displayName, this.level, this.avatar);
        }
    }

}
