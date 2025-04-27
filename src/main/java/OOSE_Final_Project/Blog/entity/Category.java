package OOSE_Final_Project.Blog.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    private String name;
    private String description;
}
