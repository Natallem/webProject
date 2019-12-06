package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.repository.TagRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public boolean isNameVacant(String name) {
        return tagRepository.countByName(name) == 0;
    }

    public Tag findByName(String name) {
        return name == null ? null : tagRepository.findByName(name);
    }

    Set<Tag> saveAll(Set<String> tagsSet){
        Set<Tag> tags= new HashSet<>();
        for (String tagString : tagsSet) {
            Tag tag = findByName(tagString);
            if (tag==null){
                tag = new Tag();
                tag.setName(tagString);
                tagRepository.save(tag);
            }
            tags.add(tag);
        }
        return tags;
    }

}
