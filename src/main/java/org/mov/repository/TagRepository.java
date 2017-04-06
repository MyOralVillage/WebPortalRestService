package org.mov.repository;

import org.mov.entity.Tag;

import java.util.Collection;

public interface TagRepository {
    void saveTag(Tag tag);

    void removeTag(Tag tag);

    Tag findTagById(Long id);

    Tag findTagByName(String name);

    Collection<Tag> findAllTags();
}
