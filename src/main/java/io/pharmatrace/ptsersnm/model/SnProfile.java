package io.pharmatrace.ptsersnm.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.pharmatrace.ptsersnm.context.config.CustomDeserializer;
import io.pharmatrace.ptsersnm.context.config.CustomSerializer;
import io.r2dbc.postgresql.codec.Json;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@Table(name = "sn_profile")
public class SnProfile implements BaseEntity<UUID>, Serializable {

    @Id
    @Column("profile_id")
    private UUID id;

    @NotBlank(message="is required field")
    @Column("profile_name")
    @Size(max = 100)
    private String name;

    @NotBlank(message="is required field")
    @Column("identifier")
    @Size(max = 100)
    private String identifier;

    @Column("product")
    private String product; //Foriegn key

    @Size(max = 50)
    @Column("front_prep_data")
    private String frontPrepandData;

    @Size(max = 50)
    @Column("prep_data")
    private String prepandData;

    @Size(max = 50)
    @Column("append_data")
    private String appendData;

    @Size(max = 5)
    @Column("max_req_size")
    private Integer maxRequestSize;

    @Size(max = 5)
    @Column("pad_length")
    private Integer padLength;

    @Size(max = 1)
    @Column("pad_characters")
    private String padCharacter;

    @NotBlank(message="is required field")
    @Size(max = 1)
    @Column( "format")
    private String format;

    @Size(max = 1)
    @Column("lower_case_alph")
    private boolean lowerCaseAlphabet;

    @Size(max = 26)
    @Column("exclude_lower_alph")
    private String excludeLowerAlph;

    @Size(max = 1)
    @Column("upper_case_alph")
    private boolean upperCaseAlphabet;

    @Size(max = 26)
    @Column("exclude_upper_alph")
    private String excludeUpperAlph;

    @Size(max = 1)
    @Column("numeric_values")
    private boolean numericValues;

    @Size(max = 10)
    @Column("exclude_numeric_values")
    private String excludeNumericValues;

    @Size(max = 1)
    @Column("special_case")
    private boolean specialCase;

    @Size(max = 20)
    @Column("include_special_case")
    private String includeSpecialCase;

    @Size(max = 1)
    @Column("active")
    private boolean active;

    @Size(max = 50)
    @Column("remaining_numbers")
    private Integer remainingNumbers;

    @Size(max = 15)
    @Column("exclude_number_count")
    private Integer excludeNumberCount;

    @Size(max = 20)
    @Column("minimum_value")
    private Integer minimumValue;

    @Size(max = 20)
    @Column("maximum_value")
    private Integer maximumValue;

    @Size(max = 15)
    @Column("profile_creation_date")
    private Instant createdOn;

    @Column("is_delete")
    private boolean isDelete;

    @Size(max=82)
    @Column("serial_num_chars")
    private String serialNumChars;

    @Size(max = 400)
    @Column("remarks")
    private String remarks;

    @Column("profile_metadata")
//    @JsonSerialize(using = CustomSerializer.class)
//    @JsonDeserialize(using = CustomDeserializer.class)
    private String profileMetadata;

    @Size(max = 40)
    @Column("serial_number_index")
    private Long serialNumberIndex;

    @Size(max = 40)
    @Column("serial_number_used_index")
    private Long serialNumberUsedIndex;

    @Override
    public UUID generatePK() {
        return UUID.randomUUID();
    }

    @Override
    public void setIsDelete(Boolean isDelete) {
        this.isDelete=isDelete;
    }

    @Override
    public Boolean getIsDelete() {
        return isDelete;
    }

    @Override
    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public void setUpdatedOn(Instant updatedOn) {

    }

    public void init(){
        this.serialNumberIndex=0l;
        this.serialNumberUsedIndex=0l;
        this.serialNumChars = "";
        if(this.format.equals("numeric") || (this.numericValues)){
            this.serialNumChars+="0123456789";
            this.serialNumChars = this.serialNumChars.replaceAll("["+this.excludeNumericValues+"]", "");
        }
        if(this.format.equals("AlphaNumeric")){
            if(this.lowerCaseAlphabet){
                this.serialNumChars+="abcdefghijklmnopqrstuvwxyz";
                this.serialNumChars = this.serialNumChars.replaceAll("["+this.excludeLowerAlph+"]", "");
            }
            if(this.upperCaseAlphabet){
                this.serialNumChars+="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                this.serialNumChars = this.serialNumChars.replaceAll("["+this.excludeUpperAlph+"]", "");
            }
            if(this.specialCase){
                this.serialNumChars+=includeSpecialCase;
//
            }
        }
    }

}
