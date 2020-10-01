package com.novoseltsev.dictionaryapi.domain.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "term_group", schema = "dictionary_schema")
public class TermGroup extends AbstractEntity {

    @Column(nullable = false, length = 80)
    @NotBlank
    private String name;

    @Column(nullable = false, length = 120)
    @NotNull
    private String description;

    @OneToMany(mappedBy = "termGroup", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Term> terms = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "term_group_folder_id")
    @ToString.Exclude
    private TermGroupFolder termGroupFolder;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(nullable = false)
    @ToString.Exclude
    private User user;

    public TermGroup(Long id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }

    public void addTerm(Term term) {
        term.setTermGroup(this);
        this.terms.add(term);
    }
}