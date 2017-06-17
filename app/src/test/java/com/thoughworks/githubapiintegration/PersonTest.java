package com.thoughworks.githubapiintegration;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {

    private Person person;

    @Before
    public void setUp() throws Exception {

        person = new Person("Karthik R", "Initial Commit", "abcd3fawdw1132", "sample_url");
    }

    @Test
    public void getName() throws Exception {
        assertEquals("Karthik R", person.getName());
    }

    @Test
    public void getCommitMessage() throws Exception {
        assertEquals("Initial Commit", person.getCommitMessage());
    }

    @Test
    public void getCommitSha() throws Exception {
        assertEquals("abcd3fawdw1132", person.getCommitSha());
    }

    @Test
    public void getImageUrl() throws Exception {
        assertEquals("sample_url", person.getImageUrl());
    }

}