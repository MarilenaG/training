package com.training.fullstack;

import com.training.fullstack.admin.infrastructure.SkillRepository;
import com.training.fullstack.admin.model.Skill;
import com.training.fullstack.admin.service.SkillService;
import com.training.fullstack.users.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkillServiceTest {
    @Autowired
    private SkillService skillService;
    @Autowired
    private SkillRepository skillRepository;

    @Test
    public void add_non_existing_skill_should_work(){
        skillRepository.deleteAll();
        Skill java = skillService.addSkill("java", "general training java language" ,"basic programming skills");
        Skill skillSaved = skillRepository.findByTitle("java").get();
        assert (skillSaved.getId()>0);
        skillRepository.delete(skillSaved);
    }
    @Test(expected = IllegalArgumentException.class)
    public void add_existing_skill_should_fail(){
        skillRepository.deleteAll();
        Skill java = skillService.addSkill("java", "general training java language" ,"basic programming skills");
        Skill secondJava = skillService.addSkill("java", "another java training" ,"another prerquisites");
    }

    @Test(expected = NoSuchElementException.class)
    public void delete_non_existing_skill_should_fail(){
        skillRepository.deleteAll();
        skillService.deleteSkill("java");
     }

    @Test()
    public void delete_existing_skill_should_work(){
        skillRepository.deleteAll();
        Skill java = skillService.addSkill("java", "general training java language" ,"basic programming skills");
        skillService.deleteSkill("java");
        assert (skillRepository.findAll().size()==0);
    }

    @Test()
    public void list_skill_should_return_correct_number_of_skills(){
        skillRepository.deleteAll();
        assert (skillService.listSkills().size()==0);
        Skill java = skillService.addSkill("java", "general training java language" ,"basic programming skills");
        Skill java1 = skillService.addSkill("java 8", "another java training" ,"java trainign");
        assert (skillService.listSkills().size()==2);
    }
}
