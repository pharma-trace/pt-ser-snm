package io.pharmatrace.ptsersnm.model;

import org.springframework.data.domain.Persistable;

import java.time.Instant;
import java.util.Objects;

public interface BaseEntity<PK> extends Persistable<PK> {

    @Override
    default boolean isNew() {
        boolean result = Objects.isNull(getId());
        if(result == true) {
            setId(generatePK());
        }
        return result;
    }

    PK generatePK();

    void setId(PK id);

    PK getId();

    default void setIsDelete(Boolean isDelete) {}

    default Boolean getIsDelete() {
        return null;
    }


    void setCreatedOn(Instant createdOn);


}
