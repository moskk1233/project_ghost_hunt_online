package com.moskuza.controller;

import com.moskuza.entity.Ghost;
import com.moskuza.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private int wave;
    private List<Player> players;
    private List<Ghost> ghosts;

    public GameController() {
        this.wave = 1;
        this.players = new ArrayList<>();
        this.ghosts = new ArrayList<>();
        this.ghosts.add(new Ghost());
        this.ghosts.add(new Ghost());
        this.ghosts.add(new Ghost());
        this.ghosts.add(new Ghost());
        this.ghosts.add(new Ghost());
    }

    public int getWave() {
        return wave;
    }

    public List<Ghost> getGhosts() {
        return ghosts;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setGhosts(List<Ghost> ghosts) {
        this.ghosts = ghosts;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(int index) {
        this.players.remove(index);
    }
}
