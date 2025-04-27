package OOSE_Final_Project.Blog.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tags")
public class Tag extends BaseEntity {

    private String name;
    private String description;
}