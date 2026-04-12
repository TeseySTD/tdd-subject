package edu.skoreiko.library.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * @author Rout
 * @version 1.0.0
 * @project Library
 * @class BaseMetaData
 * @since 12.04.2026 - 14.01
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseMetaData {
    @Builder.Default
    private int code = 200;
    @Builder.Default
    private boolean success = true;
    @Builder.Default
    private String errorMessage = null;

    public BaseMetaData(int code, boolean success) {
        this.code = code;
        this.success = success;
    }
}