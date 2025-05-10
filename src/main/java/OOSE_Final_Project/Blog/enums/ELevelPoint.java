package OOSE_Final_Project.Blog.enums;

import lombok.Getter;

@Getter
public enum ELevelPoint {
    VIEW(0.4),COMMENT(0.5),LIKE(0.3),COMMENT_LIKE(0.2);

    private double point;
    ELevelPoint(double point) {
        this.point = point;
    }

}
