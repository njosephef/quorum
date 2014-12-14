package com.design.strategy;

public class Search {
    private ISearch search;

    public Search() {
    }
    
    public void setSearch(ISearch search) {
        this.search = search;
    }
    
    public void search() {
        search.search();
    }
    
    public String toString() {
        return "Search.toString()";
    }
}
