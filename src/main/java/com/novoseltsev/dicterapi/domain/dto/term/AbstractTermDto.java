package com.novoseltsev.dicterapi.domain.dto.term;

import com.novoseltsev.dicterapi.domain.dto.DtoMapper;
import com.novoseltsev.dicterapi.domain.entity.Term;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;


import static com.novoseltsev.dicterapi.validation.Pattern.KEY_WORD_PATTERN;

@Getter
@NoArgsConstructor
public abstract class AbstractTermDto implements DtoMapper<Term> {

    @NotBlank
    private String name;

    @NotBlank
    private String definition;

    @Pattern(regexp = KEY_WORD_PATTERN)
    private String keyword;

    public AbstractTermDto(String name, String definition, String keyword) {
        this.name = name;
        this.definition = definition;
        this.keyword = keyword;
    }

    @Override
    public Term toEntity() {
        return new Term(name, definition, keyword);
    }
}
