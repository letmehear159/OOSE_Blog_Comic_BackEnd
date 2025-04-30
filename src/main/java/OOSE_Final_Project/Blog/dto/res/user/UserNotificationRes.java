package OOSE_Final_Project.Blog.dto.res.user;

public class UserNotificationRes {

    private Long userId;
    private String displayName;
    private String avatar;

    // Constructor private để bắt buộc dùng builder
    private UserNotificationRes(Builder builder) {
        this.userId = builder.userId;
        this.displayName = builder.displayName;
        this.avatar = builder.avatar;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long userId;
        private String displayName;
        private String avatar;

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder avatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public UserNotificationRes build() {
            return new UserNotificationRes(this);
        }
    }

    // Getters (nếu cần)
    public Long getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAvatar() {
        return avatar;
    }
}
