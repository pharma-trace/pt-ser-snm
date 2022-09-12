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

@Data
@Getter
@Setter
@AllArgsConstructor
@Table(name = "serial_numbers")
public class SerialNumber implements BaseEntity<Long>, Serializable {

    @Id
    @Column("id")
    private Long id;


    @NotBlank(message="is required field")
    @Column("serial_number")
    @Size(max = 30)
    private String serialNumber;

    public SerialNumber(){

    }

    @Override
    public Long generatePK() {
        return hash(this.serialNumber);
    }


    @Override
    public void setCreatedOn(Instant createdOn) {

    }

    @Override
    public void setUpdatedOn(Instant updatedOn) {

    }

    public static long hash(final String string)
    {
        long BASE_PRIME = 1125899906842597L;
        long      h   = BASE_PRIME;
        final int len = string.length();

        for(int i = 0; i < len; i++)
        {
            h = 31 * h + string.charAt(i);
        }
        return h;
    }
}
