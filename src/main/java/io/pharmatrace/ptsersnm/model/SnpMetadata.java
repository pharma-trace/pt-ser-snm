package io.pharmatrace.ptsersnm.model;

import lombok.*;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sn_profile_metadata")
public class SnpMetadata implements BaseEntity<UUID>{

    @Id
    @Column("metadata_id")
    private UUID id;

    @Size(max = 20)
    @Column("profile_creation_date")
    private String key;

    @Size(max = 50)
    @Column("profile_creation_date")
    private String value;

    @Size(max = 40)
    @Column("profile_creation_date")
    private UUID profileId;

    @Size(max = 15)
    @Column("profile_creation_date")
    private Instant createdOn;

    @Override
    public UUID generatePK() {
        return UUID.randomUUID();
    }


    @Override
    public void setIsDelete(Boolean isDelete) {
        BaseEntity.super.setIsDelete(false);
    }

    @Override
    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }
}
