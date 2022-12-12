package com.example.newestlinen.form.province;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Schema
@Data
public class AddAllProvinceForm {
    @Schema(name = "names")
    @Size(min = 1)
    private List<String> names;

    @Schema(name = "level")
    @Min(value = 1)
    private int level;

    @Schema(name = "parentId")
    @Min(value = 1,message = "parent should not be null")
    private Long parentId;
}
