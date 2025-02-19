package spring.myproject.domain.gathering;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@Entity
@Setter
@AllArgsConstructor
@Builder
@Table(name = "gathering_count")
public class GatheringCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @MapsId
    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "gathering_id")
    private Gathering gathering;


    private int count;
}
