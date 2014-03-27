package com.example.model;

public class Score {
	
	private int playerScore;
	private int cpuScore;
	
	public Score() {
		playerScore = 0;
		cpuScore = 0;
	}
	
	public void reachedWin(int playerOrCpu) {
		if (playerOrCpu==21) {
			this.playerScore = 0;
			this.cpuScore = 0;
		}
	}
	
	public void incrementPlayerScore() {
		playerScore++;
	}
	
	public void incremeentCpuScore() {
		cpuScore++;
	}
	
	public int getPlayerScore() {
		return playerScore;
	}
	
	public int getCpuScore() {
		return cpuScore;
	}

}
