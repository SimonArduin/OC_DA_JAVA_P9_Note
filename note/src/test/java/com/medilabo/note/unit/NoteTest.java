package com.medilabo.note.unit;

import com.medilabo.note.TestVariables;
import com.medilabo.note.domain.Note;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = Note.class)
public class NoteTest extends TestVariables {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
    }

    @Test
    public void contextLoads() {}

    @Nested
    public class validationTests {

        @Test
        public void validationTest() {
            Set<ConstraintViolation<Note>> violations = validator.validate(note);
            assertTrue(violations.isEmpty());
        }

        @Nested
        public class patientIdTests {

            @Test
            public void patientIdNotNull() {
                note.setPatientId(null);
                Set<ConstraintViolation<Note>> violations = validator.validate(note);
                assertFalse(violations.isEmpty());
            }

        }

        @Nested
        public class patientTests {

            @Test
            public void patientNotNull() {
                note.setPatient(null);
                Set<ConstraintViolation<Note>> violations = validator.validate(note);
                assertFalse(violations.isEmpty());
            }

            @Test
            public void patientNotBlank() {
                note.setPatient(stringBlank);
                Set<ConstraintViolation<Note>> violations = validator.validate(note);
                assertFalse(violations.isEmpty());
            }

        }

        @Nested
        public class noteTests {

            @Test
            public void noteNotNull() {
                note.setNote(null);
                Set<ConstraintViolation<Note>> violations = validator.validate(note);
                assertFalse(violations.isEmpty());
            }

            @Test
            public void noteNotBlank() {
                note.setNote(stringBlank);
                Set<ConstraintViolation<Note>> violations = validator.validate(note);
                assertFalse(violations.isEmpty());
            }

        }

    }

}
