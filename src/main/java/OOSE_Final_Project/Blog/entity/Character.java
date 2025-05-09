package OOSE_Final_Project.Blog.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "characters")
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Character extends BaseEntity {

    String vietName;

    String chineseName;

    String englishName;

    String otherName;

    // Bí danh
    String alias;

    int age;

    String gender;

    // Tên giả
    String pseudonym;

    // Huyết mạch
    String bloodLine;

    //Chủng tộc
    String race;

    // Tông môn
    String sect;

    // Gia tộc
    String clan;

    // Quốc gia/lãnh thỗ
    String realm;

    // Cảnh giới tu luyện
    String cultivationRealm;

    // Cảnh giới thân thể
    String bodyRealm;

    //Sức mạnh
    String combatPower;

    String status;

    String causeOfDeath;

    // Hôn phu
    String betrothed;

    // Phe phái
    String faction;

    // Xuất hiện lần đầu
    String firstAppearance;
}
