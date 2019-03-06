package com.training.fullstack.admin;

import com.training.fullstack.admin.model.Skill;
import com.training.fullstack.admin.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8091")
@RestController
@RequestMapping(value = "/admin")
public class SkillController {
    @Autowired   SkillService skillService ;

    @GetMapping("/skills")
    ResponseEntity<List<Skill>> listAllSkills(){
        List<Skill> results = skillService.listSkills();
        return  ResponseEntity.ok(results);
    }

    @PostMapping("/createSkill")
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill ) {
        Skill createdSkill = skillService.addSkill(skill.getTitle(),skill.getContent(),skill.getPrerequisites());
        return new ResponseEntity(createdSkill, HttpStatus.OK);
    }
    @PostMapping("/deleteSkill")
    public ResponseEntity<Void> deleteSkill(@RequestBody Skill skill ) {
        skillService.deleteSkill(skill.getTitle());
        return ResponseEntity.ok().build();
    }
    @PostMapping("/updateSkill")
    public ResponseEntity<Skill> updateSkill(@RequestBody Skill skill ) {
        Skill createdSkill = skillService.updateSkill(skill);
        return new ResponseEntity(createdSkill, HttpStatus.OK);
    }
}
