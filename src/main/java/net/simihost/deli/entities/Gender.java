package net.simihost.deli.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import net.simihost.deli.helper.UtilHelper;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Ruba Hamad on 15/04/2019
 *
 */
public enum Gender {

    MALE("Male"), FEMALE("Female");

    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonCreator
    public static Gender fromValue(String value) {
        return value == null || StringUtils.isBlank(value) ?
                null : UtilHelper.getEnumFromString(Gender.class, value);
    }
}
