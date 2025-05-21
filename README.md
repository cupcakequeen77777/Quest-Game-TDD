# Quest-Game-TDD

Quest-Game-TDD is a text-based adventure game where players draw cards, participate in quests, and compete to earn shields. This project demonstrates a robust, **Test-Driven Development (TDD)** approach to building a game interface that is **decoupled from its core logic**, allowing for a seamless transition to other platforms, such as a web-based interface.

The game involves strategic decision-making and card management to progress through various stages and ultimately win.

## Key Features

* Adventure and Event Decks
* Player Hands and Shields
* Quest Sponsorship and Participation
* Stage Attacks and Resolutions

## Skills Learned

### Iterative Development
* **Hotseat Approach**: Successfully developed a multi-player text-based game for a shared computer environment, laying the groundwork for a scalable multi-user experience.
* **Web-Based Evolution**: Seamlessly transitioned the core game logic from a text-based interface to a web-based interface, demonstrating adaptability and maintainable code.

### Test-Driven Development (TDD)
* **Responsibility Identification**: Mastered the decomposition of complex use cases into granular responsibilities, validated through comprehensive test suites.
* **Commit Structure**: Employed a systematic R-TEST/R-CODE commit strategy, ensuring thorough development and testing for each functional responsibility.

### Acceptance Testing
* **Scenario Path Identification**: Mapped out paths through use cases for comprehensive testing.

### Game Logic and Interface Design
* **Decoupling Interface and Logic**: Architected a clear separation between the game's user interface and its underlying logic, enabling flexible UI changes without impacting core gameplay.
* **Stage and Attack Setup**: Implemented and validated complex game rules for quest stages and attacks, ensuring strict adherence to game mechanics.

### Refactoring
* **Code Improvement**: Made necessary adjustments to improve design and correct mistakes.
* **Commit Management**: Used REFAC commits to manage refactoring changes.

### Project Management
* **Repository Organization**: Structured the repository with clear naming conventions and commit descriptions.

## Setup and Run

1.  Clone the repository: `git clone https://github.com/cupcakequeen77777/Quest-Game-TDD.git`
2.  Navigate to the project directory: `cd Quest-Game-TDD`
3.  Build the project using Maven: `mvn clean install`
4.  Run the project: `java -jar target/Quest-Game-TDD-1.0-SNAPSHOT.jar`

### Example/Demo

To see an example of the project in action, run the main class `Main.java`. The game will start, and you can follow the prompts to play the game.


### Testing Instructions

1.  Navigate to the project directory: `cd Quest-Game-TDD`
2.  Run the tests using Maven: `mvn test`
3.  Check the test results in the console output or in the generated reports.
