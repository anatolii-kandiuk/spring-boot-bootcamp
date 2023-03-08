package com.ltp.gradesubmission;

import com.ltp.gradesubmission.pojo.Grade;
import com.ltp.gradesubmission.repository.GradeRepository;
import com.ltp.gradesubmission.service.GradeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GradeServiceTest {

    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private GradeService gradeService;

    @Test
    public void getTradesFromRepoTest() {
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(
                new Grade("Harry", "Potions", "C-"),
                new Grade("Hermione", "Potions", "A+")
        ));

        List<Grade> grades = gradeService.getGrades();

        assertEquals("Harry", grades.get(0).getName());
        assertEquals("Potions", grades.get(1).getSubject());
    }

    @Test
    public void getGradeIndexTest() {
        Grade grade = new Grade("Harry", "Potions", "C-");

        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(grade));
        when(gradeRepository.getGrade(0)).thenReturn(grade);

        int valid = gradeService.getGradeIndex(grade.getId());
        int notFound = gradeService.getGradeIndex("123");

        assertEquals(0, valid);
        assertEquals(Constants.NOT_FOUND, notFound);
    }

    @Test
    public void getGradeByIdTest() {
        Grade grade = new Grade("Harry", "Potions", "C-");

        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(grade));
        when(gradeRepository.getGrade(0)).thenReturn(grade);

        String id = grade.getId();
        Grade result = gradeService.getGradeById(id);

        assertEquals(grade, result);
    }

    @Test
    public void addGradeTest() {
        Grade grade = new Grade("Harry", "Potions", "C-");

        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(grade));
        when(gradeRepository.getGrade(0)).thenReturn(grade);

        Grade newGrade = new Grade("Hermione", "Potions", "A+");

        gradeService.submitGrade(newGrade);

        verify(gradeRepository, times(1)).addGrade(newGrade);
    }

    @Test
    public void updateGradeTest() {
        Grade grade = new Grade("Harry", "Potions", "C-");

        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(grade));
        when(gradeRepository.getGrade(0)).thenReturn(grade);

        grade.setScore("A-");

        gradeService.submitGrade(grade);

        verify(gradeRepository, times(1)).updateGrade(grade, 0);
    }
}