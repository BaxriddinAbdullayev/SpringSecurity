package dasturlash.uz.entity;

import dasturlash.uz.enums.GeneralStatus;
import dasturlash.uz.enums.ProfileRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "task")
public class TaskEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "title", columnDefinition = "text")
    private String title;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "profile_id")
    private String profileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", updatable = false,insertable = false)
    private ProfileEntity profile;
}
