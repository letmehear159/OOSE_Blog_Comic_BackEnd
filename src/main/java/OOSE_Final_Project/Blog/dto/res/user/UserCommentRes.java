package OOSE_Final_Project.Blog.dto.res.user;

import lombok.Getter;

@Getter
public class UserCommentRes {


    Long userId;

    String displayName;

    Long level;

    String avatar;

    private UserCommentRes(Long userId, String displayName, Long level, String avatar) {
        this.userId = userId;
        this.displayName = displayName;
        this.level = level;
        this.avatar = avatar;
    }

    public static class Builder {

        private Long userId;

        private String displayName;

        private Long level;

        private String avatar;

        public Builder setAvatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setLevel(Long level) {
            this.level = level;
            return this;
        }

        public Builder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public UserCommentRes build() {
            return new UserCommentRes(this.userId, this.displayName, this.level, this.avatar);
        }
    }

}
