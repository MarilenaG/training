package com.training.fullstack.admin.service;

import com.training.fullstack.admin.infrastructure.SkillRepository;
import com.training.fullstack.admin.model.Skill;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class SkillService {

    private SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill addSkill (String title, String content, String prerequisiste){
        Optional<Skill>  skill =  skillRepository.findByTitle(title);
        if (skill.isPresent()) {
            throw new IllegalArgumentException("Skill with this title already exists");
        }
        return skillRepository.save(new Skill(title,content,prerequisiste));
    }

    public List<Skill> listSkills (){
        return skillRepository.findAll();
    }

    public List<Skill> deleteSkill ( String title){
        Skill skill = skillRepository.findByTitle(title).orElseThrow(()-> new NoSuchElementException("No skill with title " + title));
        skillRepository.delete(skill);
        return listSkills();
    }
}
