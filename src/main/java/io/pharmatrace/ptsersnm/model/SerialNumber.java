package io.pharmatrace.ptsersnm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@Table(name = "serial_numbers")
public class SerialNumber implements BaseEntity<UUID>, Serializable {

    @Id
    @Column("sn_id")
    private UUID id;

    @Column("sn_index")
    private Long snIndex;

    @Column("serial_number")
    @Size(max = 30)
    private String serialNumber;

    @Column("profile_id")
    private UUID profileId;

    @NotBlank(message="is required field")
    @Column("status")
    private String status;

    public SerialNumber(){

    }

    @Override
    public UUID generatePK() {
        return UUID.randomUUID();
    }


    @Override
    public void setCreatedOn(Instant createdOn) {

    }

    @Override
    public void setUpdatedOn(Instant updatedOn) {

    }

}
