package hu.personal.caradvertiser.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "advertisement")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ADVERTISEMENT_TABLE")
    @SequenceGenerator(name = "SEQ_ADVERTISEMENT_TABLE", sequenceName = "SEQ_ADVERTISEMENT_TABLE", allocationSize = 1, initialValue = 15)
    private Long id;

    private String brand;

    private String type;

    private String description;

    private Long price;
}
