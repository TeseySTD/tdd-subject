package edu.skoreiko.library.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rout
 * @version 1.0.0
 * @project Library
 * @class ApiResponse
 * @since 12.04.2026 - 14.01
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ApiResponse<M extends BaseMetaData, D> {
    private M meta;
    private List<D> data;

    public ApiResponse(M meta, D data) {
        this.meta = meta;
        this.data = new ArrayList<>();
        this.data.add(data);
    }

    public ApiResponse(M meta) {
        this.meta = meta;
        this.data = new ArrayList<>();
    }
}
