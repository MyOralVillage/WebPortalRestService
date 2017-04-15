package org.mov.api;

import org.mov.entity.Tag;
import org.mov.service.MOVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private MOVService movService;

    @Autowired
    public TagController(MOVService movService) {
        this.movService = movService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Tag> showTags(@RequestParam(value = "search", required = false) final String search,
                              @RequestParam(value = "skip", required = false) final Integer skip,
                              @RequestParam(value = "limit", required = false) final Integer limit) {
        Stream<Tag> tagStream = movService.findAllTags().stream();
        if (search != null) tagStream = tagStream.filter(tag -> tag.getName().contains(search.toUpperCase()));
        if (skip != null) tagStream = tagStream.skip(skip);
        if (limit != null) tagStream = tagStream.limit(limit);
        return tagStream.collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public void addTag(@RequestBody Tag tag) {
        movService.saveTag(tag);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    public void saveTags(@RequestBody List<Tag> tags) {
        for (Tag tag : tags) movService.saveTag(tag);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public void saveTag(@PathVariable("id") Long id, @RequestBody Tag tag) {
        tag.setId(id);
        movService.saveTag(tag);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeTag(@PathVariable("id") Long id) {
        Tag tag = new Tag();
        tag.setId(id);
        movService.removeTag(tag);
    }
}
