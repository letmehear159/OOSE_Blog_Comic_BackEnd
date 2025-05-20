package OOSE_Final_Project.Blog.dto.res.user;

import OOSE_Final_Project.Blog.enums.ERole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReportRes {

    long id;

    String displayName;

    String avatar;

    ERole role;

    double level;

    private UserReportRes(Builder builder) {
        this.displayName = builder.displayName;
        this.avatar = builder.avatar;
        this.role = builder.role;
        this.level = builder.level;
        this.id = builder.id;
    }

    public static class Builder {

        private String displayName;

        private String avatar;

        private ERole role;

        private double level;

        private long id;

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder avatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder role(ERole role) {
            this.role = role;
            return this;
        }

        public Builder level(double level) {
            this.level = level;
            return this;
        }

        public UserReportRes build() {
            return new UserReportRes(this);
        }
    }
}
