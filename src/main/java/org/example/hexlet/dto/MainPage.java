package org.example.hexlet.dto;

public class MainPage {
    private boolean visited;

    public MainPage(boolean visited) {
        this.visited = visited;
    }

    public boolean isVisited() {
        return visited;
    }
}
