package com.novoseltsev.dicterapi.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

import static com.novoseltsev.dicterapi.validation.Pattern.DESCRIPTION_PATTERN;
import static com.novoseltsev.dicterapi.validation.ValidationMessage.DESCRIPTION_ERROR;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table
public class Activity extends AbstractEntity {

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Pattern(regexp = DESCRIPTION_PATTERN, message = DESCRIPTION_ERROR)
    private String description;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Folder> folders = new ArrayList<>();

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TermGroup> termGroups = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    public Activity(Long id) {
        super(id);
    }

    public Activity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addFolder(Folder folder) {
        folder.setActivity(this);
        this.folders.add(folder);
        this.user.addFolder(folder);
    }

    public void addTermGroup(TermGroup termGroup) {
        termGroup.setActivity(this);
        this.termGroups.add(termGroup);
        this.user.addTermGroup(termGroup);
    }
}
