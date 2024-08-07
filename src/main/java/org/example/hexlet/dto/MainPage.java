package org.example.hexlet.dto;

public class MainPage {
    private boolean visited;
    private String currentUser;

    public MainPage(boolean visited, String currentUser) {
        this.visited = visited;
        this.currentUser = currentUser;
    }

    public boolean isVisited() {
        return visited;
    }

    public String getCurrentUser() {
        return currentUser;
    }
}
