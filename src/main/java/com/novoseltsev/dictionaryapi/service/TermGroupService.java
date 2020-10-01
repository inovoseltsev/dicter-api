package com.novoseltsev.dictionaryapi.service;

import com.novoseltsev.dictionaryapi.domain.entity.TermGroup;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface TermGroupService {

    TermGroup createForUser(TermGroup termGroup, Long userId);

    TermGroup createForTermGroupFolder(TermGroup termGroup, Long folderId);

    TermGroup update(TermGroup termGroup);

    void deleteById(Long id);

    TermGroup findById(Long id);

    List<TermGroup> findAllByUserId(Long userId);

    List<TermGroup> findAllByTermGroupFolderId(Long folderId);
}