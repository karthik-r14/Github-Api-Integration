package com.thoughworks.githubapiintegration;

class Person {
    private String name;
    private String commitMessage;
    private String commitSha;
    private String imageUrl;

    public Person(String name, String commitMessage, String commitSha, String imageUrl) {
        this.name = name;
        this.commitMessage = commitMessage;
        this.commitSha = commitSha;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public String getCommitSha() {
        return commitSha;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
