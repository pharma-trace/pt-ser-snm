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
import java.lang.reflect.Array;
import java.time.Instant;
import java.util.ArrayList;
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
    private Double remainingNumbers;

    @Size(max = 15)
    @Column("exclude_number_count")
    private Double excludeNumberCount;

    @Size(max = 20)
    @Column("minimum_value")
    private String minimumValue;

    @Size(max = 20)
    @Column("maximum_value")
    private String maximumValue;

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

    @Size(max = 7)
    @Column("serial_num_length")
    private Integer serialNumberLength;

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
        Integer excludeCount =0;
        char smallest='_', largest='_';

        if(this.format.equals("AlphaNumeric")){
            if(this.numericValues){
                this.serialNumChars="0123456789"+this.serialNumChars;
                if(!(this.excludeNumericValues==null || this.excludeNumericValues.equals(""))){
                    this.serialNumChars = this.serialNumChars.replaceAll("["+this.excludeNumericValues+"]", "");
                    excludeCount+=this.excludeNumericValues.length();
                }

                smallest= this.serialNumChars.charAt(0);
                largest = this.serialNumChars.charAt(this.serialNumChars.length()-1);
            }
            if(this.lowerCaseAlphabet){
                this.serialNumChars+="abcdefghijklmnopqrstuvwxyz";
                if(!(this.excludeLowerAlph==null || this.excludeLowerAlph.equals(""))){
                    this.serialNumChars = this.serialNumChars.replaceAll("["+this.excludeLowerAlph+"]", "");
                    excludeCount+=excludeLowerAlph.length();
                }

                if(smallest=='_')
                    smallest= this.serialNumChars.charAt(0);
                largest = this.serialNumChars.charAt(this.serialNumChars.length()-1);

            }
            if(this.upperCaseAlphabet){
                this.serialNumChars+="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                if(!(this.excludeUpperAlph==null || this.excludeUpperAlph.equals(""))){
                    this.serialNumChars = this.serialNumChars.replaceAll("["+this.excludeUpperAlph+"]", "");
                    excludeCount+=excludeUpperAlph.length();
                }

                if(smallest=='_')
                    smallest= this.serialNumChars.charAt(0);
                largest = this.serialNumChars.charAt(this.serialNumChars.length()-1);
            }
            if(this.specialCase){
                this.serialNumChars+=includeSpecialCase;
//                if(!(this.includeSpecialCase==null || this.includeSpecialCase.equals(""))){
//                    excludeCount+="!\"%&'()*+,-./:;<=?".replaceAll("["+this.includeSpecialCase+"]", "").length();
//                }
            }

        }else{
            this.serialNumChars+="0123456789";
            if(!(this.excludeNumericValues ==null || this.excludeNumericValues.equals(""))){
                this.serialNumChars = this.serialNumChars.replaceAll("["+this.excludeNumericValues+"]", "");
                excludeCount+=this.excludeNumericValues.length();
            }
            smallest= this.serialNumChars.charAt(0);
            largest = this.serialNumChars.charAt(this.serialNumChars.length()-1);
        }

        if(this.maxRequestSize==null || maxRequestSize<1000){
            this.maxRequestSize=1000;
        }

        double temp0 = this.serialNumChars.length();
        this.remainingNumbers = (double)Math.pow(temp0, this.serialNumberLength);
        double temp1 =temp0+excludeCount;
        double temp2 = (double) Math.pow(temp1, this.serialNumberLength);
        this.excludeNumberCount = temp2-this.remainingNumbers;

        this.minimumValue = String.valueOf(smallest).repeat(serialNumberLength);
        this.maximumValue = String.valueOf(largest).repeat(serialNumberLength);


    }

}
